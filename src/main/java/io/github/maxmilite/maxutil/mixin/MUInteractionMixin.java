package io.github.maxmilite.maxutil.mixin;


import io.github.maxmilite.maxutil.client.MaxUtilClient;
import io.github.maxmilite.maxutil.module.blatant.Reach;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayerInteractionManager.class)
public class MUInteractionMixin {

    @Shadow private GameMode gameMode;

    /**
     * @author Maxmilite
     * @reason Modify reach
     */
    @Overwrite
    public float getReachDistance() {
        if (MaxUtilClient.manager.getModule("Reach").isToggle())
            return ((Reach) MaxUtilClient.manager.getModule("Reach")).getReach();
        else
            return this.gameMode.isCreative() ? 5.0F : 4.5F;
    }
}
