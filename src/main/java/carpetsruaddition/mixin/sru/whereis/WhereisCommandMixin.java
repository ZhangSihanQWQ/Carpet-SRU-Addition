package carpetsruaddition.mixin.sru.whereis;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "dev.dubhe.gugle.carpet.commands.WhereisCommand", remap = false)
@SuppressWarnings("UnresolvedMixinReference")
public final class WhereisCommandMixin {
    @Inject(method = "execute", at = @At("TAIL"), remap = false)
    private static void carpetsruaddition$notifyTarget(CommandContext<?> context, CallbackInfoReturnable<Integer> cir) {
        Object sourceObj = context.getSource();
        if (!(sourceObj instanceof ServerCommandSource source)) {
            return;
        }
        ServerPlayerEntity target = context.getArgument("player", ServerPlayerEntity.class);
        String requesterName = source.getName();
        target.sendMessage(Text.literal("<" + requesterName + ">使用 /whereis 定位了你！"), false);
    }
}
