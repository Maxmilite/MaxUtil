package io.github.maxmilite.maxutil.module.blatant;

import com.google.gson.JsonObject;
import io.github.maxmilite.maxutil.module.Module;

public class Strafe extends Module {

    public Strafe() {
        super("Strafe", false);
        this.autoJump = false;
        this.amplifier = 1.0F;
    }

    public float amplifier;
    public boolean autoJump;

    public boolean isAutoJump() {
        return autoJump;
    }

    public void setAutoJump(boolean autoJump) {
        this.autoJump = autoJump;
    }

    public float getAmplifier() {
        return amplifier;
    }

    public void setAmplifier(float amplifier) {
        this.amplifier = amplifier;
    }

    @Override
    public void insertElement() {
        this.jsonObject = new JsonObject();
        jsonObject.addProperty("toggle", this.toggle);
        jsonObject.addProperty("amplifier", (double) this.amplifier);
        jsonObject.addProperty("autojump", this.autoJump);
        jsonObject.addProperty("key", key);
    }

    @Override
    public void readJsonObject() {
        this.toggle = jsonObject.get("toggle").getAsBoolean();
        this.amplifier = jsonObject.get("amplifier").getAsFloat();
        this.autoJump = jsonObject.get("autojump").getAsBoolean();
        this.key = jsonObject.get("key").getAsInt();
    }
}
