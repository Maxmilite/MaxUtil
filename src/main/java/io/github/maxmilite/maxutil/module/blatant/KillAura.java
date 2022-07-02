package io.github.maxmilite.maxutil.module.blatant;

import io.github.maxmilite.maxutil.client.MaxUtilClient;
import io.github.maxmilite.maxutil.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec2f;

import static io.github.maxmilite.maxutil.client.MaxUtilClient.mc;

public class KillAura extends Module {
    public KillAura() {
        super("KillAura", false);
    }

    public static void onKill() {
        PlayerEntity player = mc.player;
        if (MaxUtilClient.manager.getModule("KillAura").isToggle() && player != null) {
            if (mc.world != null) {
                for (Entity entity : mc.world.getEntities()) {
                    if (entity.equals(mc.player))
                        continue;
                    if (!(entity instanceof LivingEntity))
                        continue;
                    if (((LivingEntity) entity).getHealth() <= 0.0F)
                        continue;
                    if (entity.distanceTo(player) > 3.8)
                        continue;
                    Vec2f v = player.getRotationClient();
                    player.lookAt(player.getCommandSource().getEntityAnchor(), entity.getPos());
                    if (mc.cameraEntity != null) {
                        mc.cameraEntity.setPitch(v.x);
                        mc.cameraEntity.setYaw(v.y);
                    }
                    if (player.getAttackCooldownProgress(0.5F) < 1)
                        continue;
                    assert mc.interactionManager != null;
                    mc.interactionManager.attackEntity(mc.player, entity);
                    player.swingHand(player.getActiveHand(), true);
                    if (mc.cameraEntity != null) {
                        mc.cameraEntity.setPitch(v.x);
                        mc.cameraEntity.setYaw(v.y);
                    }
                    break;
                }
            }
        }
    }
}
