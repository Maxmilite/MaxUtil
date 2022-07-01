package io.github.maxmilite.maxutil.util;

import net.minecraft.util.math.Vec2f;

import static io.github.maxmilite.maxutil.client.MaxUtilClient.mc;

public class MathUtil {
    public static Vec2f calcVector(double speed) {
        assert mc.player != null;
        float forward = 1.0F;
        float yaw = mc.player.prevYaw + (mc.player.getYaw() - mc.player.prevYaw) * mc.getTickDelta();
        double velX, velZ;
        double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        velX = (double) forward * speed * mx;
        velZ = (double) forward * speed * mz;
        return new Vec2f((float) velX, (float) velZ);
    }
}
