package io.github.maxmilite.maxutil.events;

import io.github.maxmilite.maxutil.client.MaxUtilClient;
import io.github.maxmilite.maxutil.config.Binding;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;

import static io.github.maxmilite.maxutil.client.MaxUtilClient.mc;

public class KeyEvent {
    public static void keyPress() {

        long handle = MinecraftClient.getInstance().getWindow().getHandle();

        Binding binding = MaxUtilClient.binding;

        if (!mc.mouse.isCursorLocked())
            return;

        for (var i : MaxUtilClient.manager.getModules()) {
            if (i.key < 0)
                continue;

            /*

            if (GLFW.glfwGetKey(handle, i.key) == GLFW.GLFW_PRESS) {
                if (binding.getPressed(i.key) == 0)
                    i.switchToggle();
                binding.setPressed(i.key, 1);
            }
            else if (GLFW.glfwGetKey(handle, i.key) == GLFW.GLFW_RELEASE) {
                binding.setPressed(i.key, 0);
            }


             */

            if (InputUtil.isKeyPressed(handle, i.key)) {
                if (binding.getPressed(i.key) == 0)
                    i.switchToggle();
                binding.setPressed(i.key, 1);
            } else {
                binding.setPressed(i.key, 0);
            }

        }

    }

}
