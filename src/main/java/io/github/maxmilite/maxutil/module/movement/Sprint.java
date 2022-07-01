package io.github.maxmilite.maxutil.module.movement;

import com.google.gson.JsonObject;
import io.github.maxmilite.maxutil.module.Module;
import net.minecraft.client.MinecraftClient;

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", false);
        this.allDirections = false;
    }

    public boolean allDirections;

    public boolean isAllDirections() {
        return allDirections;
    }

    public void setAllDirections(boolean allDirections) {
        this.allDirections = allDirections;
    }

    MinecraftClient mc = MinecraftClient.getInstance();


    @Override
    public void insertElement() {
        this.jsonObject = new JsonObject();
        jsonObject.addProperty("toggle", this.toggle);
        jsonObject.addProperty("alldirections", this.allDirections);
        jsonObject.addProperty("key", key);
    }
    @Override
    public void readJsonObject() throws NullPointerException {
        this.toggle = jsonObject.get("toggle").getAsBoolean();
        this.allDirections = jsonObject.get("alldirections").getAsBoolean();
        this.key = jsonObject.get("key").getAsInt();
    }

}
