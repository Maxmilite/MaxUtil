package io.github.maxmilite.maxutil.mixin;


import io.github.maxmilite.maxutil.client.MaxUtilClient;
import io.github.maxmilite.maxutil.module.blatant.Reach;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import static io.github.maxmilite.maxutil.client.MaxUtilClient.mc;

@Mixin(ClientPlayerInteractionManager.class)
public class MUInteractionMixin {

    @Shadow
    private GameMode gameMode;

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

    @Inject(method = "attackEntity", at = @At("TAIL"))
    public void hitEffect(PlayerEntity player, Entity target, CallbackInfo info) {
        if (MaxUtilClient.manager.getModule("HitEffect").isToggle()
                && MaxUtilClient.manager.getModule("HitEffect").mode.equals("LIGHTNING")) {
            LightningEntity li = new LightningEntity(EntityType.LIGHTNING_BOLT, mc.world);
            li.setPosition(target.getPos());
            Random random = new Random();
            if (mc.world != null)
                new Thread(() -> {
                    int randInt = random.nextInt();
                    while (mc.world.getEntityById(randInt) != null) {
                        randInt = random.nextInt();
                    }
                    mc.world.addEntity(randInt, li);
                }).start();
        }
    }
}
