package io.github.maxmilite.maxutil.module.blatant;

import com.google.gson.JsonObject;
import io.github.maxmilite.maxutil.module.Module;

import java.util.Arrays;

public class NoFall extends Module {

    public NoFall() {
        super("NoFall", false);
        this.modeList = Arrays.asList("SPOOFGROUND", "PACKET");
        this.mode = "PACKET";
        insertElement();
    }

    @Override
    public void insertElement() {
        this.jsonObject = new JsonObject();
        jsonObject.addProperty("toggle", this.toggle);
        jsonObject.addProperty("mode", this.mode);
        jsonObject.addProperty("key", key);
    }
    @Override
    public void readJsonObject() {
        this.toggle = jsonObject.get("toggle").getAsBoolean();
        this.mode = jsonObject.get("mode").getAsString();
        this.key = jsonObject.get("key").getAsInt();
    }

}
