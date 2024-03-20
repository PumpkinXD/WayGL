package net.wiredtomato.waygl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Mouse;
import net.wiredtomato.waygl.Config;
import net.wiredtomato.waygl.VirtualCursor;
import net.wiredtomato.waygl.WayGL;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Mouse.class)
public abstract class MouseMixin {

    @Shadow public abstract boolean isCursorLocked();

    @Shadow public abstract double getX();

    @Shadow public abstract double getY();

    @ModifyArgs(method = "method_22689", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Mouse;onCursorPos(JDD)V"))
    private void modifyCursorPos(Args args) {
        if (WayGL.isWayland() && Config.useVirtualCursor) {
            args.set(1, VirtualCursor.INSTANCE.handleMovementX(args.get(1)));
            args.set(2, VirtualCursor.INSTANCE.handleMovementY(args.get(2)));
        }
    }

    @Inject(method = { "lockCursor", "unlockCursor" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/Mouse;cursorLocked:Z", ordinal = 1, shift = At.Shift.AFTER))
    private void onLockCursor(CallbackInfo ci) {
        if (WayGL.isWayland() && Config.useVirtualCursor) {
            VirtualCursor.INSTANCE.grabMouse(isCursorLocked());
        }
    }

    @WrapOperation(method = { "lockCursor", "unlockCursor" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/InputUtil;setCursorParameters(JIDD)V"))
    private void onLockCursorSetCursorPosition(long handler, int inputModeValue, double x, double y, Operation<Void> original) {
        if (!(Config.useVirtualCursor && WayGL.useWayland())) {
            if (isCursorLocked()) original.call(handler, inputModeValue, x, y);
        } else VirtualCursor.INSTANCE.setCursorPosition(getX(), getY());
    }
}
