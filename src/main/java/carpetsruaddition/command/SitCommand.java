package carpetsruaddition.command;

import carpet.utils.CommandHelper;
import carpetsruaddition.CarpetSettings;
import carpetsruaddition.sit.access.SitEntity;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;

public final class SitCommand {
    private SitCommand() {
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("sit")
                .requires(source -> CommandHelper.canUseCommand(source, CarpetSettings.commandSit))
                .executes(context -> sitPlayer(context.getSource().getPlayer())));
    }

    public static int sitPlayer(@Nullable ServerPlayerEntity player) {
        if (player == null || !player.isOnGround()) {
            return 0;
        }

        ServerWorld world = player.getEntityWorld();
        ArmorStandEntity armorStandEntity = new ArmorStandEntity(world, player.getX(), player.getY() - 0.16, player.getZ());

        ((SitEntity) armorStandEntity).sru$setSitEntity(true);
        world.spawnEntity(armorStandEntity);
        player.setSneaking(false);
        player.startRiding(armorStandEntity);

        return 1;
    }
}
