package net.wiredtomato.waygl.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.wiredtomato.waygl.WayGL;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextFieldWidget.class)
public class TextFieldWidgetMixin {
    @Inject(method = "charTyped", at = @At("HEAD"), cancellable = true)
    private void charTyped(char chr, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (WayGL.isWayland() && isSpecialChar(chr) && Screen.hasControlDown()) cir.setReturnValue(false);
    }

    @Unique
    private boolean isSpecialChar(char chr) {
        return chr == 'a' || chr == 'v' || chr == 'c' || chr == 'x';
    }
}
