package io.github.maxmilite.maxutil.module;

import com.google.gson.JsonObject;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class Module {
    private final String name;
    public boolean toggle;
    public String mode;
    public double value;
    public int key;
    public List<String> modeList;
    public JsonObject jsonObject;

    public String getMode() {
        return this.mode;
    }


    public Module(String name, boolean toggle) {
        this.name = name;
        this.toggle = toggle;
        this.value = 0.0;
        this.mode = "NULL";
        this.key = GLFW.GLFW_KEY_UNKNOWN;
        insertElement();
    }

    public void insertElement() {
        this.jsonObject = new JsonObject();
        jsonObject.addProperty("toggle", this.toggle);
        jsonObject.addProperty("mode", this.mode);
        jsonObject.addProperty("value", this.value);
        jsonObject.addProperty("key", key);
    }

    public void readJsonObject() {
        this.toggle = jsonObject.get("toggle").getAsBoolean();
        this.mode = jsonObject.get("mode").getAsString();
        this.value = jsonObject.get("value").getAsDouble();
        this.key = jsonObject.get("key").getAsInt();
    }

    public String getName() {
        return this.name;
    }

    public int setMode(String arg) {
        arg = arg.toUpperCase();
        if (modeList.contains(arg)) {
            this.mode = arg;
            return 0;
        }
        else
            return 1;
    }

    public double getValue() {
        return this.value;
    }

    public int setValue(double arg) {
        this.value = arg;
        return 0;
    }

    public boolean isToggle() {
        return this.toggle;
    }

    public void switchToggle() {
        this.toggle = !this.toggle;
    }
}
