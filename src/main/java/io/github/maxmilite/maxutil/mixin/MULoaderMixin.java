package io.github.maxmilite.maxutil.mixin;

import io.github.maxmilite.maxutil.client.MaxUtilClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MULoaderMixin {

    @Shadow @Final public GameOptions options;

    @Inject(method = "tick", at = @At("TAIL"))
    public void nightVision(CallbackInfo info) {
        if (this.options.getGamma().getValue() != 15.0)
            if (MaxUtilClient.manager.getModule("NightVision").isToggle() &&
                    MaxUtilClient.manager.getModule("NightVision").mode.equals("GAMMA"))
                this.options.getGamma().setValue(15.0);
        else if (!MaxUtilClient.manager.getModule("NightVision").isToggle())
            this.options.getGamma().setValue(1.0);
    }

}
