package net.wiredtomato.waygl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.wiredtomato.waygl.Config;
import net.wiredtomato.waygl.VirtualCursor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;draw()V"))
    private void drawVirtualCursor(DrawContext instance, Operation<Void> original) {
        if (Config.useVirtualCursor) VirtualCursor.INSTANCE.render(instance);
        original.call(instance);
    }
}
