package carpetsruaddition.mixin.client.chat;

import carpetsruaddition.CarpetSettings;
import carpetsruaddition.client.CommandPrefixAutoComplete;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin {
    @Shadow protected TextFieldWidget chatField;

    @Inject(method = "keyPressed(III)Z", at = @At("HEAD"), cancellable = true)
    private void carpetsruaddition$completeDoubleBang(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (keyCode != GLFW.GLFW_KEY_TAB || !CarpetSettings.mcdrCommandAutoCompletion) {
            return;
        }

        String text = this.chatField.getText();
        if (!text.startsWith("!!")) {
            return;
        }

        String suggestion = CommandPrefixAutoComplete.nextMatch(text);
        if (suggestion == null) {
            return;
        }

        this.chatField.setText(suggestion);
        this.chatField.setCursorToEnd(false);
        cir.setReturnValue(true);
    }
}

