package io.github.maxmilite.maxutil.module.blatant;

import com.google.gson.JsonObject;
import io.github.maxmilite.maxutil.module.Module;

public class SkyFloat extends Module {

    public double speed;
    public double vertical;
    public boolean antiKick;

    public SkyFloat() {
        super("SkyFloat", false);
        this.speed = 1.0;
        this.vertical = 1.0;
        this.antiKick = true;
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
    }

    @Override
    public void readJsonObject() {
        this.toggle = jsonObject.get("toggle").getAsBoolean();
        this.antiKick = jsonObject.get("antikick").getAsBoolean();
        this.speed = jsonObject.get("speed").getAsDouble();
        this.vertical = jsonObject.get("vertical").getAsDouble();
        this.key = jsonObject.get("key").getAsInt();
    }
}
