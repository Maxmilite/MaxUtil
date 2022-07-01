package io.github.maxmilite.maxutil.module.blatant;

import com.google.gson.JsonObject;
import io.github.maxmilite.maxutil.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.Objects;

public class SkyFloat extends Module {

    public double speed;
    public double vertical;
    public boolean antiKick;
    public boolean fakeDamage;

    public SkyFloat() {
        super("SkyFloat", false);
        this.speed = 1.0;
        this.vertical = 1.0;
        this.antiKick = true;
        this.fakeDamage = false;
        insertElement();
    }

    @Override
    public void insertElement() {
        this.jsonObject = new JsonObject();
        jsonObject.addProperty("toggle", this.toggle);
        jsonObject.addProperty("key", key);
        jsonObject.addProperty("speed", speed);
        jsonObject.addProperty("vertical", vertical);
        jsonObject.addProperty("antikick", antiKick);
        jsonObject.addProperty("fakedamage", fakeDamage);
    }

    @Override
    public void readJsonObject() throws NullPointerException {
        this.toggle = jsonObject.get("toggle").getAsBoolean();
        this.antiKick = jsonObject.get("antikick").getAsBoolean();
        this.speed = jsonObject.get("speed").getAsDouble();
        this.vertical = jsonObject.get("vertical").getAsDouble();
        this.key = jsonObject.get("key").getAsInt();
        this.fakeDamage = jsonObject.get("fakedamage").getAsBoolean();
    }

    @Override
    public void switchToggle() {
        this.toggle = !this.toggle;
        if (this.toggle && this.fakeDamage) {
            Objects.requireNonNull(MinecraftClient.getInstance().player).animateDamage();
            Objects.requireNonNull(MinecraftClient.getInstance().player).playSound(new SoundEvent(SoundEvents.ENTITY_PLAYER_HURT.getId()), 1.0F, 1.0F);
            MinecraftClient.getInstance().player.jump();

        }
    }
}
