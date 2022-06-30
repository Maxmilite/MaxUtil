package io.github.maxmilite.maxutil.mixin;


import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.net.URI;

@Mixin(TitleScreen.class)
public class MUTitleScreenMixin extends Screen {

    protected MUTitleScreenMixin(Text title) {
        super(title);
    }

    public void drawCredit() {
        this.clearChildren();
        Text info1 = Text.literal("The MaxUtil Client is created by Maxmilite.");
        Text info2 = Text.literal("Modification is permitted.");
        Text info3 = Text.literal("View more information on github.com/Maxmilite.");
        int width1 = this.textRenderer.getWidth(info1);
        int width2 = this.textRenderer.getWidth(info2);
        int width3 = this.textRenderer.getWidth(info3);
        PressableTextWidget text1 = new PressableTextWidget(this.width / 2 - width1 / 2, this.height / 2 - 10, width1, 10, info1, (button) -> {
        }, this.textRenderer);
        PressableTextWidget text2 = new PressableTextWidget(this.width / 2 - width2 / 2, this.height / 2, width2, 10, info2, (button) -> {
        }, this.textRenderer);
        PressableTextWidget text3 = new PressableTextWidget(this.width / 2 - width3 / 2, this.height / 2 + 10, width3, 10, info3, (button) -> {
            try {
                String url = "https://github.com/Maxmilite";
                URI uri = URI.create(url);
                Desktop dp = Desktop.getDesktop();
                if (dp.isSupported(Desktop.Action.BROWSE)) {
                    dp.browse(uri);
                } else {
                    throw new NullPointerException();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, this.textRenderer);
        this.addDrawableChild(text1);
        this.addDrawableChild(text2);
        this.addDrawableChild(text3);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.remove(text1);
        this.remove(text2);
        this.remove(text3);
        this.init();
    }

    @Inject(method = "init", at = @At("TAIL"))
    protected void init(CallbackInfo callbackInfo) {
//        List<? extends Element> list = this.children();
        Text info = Text.literal("MaxUtil Client | Created by Maxmilite");
        this.addDrawableChild(new PressableTextWidget(this.width - this.textRenderer.getWidth(info) - 2,
                this.height - 20, this.textRenderer.getWidth(info), 10, info, (button) -> {
            Thread thread = new Thread(this::drawCredit);
            thread.start();
        }, this.textRenderer));
    }
}
