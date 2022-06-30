package io.github.maxmilite.maxutil.mixin;

import io.github.maxmilite.maxutil.Information;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public class MUWindowTitleMixin {
    @Final @Shadow private long handle;
    @Inject(method = "setTitle", at = @At("TAIL"))
    public void setTitle(CallbackInfo callbackInfo) {
        MinecraftClient mc = MinecraftClient.getInstance();
        String title = "Minecraft " + Information.mcVersion + " | " + Information.clientName + " " + Information.version + " on " + mc.getGameVersion();
        GLFW.glfwSetWindowTitle(this.handle, title);
    }
}