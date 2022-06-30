package io.github.maxmilite.maxutil.mixin;


import com.mojang.blaze3d.systems.RenderSystem;
import io.github.maxmilite.maxutil.client.MaxUtilClient;
import io.github.maxmilite.maxutil.module.render.NoFireRender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(InGameOverlayRenderer.class)
public class MUInGameOverlayMixin {
    /**
     * @author Maxmilite
     * @reason Realize better vision on fire
     */
    @Overwrite
    private static void renderFireOverlay(MinecraftClient client, MatrixStack matrices) {

        boolean enable = MaxUtilClient.manager.getModule("NoFireRender").isToggle();

        float alpha = enable ? ((NoFireRender) MaxUtilClient.manager.getModule("NoFireRender")).getAlpha() : 1F;


        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        RenderSystem.depthFunc(519);
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableTexture();
        Sprite sprite = ModelLoader.FIRE_1.getSprite();
        RenderSystem.setShaderTexture(0, sprite.getAtlas().getId());
        float f = sprite.getMinU();
        float g = sprite.getMaxU();
        float h = (f + g) / 2.0F;
        float i = sprite.getMinV();
        float j = sprite.getMaxV();
        float k = (i + j) / 2.0F;
        float l = sprite.getAnimationFrameDelta();
        float m = MathHelper.lerp(l, f, h);
        float n = MathHelper.lerp(l, g, h);
        float o = MathHelper.lerp(l, i, k);
        float p = MathHelper.lerp(l, j, k);

        for(int r = 0; r < 2; ++r) {
            matrices.push();
            matrices.translate((float)(-(r * 2 - 1)) * 0.24F, -0.30000001192092896, 0.0);
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float)(r * 2 - 1) * 10.0F));
            Matrix4f matrix4f = matrices.peek().getPositionMatrix();
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
            bufferBuilder.vertex(matrix4f, -0.5F, -0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, alpha).texture(n, p).next();
            bufferBuilder.vertex(matrix4f, 0.5F, -0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, alpha).texture(m, p).next();
            bufferBuilder.vertex(matrix4f, 0.5F, 0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, alpha).texture(m, o).next();
            bufferBuilder.vertex(matrix4f, -0.5F, 0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, alpha).texture(n, o).next();
            BufferRenderer.drawWithShader(bufferBuilder.end());
            matrices.pop();
        }

        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.depthFunc(515);
    }

    /**
     * @author Maxmilite
     * @reason Realize better vision in wall
     */
    @Overwrite
    private static void renderInWallOverlay(Sprite sprite, MatrixStack matrices) {

        boolean enable = MaxUtilClient.manager.getModule("NoInWallOverlay").isToggle();

        float alpha = enable ? 0.1F : 1F;

        RenderSystem.setShaderTexture(0, sprite.getAtlas().getId());
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        float l = sprite.getMinU();
        float m = sprite.getMaxU();
        float n = sprite.getMinV();
        float o = sprite.getMaxV();
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
        bufferBuilder.vertex(matrix4f, -1.0F, -1.0F, -0.5F).color(0.1F, 0.1F, 0.1F, alpha).texture(m, o).next();
        bufferBuilder.vertex(matrix4f, 1.0F, -1.0F, -0.5F).color(0.1F, 0.1F, 0.1F, alpha).texture(l, o).next();
        bufferBuilder.vertex(matrix4f, 1.0F, 1.0F, -0.5F).color(0.1F, 0.1F, 0.1F, alpha).texture(l, n).next();
        bufferBuilder.vertex(matrix4f, -1.0F, 1.0F, -0.5F).color(0.1F, 0.1F, 0.1F, alpha).texture(m, n).next();
        BufferRenderer.drawWithShader(bufferBuilder.end());
    }

    @Shadow @Final
    private static Identifier UNDERWATER_TEXTURE;

    /**
     * @author Maxmilite
     * @reason Realize better vision underwater
     */
    @Overwrite
    private static void renderUnderwaterOverlay(MinecraftClient client, MatrixStack matrices) {

        boolean enable = MaxUtilClient.manager.getModule("NoUnderwaterOverlay").isToggle();

        float alpha = enable ? 0F : 1F;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableTexture();
        RenderSystem.setShaderTexture(0, UNDERWATER_TEXTURE);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        BlockPos blockPos = null;
        if (client.player != null) {
            blockPos = new BlockPos(client.player.getX(), client.player.getEyeY(), client.player.getZ());
        }
        float f = 0;
        if (client.player != null) {
            f = LightmapTextureManager.getBrightness(client.player.world.getDimension(), client.player.world.getLightLevel(blockPos));
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(f, f, f, 0.1F);
        float m = -client.player.getYaw() / 64.0F;
        float n = client.player.getPitch() / 64.0F;
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(matrix4f, -1.0F, -1.0F, -0.5F).color(1.0F, 1.0F, 1.0F, alpha).texture(4.0F + m, 4.0F + n).next();
        bufferBuilder.vertex(matrix4f, 1.0F, -1.0F, -0.5F).color(1.0F, 1.0F, 1.0F, alpha).texture(0.0F + m, 4.0F + n).next();
        bufferBuilder.vertex(matrix4f, 1.0F, 1.0F, -0.5F).color(1.0F, 1.0F, 1.0F, alpha).texture(0.0F + m, 0.0F + n).next();
        bufferBuilder.vertex(matrix4f, -1.0F, 1.0F, -0.5F).color(1.0F, 1.0F, 1.0F, alpha).texture(4.0F + m, 0.0F + n).next();
        BufferRenderer.drawWithShader(bufferBuilder.end());
        RenderSystem.disableBlend();
    }
}
