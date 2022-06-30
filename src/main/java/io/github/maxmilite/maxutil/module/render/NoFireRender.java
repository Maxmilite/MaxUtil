package io.github.maxmilite.maxutil.module.render;

import com.google.gson.JsonObject;
import io.github.maxmilite.maxutil.module.Module;

public class NoFireRender extends Module {

    public NoFireRender() {
        super("NoFireRender", false);
        this.alpha = 0.3F;
    }

    @Override
    public void insertElement() {
        this.jsonObject = new JsonObject();
        jsonObject.addProperty("toggle", this.toggle);
        jsonObject.addProperty("alpha", this.alpha);
        jsonObject.addProperty("key", key);
    }
    @Override
    public void readJsonObject() {
        this.toggle = jsonObject.get("toggle").getAsBoolean();
        this.alpha = jsonObject.get("alpha").getAsFloat();
        this.key = jsonObject.get("key").getAsInt();
    }

    public float alpha;

    public float getAlpha() {
        return alpha;
    }
}
