package io.github.maxmilite.maxutil.mixin;

import io.github.maxmilite.maxutil.client.MaxUtilClient;
import io.github.maxmilite.maxutil.module.blatant.SkyFloat;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MUC2SPacketMixin {

    @Inject(method = "sendPacket", at = @At("HEAD"))
    public void noFallPacketHandle(Packet<?> packet, CallbackInfo info) {
        if (MaxUtilClient.manager.getModule("NoFall").isToggle()
            && MaxUtilClient.manager.getModule("NoFall").mode.equals("PACKET")) {
            if (packet instanceof PlayerMoveC2SPacket) {
                ((MUPlayerMoveC2SPacketMixin) packet).setOnGround(true);
            }
        }
        if (MaxUtilClient.manager.getModule("SkyFloat").isToggle()
                && ((SkyFloat) MaxUtilClient.manager.getModule("SkyFloat")).antiKick) {
            if (packet instanceof PlayerMoveC2SPacket) {
                ((MUPlayerMoveC2SPacketMixin) packet).setOnGround(true);
            }
        }
    }

}
