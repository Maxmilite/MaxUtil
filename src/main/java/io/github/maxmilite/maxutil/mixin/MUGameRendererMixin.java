package io.github.maxmilite.maxutil.mixin;

import io.github.maxmilite.maxutil.client.MaxUtilClient;
import io.github.maxmilite.maxutil.module.blatant.Reach;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GameRenderer.class)
public class MUGameRendererMixin {
    @ModifyConstant(method = "updateTargetedEntity", constant = @Constant(doubleValue = 3))
    private double updateTargetedEntityModifySurvivalReach(double d) {
        if (MaxUtilClient.manager.getModule("Reach").isToggle())
            return ((Reach) MaxUtilClient.manager.getModule("Reach")).getReach();
        else
            return d;
    }

    @ModifyConstant(method = "updateTargetedEntity", constant = @Constant(doubleValue = 9))
    private double updateTargetedEntityModifySquaredMaxReach(double d) {
        if (MaxUtilClient.manager.getModule("Reach").isToggle())
            return ((Reach) MaxUtilClient.manager.getModule("Reach")).getReach();
        else
            return d;
    }
}

