package io.github.maxmilite.maxutil.module.render;

import io.github.maxmilite.maxutil.client.MaxUtilClient;
import io.github.maxmilite.maxutil.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActiveMods extends Module {

    public ActiveMods() {
        super("ActiveMods", true);
    }

    public void render(MatrixStack matrices) {

        if (!this.isToggle())
            return;

        MinecraftClient mc = MinecraftClient.getInstance();

        final Identifier logo = new Identifier("maxutil", "segoepr");
        Style style = Style.EMPTY.withFont(logo);

        mc.textRenderer.draw(matrices, Text.literal("MaxUtil Client").setStyle(style), 3, 3, -1);

        int width = mc.getWindow().getScaledWidth() - 3;

        int cnt = 0, base = 3;

        final Identifier module = new Identifier("minecraft", "default");
        Style moduleStyle = Style.EMPTY.withFont(module);

        List<String> names = new ArrayList<>();
        for (var i : MaxUtilClient.manager.getModules()) {
            if (!i.isToggle() || i.getName().equals("ActiveMods"))
                continue;
            names.add(i.getName());
        }

        names.sort((s1, s2) -> {
//            int num = s1.length() - s2.length();
            int num = mc.textRenderer.getWidth(s2) - mc.textRenderer.getWidth(s1);
            if (num == 0) {
                return s1.compareTo(s2);
            }
            return num;
        });

        if (Objects.requireNonNull(mc.player).getActiveStatusEffects().size() != 0)
            base += 24;

        for (var i : names) {
            float len = mc.textRenderer.getWidth(i);
            mc.textRenderer.draw(matrices, Text.literal(i).setStyle(moduleStyle), width - len, base + 11 * cnt++, -1);
        }
    }
}
