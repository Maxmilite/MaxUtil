package io.github.maxmilite.maxutil.events;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.Objects;

public class MessageEvent {

    public static MinecraftClient mc = MinecraftClient.getInstance();

    public static String info = "§7[§3MaxUtil§7] §e";

    public static String error = "§7[§3MaxUtil§7] §4Error: §c";

    public static void sendInfo(String x) {
        Objects.requireNonNull(mc.player).sendMessage(Text.of(info + x));
    }

    public static void sendError(String x) {
        Objects.requireNonNull(mc.player).sendMessage(Text.of(error + x));
    }

}
