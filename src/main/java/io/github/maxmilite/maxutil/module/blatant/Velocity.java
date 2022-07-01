package io.github.maxmilite.maxutil.module.blatant;

import com.google.gson.JsonObject;
import io.github.maxmilite.maxutil.module.Module;

public class Velocity extends Module {

    public double vertical, horizontal;

    public Velocity() {
        super("Velocity", false);
        this.vertical = 0.0;
        this.horizontal = 0.0;
    }

    @Override
    public void insertElement() {
        this.jsonObject = new JsonObject();
        jsonObject.addProperty("toggle", this.toggle);
        jsonObject.addProperty("vertical", (double) this.vertical);
        jsonObject.addProperty("horizontal", (double) this.horizontal);
        jsonObject.addProperty("key", key);
    }

    @Override
    public void readJsonObject() throws NullPointerException {
        this.toggle = jsonObject.get("toggle").getAsBoolean();
        this.vertical = jsonObject.get("vertical").getAsDouble();
        this.horizontal = jsonObject.get("horizontal").getAsDouble();
        this.key = jsonObject.get("key").getAsInt();
    }

}
