package io.github.maxmilite.maxutil.mixin;

import io.github.maxmilite.maxutil.client.MaxUtilClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.Random;

import static io.github.maxmilite.maxutil.client.MaxUtilClient.mc;

@Mixin(MinecraftClient.class)
public class MULoaderMixin {

    @Shadow
    @Final
    public GameOptions options;

    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    @Inject(method = "tick", at = @At("TAIL"))
    public void nightVision(CallbackInfo info) {
        if (this.options.getGamma().getValue() != 15.0)
            if (MaxUtilClient.manager.getModule("NightVision").isToggle() &&
                    MaxUtilClient.manager.getModule("NightVision").mode.equals("GAMMA"))
                this.options.getGamma().setValue(15.0);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void fastUse(CallbackInfo info) {
        int coolDown = (int) (32 / MaxUtilClient.manager.getModule("FastUse").value);

        if (player != null) {
            if (player.isUsingItem()) {
                if (MaxUtilClient.manager.getModule("FastUse").isToggle()) {
                    if (player.getActiveItem().isFood()) {
//                        MessageEvent.sendInfo(player.getItemUseTime() + " " + coolDown);
                        if (player.getItemUseTime() > coolDown) {
                            for (int i = 1; i <= 40; ++i) {
                                Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(player.isOnGround()));
                            }
                            player.stopUsingItem();
                        }
                    }
                }
            }
        }

    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void derp(CallbackInfo info) {
        if (MaxUtilClient.manager.getModule("Derp").isToggle() && mc.player != null) {
            Random random = new Random();
            Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(
            random.nextFloat(360.0F) - 180.0F, random.nextFloat(180.0F) - 90.0F, mc.player.isOnGround()
            ));
        }
    }

}

