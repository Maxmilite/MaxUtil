package io.github.maxmilite.maxutil.events;

import io.github.maxmilite.maxutil.client.MaxUtilClient;
import io.github.maxmilite.maxutil.module.Module;
import io.github.maxmilite.maxutil.util.KeyUtil;
import io.github.maxmilite.maxutil.util.MathUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec2f;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

public class CommandEvent {

    public static String DECIMAL_REGEX = "^[-0-9.]+";
    public static String DIGIT_REGEX = "^[-0-9]+";

    public static void onCommand(String message, @Nullable Text ignoredPreview, CallbackInfo info) {

        if (message.indexOf(":MU") == 0) {
            info.cancel();
            String[] args = message.split(" ");

            if (args.length <= 1) {
                MessageEvent.sendError("Invalid command.");
                return;
            }

            args[1] = args[1].toLowerCase();

            switch (args[1]) {
                case "set":
                    if (args.length != 5)
                        MessageEvent.sendError("Invalid command");
                    else {
                        args[3] = args[3].toLowerCase();
                        if (MaxUtilClient.manager.getModule(args[2]) != null) {
                            Module module = MaxUtilClient.manager.getModule(args[2]);
                            module.insertElement();
                            if (args[4].matches(DIGIT_REGEX))
                                module.jsonObject.addProperty(args[3], Integer.valueOf(args[4]));
                            else if (args[4].matches(DECIMAL_REGEX))
                                module.jsonObject.addProperty(args[3], Double.valueOf(args[4]));
                            else {
                                args[4] = args[4].toUpperCase();
                                module.jsonObject.addProperty(args[3], args[4].toUpperCase());
                            }
                            module.readJsonObject();
                            MaxUtilClient.config.writeConfig();
                            MessageEvent.sendInfo("Succeed in setting the §b" + args[3] + "§e value of §b" + MaxUtilClient.manager.getModule(args[2]).getName() + "§e to §b" + args[4]);
                        } else {
                            MessageEvent.sendError("Invalid command.");
                        }
                    }
                    break;

                case "toggle":

                case "t":
                    if (args.length != 3)
                        MessageEvent.sendError("Invalid command.");
                    else {
                        if (MaxUtilClient.manager.getModule(args[2]) != null) {
                            MaxUtilClient.manager.getModule(args[2]).switchToggle();
                            MaxUtilClient.config.writeConfig();
                            if (MaxUtilClient.manager.getModule(args[2]).isToggle())
                                MessageEvent.sendInfo("§b" + MaxUtilClient.manager.getModule(args[2]).getName() + "§e was enabled.");
                            else
                                MessageEvent.sendInfo("§b" + MaxUtilClient.manager.getModule(args[2]).getName() + "§e was disabled.");
                        } else {
                            MessageEvent.sendError("Invalid command.");
                        }
                    }
                    break;

                case "bind":
                    if (args.length != 4)
                        MessageEvent.sendError("Invalid command");
                    else {
                        if (MaxUtilClient.manager.getModule(args[2]) != null) {
                            Module module = MaxUtilClient.manager.getModule(args[2]);
                            args[3] = args[3].toUpperCase();
                            int index = KeyUtil.getKey(args[3]);
                            MaxUtilClient.manager.getModule(args[2]).jsonObject.addProperty("key", index);
                            module.readJsonObject();
                            MaxUtilClient.config.writeConfig();
                            MessageEvent.sendInfo("Succeed in setting the binding key of §b" + MaxUtilClient.manager.getModule(args[2]).getName() + "§e to §b" + (index == -1 ? "UNKNOWN" : args[3]));
                        } else {
                            MessageEvent.sendError("Invalid command.");
                        }
                    }
                    break;

                case "reload":
                    MaxUtilClient.config.readConfig();
                    MessageEvent.sendInfo("Config reloaded.");
                    break;

                case "vclip":
                    if (args.length != 3)
                        MessageEvent.sendError("Invalid command.");
                    else {
                        if (!args[2].matches(DECIMAL_REGEX))
                            MessageEvent.sendError("Invalid command.");
                        else {
                            MinecraftClient mc = MinecraftClient.getInstance();
                            Objects.requireNonNull(mc.player).setPosition(mc.player.getPos().add(0, Double.parseDouble(args[2]), 0));
                            MessageEvent.sendInfo("VClip operation succeeded.");
                        }
                    }
                    break;

                case "hclip":
                    if (args.length != 3)
                        MessageEvent.sendError("Invalid command.");
                    else {
                        if (!args[2].matches(DECIMAL_REGEX))
                            MessageEvent.sendError("Invalid command.");
                        else {
                            double dist = Double.parseDouble(args[2]);
                            Vec2f vec2f = MathUtil.calcVector(dist);
                            MinecraftClient mc = MinecraftClient.getInstance();
                            Objects.requireNonNull(mc.player).setPosition(mc.player.getPos().add(vec2f.x, 0, vec2f.y));
                            MessageEvent.sendInfo("HClip operation succeeded.");
                        }
                    }
                    break;

                default:
                    MessageEvent.sendError("Invalid command.");
                    break;
            }

        }
    }

}
