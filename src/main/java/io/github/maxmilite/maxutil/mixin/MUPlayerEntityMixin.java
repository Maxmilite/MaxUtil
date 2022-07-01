package io.github.maxmilite.maxutil.mixin;

import com.mojang.authlib.GameProfile;
import io.github.maxmilite.maxutil.client.MaxUtilClient;
import io.github.maxmilite.maxutil.events.CommandEvent;
import io.github.maxmilite.maxutil.module.blatant.SkyFloat;
import io.github.maxmilite.maxutil.module.blatant.Strafe;
import io.github.maxmilite.maxutil.module.movement.Sprint;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.stat.StatHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec2f;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;


@Mixin(ClientPlayerEntity.class)
public abstract class MUPlayerEntityMixin extends AbstractClientPlayerEntity {
    public MUPlayerEntityMixin(ClientWorld world, GameProfile profile, @Nullable PlayerPublicKey publicKey) {
        super(world, profile, publicKey);
    }

    public float Max(float x, float y) {
        return Math.abs(x) > Math.abs(y) ? x : y;
    }

    MinecraftClient mc = MinecraftClient.getInstance();

    @Shadow public abstract void setSprinting(boolean sprinting);

    @Shadow protected abstract boolean hasMovementInput();

    @Shadow public abstract boolean isUsingItem();

    @Shadow public abstract void sendMessage(Text message);

    @Shadow public abstract void sendMessage(Text message, boolean actionBar);

    @Shadow public abstract boolean isRiding();

    @Shadow public abstract boolean isSneaking();

    @Shadow public Input input;

    @Shadow @Final private StatHandler statHandler;

    protected double getDefaultSpeed() {
        double defaultSpeed = 0.2873;
//        double defaultSpeed = 0.18;
        if (mc.player != null && mc.player.hasStatusEffect(StatusEffects.SPEED)) {
            int amplifier = Objects.requireNonNull(mc.player.getStatusEffect(StatusEffects.SPEED)).getAmplifier();
            defaultSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        if (mc.player.hasStatusEffect(StatusEffects.SLOWNESS)) {
            int amplifier = Objects.requireNonNull(mc.player.getStatusEffect(StatusEffects.SLOWNESS)).getAmplifier();
            defaultSpeed /= 1.0 + 0.2 * (amplifier + 1);
        }
        return defaultSpeed;
    }

    public Vec2f transformStrafe(double speed) {
        assert mc.player != null;
        float forward = mc.player.input.movementForward;
        float side = mc.player.input.movementSideways;
        float yaw = mc.player.prevYaw + (mc.player.getYaw() - mc.player.prevYaw) * mc.getTickDelta();

        double velX, velZ;

        if (forward == 0.0f && side == 0.0f) return new Vec2f(0, 0);

        else if (forward != 0.0f) {
            if (side >= 1.0f) {
                yaw += (float) (forward > 0.0f ? -45 : 45);
                side = 0.0f;
            } else if (side <= -1.0f) {
                yaw += (float) (forward > 0.0f ? 45 : -45);
                side = 0.0f;
            }

            if (forward > 0.0f)
                forward = 1.0f;

            else if (forward < 0.0f)
                forward = -1.0f;
        }

        double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        double mz = Math.sin(Math.toRadians(yaw + 90.0f));

        velX = (double) forward * speed * mx + (double) side * speed * mz;
        velZ = (double) forward * speed * mz - (double) side * speed * mx;

        return new Vec2f((float) velX, (float) velZ);
    }

    @Inject(method = "tickMovement", at = @At("TAIL"))
    public void onSprint(CallbackInfo info) {
        if (MaxUtilClient.manager.getModule("Sprint").isToggle() && this.hasMovementInput()) {
            float v = this.input.movementSideways;
            float v1 = this.input.movementForward;
            if (((Sprint) MaxUtilClient.manager.getModule("Sprint")).isAllDirections() || (Math.abs(v) <= 1e-6 & v1 > 0.0F))
                this.setSprinting(true);
        }
    }

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
        private boolean onSlowdown(ClientPlayerEntity player) {
            if (MaxUtilClient.manager.getModule("NoSlow").isToggle()) return false;
        return player.isUsingItem();
    }

    @Inject(method = "tickMovement", at = @At("TAIL"))
    public void movement(CallbackInfo info) {
        if (MaxUtilClient.manager.getModule("Strafe").isToggle() && !MaxUtilClient.manager.getModule("SkyFloat").isToggle())
            if (this.hasMovementInput() && !this.isRiding() && !this.isSneaking()) {
                Strafe strafe = (Strafe) MaxUtilClient.manager.getModule("Strafe");

                if (strafe.isAutoJump() && this.isOnGround())
                    this.jump();

                float amplifier = strafe.getAmplifier();

                float velx = transformStrafe(getDefaultSpeed()).x * amplifier;
                float vely = transformStrafe(getDefaultSpeed()).y * amplifier;
                this.setVelocity(velx, this.getVelocity().getY(), vely);
            }
        if (MaxUtilClient.manager.getModule("SkyFloat").isToggle()) {
            if (!this.isRiding() && !this.isSneaking()) {

                SkyFloat skyFloat = (SkyFloat) MaxUtilClient.manager.getModule("SkyFloat");

                double speed = skyFloat.speed;
                float verticalBase = (float) skyFloat.vertical;

                float verticalSpeed = 0.0F;

                if (this.input.sneaking)
                    verticalSpeed -= verticalBase;
                if (this.input.jumping)
                    verticalSpeed += verticalBase;

                float velx = transformStrafe((float) speed).x;
                float vely = transformStrafe((float) speed).y;
                this.setVelocity(velx, verticalSpeed, vely);
            }
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void nightVision(CallbackInfo info) {
        if (MaxUtilClient.manager.getModule("NightVision").isToggle() &&
                MaxUtilClient.manager.getModule("NightVision").mode.equals("EFFECT")) {
            this.setStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 100000, 137, false, false), null);
        } else if (this.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
            if (Objects.requireNonNull(this.getStatusEffect(StatusEffects.NIGHT_VISION)).getAmplifier() == 137 &&
                    !Objects.requireNonNull(this.getStatusEffect(StatusEffects.NIGHT_VISION)).isAmbient())
                this.removeStatusEffect(StatusEffects.NIGHT_VISION);
        }
    }

    @Inject(method = "sendChatMessage(Ljava/lang/String;Lnet/minecraft/text/Text;)V", at = @At("HEAD"), cancellable = true)
    public void messageHandler(String message, @Nullable Text preview, CallbackInfo info) {
        CommandEvent.onCommand(message, preview, info);
    }
}
