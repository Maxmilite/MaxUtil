package io.github.maxmilite.maxutil.module.blatant;

import com.google.gson.JsonObject;
import io.github.maxmilite.maxutil.module.Module;

public class Reach extends Module {

    public float reach;
    public Reach() {
        super("Reach", false);
        this.reach = 6.0F;
    }

    public float getReach() {
        return reach;
    }

    public void setReach(float reach) {
        this.reach = reach;
    }

    @Override
    public void insertElement() {
        this.jsonObject = new JsonObject();
        jsonObject.addProperty("toggle", this.toggle);
        jsonObject.addProperty("reach", (double) this.reach);
        jsonObject.addProperty("key", key);
    }

    @Override
    public void readJsonObject() {
        this.toggle = jsonObject.get("toggle").getAsBoolean();
        this.reach = jsonObject.get("reach").getAsFloat();
        this.key = jsonObject.get("key").getAsInt();
    }
}

