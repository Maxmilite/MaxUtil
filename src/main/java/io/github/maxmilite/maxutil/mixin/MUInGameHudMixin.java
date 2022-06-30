package io.github.maxmilite.maxutil.mixin;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import io.github.maxmilite.maxutil.client.MaxUtilClient;
import io.github.maxmilite.maxutil.module.render.ActiveMods;
import io.github.maxmilite.maxutil.module.render.ScoreBoard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static net.minecraft.client.gui.hud.InGameHud.fill;

@Mixin(InGameHud.class)
public abstract class MUInGameHudMixin {
    @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderOverlay(Lnet/minecraft/util/Identifier;F)V", ordinal = 1))
    private void onRenderPowderedSnowOverlay(Args args) {
        args.set(1, 0.3f);
    }

    @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderOverlay(Lnet/minecraft/util/Identifier;F)V", ordinal = 0))
    private void onRenderPumpkinOverlay(Args args) {
        if (MaxUtilClient.manager.getModule("NoPumpkinRender").isToggle())
            args.set(1, 0.3f);
    }

    @Inject(method = "render", at = @At(value = "TAIL"))
    public void render(MatrixStack matrices, float tickDelta, CallbackInfo info) {
        if (MaxUtilClient.manager.getModule("ActiveMods").isToggle())
            ((ActiveMods) MaxUtilClient.manager.getModule("ActiveMods")).render(matrices);
    }

    @Shadow @Final
    private MinecraftClient client;

    @Shadow
    private int scaledHeight;

    @Shadow
    private int scaledWidth;


    @Shadow public abstract void render(MatrixStack matrices, float tickDelta);

    public TextRenderer getTextRenderer() {
        return this.client.textRenderer;
    }

    /**
     * @author Maxmilite
     * @reason Better scoreboard render
     */
    @Overwrite
    private void renderScoreboardSidebar(MatrixStack matrices, ScoreboardObjective objective) {
        Scoreboard scoreboard = objective.getScoreboard();
        Collection<ScoreboardPlayerScore> collectionRaw = scoreboard.getAllPlayerScores(objective);
        List<ScoreboardPlayerScore> list = collectionRaw.stream().filter((score) -> score.getPlayerName() != null && !score.getPlayerName().startsWith("#")).collect(Collectors.toList());
        Collection<?> collection;
        if (list.size() > 15) {
            collection = Lists.newArrayList(Iterables.skip(list, collectionRaw.size() - 15));
        } else {
            collection = list;
        }

        List<Pair<ScoreboardPlayerScore, Text>> list2 = Lists.newArrayListWithCapacity(collection.size());
        Text text = objective.getDisplayName();
        int i = this.getTextRenderer().getWidth(text);
        int j = i;
        int k = this.getTextRenderer().getWidth(": ");

        ScoreboardPlayerScore scoreboardPlayerScore;
        MutableText text2;
        for(Iterator<?> var11 = collection.iterator(); var11.hasNext(); j = Math.max(j, this.getTextRenderer().getWidth(text2) + k + this.getTextRenderer().getWidth(Integer.toString(scoreboardPlayerScore.getScore())))) {
            scoreboardPlayerScore = (ScoreboardPlayerScore)var11.next();
            Team team = scoreboard.getPlayerTeam(scoreboardPlayerScore.getPlayerName());
            text2 = Team.decorateName(team, Text.literal(scoreboardPlayerScore.getPlayerName()));
            list2.add(Pair.of(scoreboardPlayerScore, text2));
        }

        int var10000 = collection.size();

        float titleAlpha, bgAlpha;

        if (MaxUtilClient.manager.getModule("ScoreBoard").isToggle()) {
            ScoreBoard scoreBoard = (ScoreBoard) MaxUtilClient.manager.getModule("ScoreBoard");
            titleAlpha = (float) scoreBoard.titleAlpha;
            bgAlpha = (float) scoreBoard.bgAlpha;
        } else {
            titleAlpha = 0.4F;
            bgAlpha = 0.3F;
        }

        Objects.requireNonNull(this.getTextRenderer());
        int l = var10000 * 9;
        int m = this.scaledHeight / 2 + l / 3;
        int o = this.scaledWidth - j - 3;
        int p = 0;
        int q = this.client.options.getTextBackgroundColor(bgAlpha);
        int r = this.client.options.getTextBackgroundColor(titleAlpha);

        for (Pair<ScoreboardPlayerScore, Text> scoreboardPlayerScoreTextPair : list2) {
            ++p;
            ScoreboardPlayerScore scoreboardPlayerScore2 = scoreboardPlayerScoreTextPair.getFirst();
            Text text3 = scoreboardPlayerScoreTextPair.getSecond();
            Formatting var31 = Formatting.RED;
            String string = "" + var31 + scoreboardPlayerScore2.getScore();
            Objects.requireNonNull(this.getTextRenderer());
            int t = m - p * 9;
            int u = this.scaledWidth - 3 + 2;
            int var10001 = o - 2;
            Objects.requireNonNull(this.getTextRenderer());
            fill(matrices, var10001, t, u, t + 9, q);
            this.getTextRenderer().draw(matrices, text3, (float) o, (float) t, -1);
            this.getTextRenderer().draw(matrices, string, (float) (u - this.getTextRenderer().getWidth(string)), (float) t, -1);
            if (p == collection.size()) {
                var10001 = o - 2;
                Objects.requireNonNull(this.getTextRenderer());
                fill(matrices, var10001, t - 9 - 1, u, t - 1, r);
                fill(matrices, o - 2, t - 1, u, t, q);
                TextRenderer var32 = this.getTextRenderer();
                float var10003 = (float) (o + j / 2 - i / 2);
                Objects.requireNonNull(this.getTextRenderer());
                var32.draw(matrices, text, var10003, (float) (t - 9), -1);
            }
        }

    }

    @Inject(method = "renderScoreboardSidebar", at = @At("TAIL"), cancellable = true)
    private void noScoreboard(MatrixStack matrixStack, ScoreboardObjective scoreboardObjective, CallbackInfo info) {
        if (MaxUtilClient.manager.getModule("NoScoreboard").isToggle())
            info.cancel();
    }

}
