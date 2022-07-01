package io.github.maxmilite.maxutil.module.blatant;

import com.google.gson.JsonObject;
import io.github.maxmilite.maxutil.module.Module;
import io.github.maxmilite.maxutil.util.MathUtil;
import net.minecraft.util.math.Vec2f;

import java.util.Objects;

import static io.github.maxmilite.maxutil.client.MaxUtilClient.mc;

public class Launch extends Module {

    public double speed;
    public double height;

    public Launch() {
        super("Launch", false);
        this.speed = 8.0;
        this.height = 1.0;
    }

    @Override
    public void insertElement() {
        this.jsonObject = new JsonObject();
        jsonObject.addProperty("toggle", this.toggle);
        jsonObject.addProperty("speed", this.speed);
        jsonObject.addProperty("height", this.height);
        jsonObject.addProperty("key", key);
    }

    @Override
    public void readJsonObject() throws NullPointerException {
        this.toggle = jsonObject.get("toggle").getAsBoolean();
        this.height = jsonObject.get("height").getAsDouble();
        this.key = jsonObject.get("key").getAsInt();
    }

    public void delay(double v) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        MessageEvent.sendInfo("Launching...");
        while (Objects.requireNonNull(mc.player).getPos().y > v + 1e-2) {
//            MessageEvent.sendInfo(Objects.requireNonNull(mc.player).getPos().y + " " + (v + 1e-2));
            try {
                //noinspection BusyWait
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
//        MessageEvent.sendInfo("Launch succeeded.");
        this.toggle = false;
    }

    @Override
    public void switchToggle() {
        this.toggle = !this.toggle;
        if (this.toggle) {
            Vec2f vec2f = MathUtil.calcVector(speed);
            double v = Objects.requireNonNull(mc.player).getPos().y;
            Objects.requireNonNull(mc.player).setVelocity(vec2f.x, height, vec2f.y);
            new Thread(() -> delay(v)).start();
        }
    }
}
