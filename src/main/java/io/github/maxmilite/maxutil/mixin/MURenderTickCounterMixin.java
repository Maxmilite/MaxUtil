package io.github.maxmilite.maxutil.mixin;

import io.github.maxmilite.maxutil.client.MaxUtilClient;
import net.minecraft.client.render.RenderTickCounter;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderTickCounter.class)
public class MURenderTickCounterMixin {
    @Shadow
    public float lastFrameDuration;

    @Inject(method = "beginRenderTick", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/RenderTickCounter;prevTimeMillis:J", opcode = Opcodes.PUTFIELD))
    private void onBeingRenderTick(long a, CallbackInfoReturnable<Integer> info) {
        if (MaxUtilClient.manager.getModule("Timer").isToggle())
            lastFrameDuration *= MaxUtilClient.manager.getModule("Timer").value;
    }
}

