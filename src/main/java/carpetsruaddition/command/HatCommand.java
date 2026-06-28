package carpetsruaddition.command;

import carpet.utils.CommandHelper;
import carpetsruaddition.CarpetSettings;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public final class HatCommand {
    private HatCommand() {
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("hat")
                .requires(source -> CommandHelper.canUseCommand(source, CarpetSettings.commandHat))
                .executes(context -> hatPlayer(context.getSource().getPlayer())));
    }

    public static int hatPlayer(@Nullable ServerPlayerEntity player) {
        if (player == null || player.isSpectator()) {
            return 0;
        }

        ItemStack hat = player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack stack = player.getMainHandStack();
        Item item = stack.getItem();

        if (hasBindingCurse(player, hat)) {
            sendMessage(player, "Already equipped with an Item with BINDING_CURSE");
            return 0;
        }
        if (item.equals(Items.TOTEM_OF_UNDYING)) {
            sendMessage(player, "Items that cannot be equipped: TOTEM_OF_UNDYING");
            return 0;
        }
        if (item instanceof BlockItem blockItem) {
            Block block = blockItem.getBlock();
            if (block instanceof ShulkerBoxBlock) {
                ContainerComponent container = stack.get(DataComponentTypes.CONTAINER);
                if (container != null && container.streamNonEmpty().findAny().isPresent()) {
                    sendMessage(player, "Items that cannot be equipped: SHULKER_BOX(notEmpty)");
                    return 0;
                }
            }
        }

        ItemStack stackCopy = stack.copyWithCount(1);
        player.equipStack(EquipmentSlot.HEAD, stackCopy);
        if (!player.isCreative()) {
            stack.decrement(1);
            if (player.getInventory().getEmptySlot() < 0) {
                ServerWorld world = player.getEntityWorld();
                ItemEntity itemEntity = new ItemEntity(world, player.getX(), player.getY() + 1.0, player.getZ(), hat.copy());
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            } else {
                player.getInventory().insertStack(hat.copy());
            }
        }
        player.playerScreenHandler.sendContentUpdates();
        return 1;
    }

    private static boolean hasBindingCurse(ServerPlayerEntity player, ItemStack stack) {
        RegistryEntry.Reference<Enchantment> bindingCurse = player.getRegistryManager()
                .getOrThrow(RegistryKeys.ENCHANTMENT)
                .getOrThrow(Enchantments.BINDING_CURSE);
        return stack.getEnchantments().getLevel(bindingCurse) > 0;
    }

    private static void sendMessage(ServerPlayerEntity player, String message) {
        player.sendMessage(Text.literal(message), false);
    }
}
