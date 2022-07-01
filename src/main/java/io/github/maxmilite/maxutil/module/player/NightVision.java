package io.github.maxmilite.maxutil.module.player;

import com.google.gson.JsonObject;
import io.github.maxmilite.maxutil.module.Module;

import java.util.Arrays;

public class NightVision extends Module {

    public NightVision() {
        super("NightVision", false);
        this.mode = "GAMMA";
        this.modeList = Arrays.asList("GAMMA", "EFFECT");
    }

    @Override
    public void insertElement() {
        this.jsonObject = new JsonObject();
        jsonObject.addProperty("toggle", this.toggle);
        jsonObject.addProperty("mode", this.mode);
        jsonObject.addProperty("key", key);
    }
    @Override
    public void readJsonObject() throws NullPointerException {
        this.toggle = jsonObject.get("toggle").getAsBoolean();
        this.mode = jsonObject.get("mode").getAsString();
        this.key = jsonObject.get("key").getAsInt();
    }

}
