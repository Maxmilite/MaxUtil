package io.github.maxmilite.maxutil.mixin;

import io.github.maxmilite.maxutil.client.MaxUtilClient;
import io.github.maxmilite.maxutil.module.blatant.Velocity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ClientPlayNetworkHandler.class)
public class MUS2CPacketMixin {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    @Inject(method = "onEntityVelocityUpdate", at = @At("HEAD"))
    public void velocityHandler(EntityVelocityUpdateS2CPacket packet, CallbackInfo ci) {
        Velocity v = (Velocity) MaxUtilClient.manager.getModule("Velocity");
        if (v.isToggle() && mc.player != null)
            if (packet.getId() == Objects.requireNonNull(mc.player).getId()) {
                double velX = (packet.getVelocityX() / 8000d - mc.player.getVelocity().x) * v.horizontal;
                double velY = (packet.getVelocityY() / 8000d - mc.player.getVelocity().y) * v.vertical;
                double velZ = (packet.getVelocityZ() / 8000d - mc.player.getVelocity().z) * v.horizontal;
                ((MUEntityVelocityUpdateS2CPacketMixin) packet).setX((int) (velX * 8000 + mc.player.getVelocity().x * 8000));
                ((MUEntityVelocityUpdateS2CPacketMixin) packet).setY((int) (velY * 8000 + mc.player.getVelocity().y * 8000));
                ((MUEntityVelocityUpdateS2CPacketMixin) packet).setZ((int) (velZ * 8000 + mc.player.getVelocity().z * 8000));
            }
    }

    @Inject(method = "onEntitySetHeadYaw", at = @At("HEAD"), cancellable = true)
    public void derp(CallbackInfo info) {
//        if (MaxUtilClient.manager.getModule("Derp").isToggle()
//            || MaxUtilClient.manager.getModule("KillAura").isToggle())
//            info.cancel();
    }

}
