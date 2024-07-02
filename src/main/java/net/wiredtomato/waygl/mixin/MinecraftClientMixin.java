package net.wiredtomato.waygl.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.wiredtomato.waygl.VirtualCursor;
import net.wiredtomato.waygl.WayGL;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;initBackendSystem()Lnet/minecraft/util/TimeSupplier$Nanoseconds;"))
    private void preGLFWInit(RunArgs args, CallbackInfo ci) {
        WayGL.tryUseWayland();
    }

    @Inject(method = "<init>", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;textureManager:Lnet/minecraft/client/texture/TextureManager;", ordinal = 0, shift = At.Shift.AFTER))
    private void onTextureManagerSetup(RunArgs args, CallbackInfo ci) {
        if (WayGL.useVCursor()) {
            VirtualCursor.INSTANCE.setup(MinecraftClient.getInstance().getWindow().getHandle());
        }
    }
}
