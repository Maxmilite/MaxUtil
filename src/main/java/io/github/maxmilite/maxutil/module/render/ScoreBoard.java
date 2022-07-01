package io.github.maxmilite.maxutil.module.render;

import com.google.gson.JsonObject;
import io.github.maxmilite.maxutil.module.Module;

public class ScoreBoard extends Module {
    public double bgAlpha;
    public double titleAlpha;

    public ScoreBoard() {
        super("ScoreBoard", false);
        this.bgAlpha = 0.0;
        this.titleAlpha = 0.0;
    }

    @Override
    public void insertElement() {
        this.jsonObject = new JsonObject();
        jsonObject.addProperty("toggle", this.toggle);
        jsonObject.addProperty("bgalpha", this.bgAlpha);
        jsonObject.addProperty("titlealpha", this.titleAlpha);
        jsonObject.addProperty("key", key);
    }
    @Override
    public void readJsonObject() throws NullPointerException {
        this.toggle = jsonObject.get("toggle").getAsBoolean();
        this.bgAlpha = jsonObject.get("bgalpha").getAsDouble();
        this.titleAlpha = jsonObject.get("titlealpha").getAsDouble();
        this.key = jsonObject.get("key").getAsInt();
    }

    public double getBgAlpha() {
        return this.bgAlpha;
    }

    public double getTitleAlpha() {
        return titleAlpha;
    }

    public void setBgAlpha(double bgAlpha) {
        this.bgAlpha = bgAlpha;
    }

    public void setTitleAlpha(double titleAlpha) {
        this.titleAlpha = titleAlpha;
    }

}
