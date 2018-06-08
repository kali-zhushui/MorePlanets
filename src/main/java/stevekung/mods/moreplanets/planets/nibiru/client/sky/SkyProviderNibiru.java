package stevekung.mods.moreplanets.planets.nibiru.client.sky;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import stevekung.mods.moreplanets.client.renderer.sky.SkyProviderBaseMP;
import stevekung.mods.stevekunglib.utils.client.GLConstants;

public class SkyProviderNibiru extends SkyProviderBaseMP
{
    private static final ResourceLocation DIONA = new ResourceLocation("moreplanets:textures/gui/celestialbodies/diona.png");
    private static final ResourceLocation CHALOS = new ResourceLocation("moreplanets:textures/gui/celestialbodies/chalos.png");
    private static final ResourceLocation LAZENDUS = new ResourceLocation("moreplanets:textures/gui/celestialbodies/lazendus.png");

    public SkyProviderNibiru(float solarSize)
    {
        this.solarSize = 85.0F * solarSize;
        this.hasRain = true;
    }

    @Override
    protected void renderObjects(float partialTicks, WorldClient world, Minecraft mc)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        float starBrightness = world.getStarBrightness(partialTicks);
        float rainStrength = world.getRainStrength(partialTicks);
        float red = 189 / 255.0F;
        float green = 105 / 255.0F;
        float blue = 32 / 255.0F;
        float alpha = 0.4F - rainStrength;
        float scale;
        float baseValue = 0.15F;
        starBrightness = 1.0F - starBrightness;

        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.shadeModel(7425);

        if (alpha < baseValue)
        {
            alpha = baseValue;
        }

        // Render sun aura
        scale = 25.0F;
        buffer.begin(GLConstants.TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(0.0D, 100.0D, 0.0D).color(red * starBrightness, green * starBrightness, blue * starBrightness, alpha * 2 / starBrightness).endVertex();
        buffer.pos(-scale, 100.0D, -scale).color(red * starBrightness, green * starBrightness, blue * starBrightness, 0.0F).endVertex();
        buffer.pos(0, 100.0D, (double) -scale * 1.5F).color(red * starBrightness, green * starBrightness, blue * starBrightness, 0.0F).endVertex();
        buffer.pos(scale, 100.0D, -scale).color(red * starBrightness, green * starBrightness, blue * starBrightness, 0.0F).endVertex();
        buffer.pos((double) scale * 1.5F, 100.0D, 0).color(red * starBrightness, green * starBrightness, blue * starBrightness, 0.0F).endVertex();
        buffer.pos(scale, 100.0D, scale).color(red * starBrightness, green * starBrightness, blue * starBrightness, 0.0F).endVertex();
        buffer.pos(0, 100.0D, (double) scale * 1.5F).color(red * starBrightness, green * starBrightness, blue * starBrightness, 0.0F).endVertex();
        buffer.pos(-scale, 100.0D, scale).color(red * starBrightness, green * starBrightness, blue * starBrightness, 0.0F).endVertex();
        buffer.pos((double) -scale * 1.5F, 100.0D, 0).color(red * starBrightness, green * starBrightness, blue * starBrightness, 0.0F).endVertex();
        buffer.pos(-scale, 100.0D, -scale).color(red * starBrightness, green * starBrightness, blue * starBrightness, 0.0F).endVertex();
        tessellator.draw();

        // Render larger sun aura
        scale = 40.0F;
        buffer.begin(GLConstants.TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(0.0D, 100.0D, 0.0D).color(red * starBrightness, green * starBrightness, blue * starBrightness, alpha * starBrightness).endVertex();
        buffer.pos(-scale, 100.0D, -scale).color(red * starBrightness, green * starBrightness, blue * starBrightness, 0.0F).endVertex();
        buffer.pos(0, 100.0D, (double) -scale * 1.5F).color(red * starBrightness, green * starBrightness, blue * starBrightness, 0.0F).endVertex();
        buffer.pos(scale, 100.0D, -scale).color(red * starBrightness, green * starBrightness, blue * starBrightness, 0.0F).endVertex();
        buffer.pos((double) scale * 1.5F, 100.0D, 0).color(red * starBrightness, green * starBrightness, blue * starBrightness, 0.0F).endVertex();
        buffer.pos(scale, 100.0D, scale).color(red * starBrightness, green * starBrightness, blue * starBrightness, 0.0F).endVertex();
        buffer.pos(0, 100.0D, (double) scale * 1.5F).color(red * starBrightness, green * starBrightness, blue * starBrightness, 0.0F).endVertex();
        buffer.pos(-scale, 100.0D, scale).color(red * starBrightness, green * starBrightness, blue * starBrightness, 0.0F).endVertex();
        buffer.pos((double) -scale * 1.5F, 100.0D, 0).color(red * starBrightness, green * starBrightness, blue * starBrightness, 0.0F).endVertex();
        buffer.pos(-scale, 100.0D, -scale).color(red * starBrightness, green * starBrightness, blue * starBrightness, 0.0F).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.popMatrix();

        this.renderSolar(SkyProviderNibiru.LAZENDUS, this.solarSize, false, true, 4.0F, alpha);
        this.renderObject(1.25F, 80.0F, 200.0F, true, SkyProviderNibiru.CHALOS, alpha, partialTicks);
        this.renderObject(0.5F, 40.0F, 0.0F, true, SkyProviderNibiru.DIONA, alpha, partialTicks);
    }

    @Override
    protected void renderStars(float starBrightness)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, this.hasRain ? starBrightness : this.getStarBrightness());
    }

    @Override
    protected float getStarBrightness()
    {
        return 0.4F;
    }

    @Override
    protected int getStarCount()
    {
        return 50000;
    }

    @Override
    protected double getStarSpreadMultiplier()
    {
        return 150.0D;
    }

    private void renderObject(float scale, float rot1, float rot2, boolean rotate, ResourceLocation resource, float rainStrength, float partialTicks)
    {
        Minecraft mc = Minecraft.getMinecraft();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        GlStateManager.pushMatrix();
        GlStateManager.rotate(rot1, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(rot2, 1.0F, 0.0F, 0.0F);

        if (rotate)
        {
            GlStateManager.rotate(mc.world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
        }

        mc.getTextureManager().bindTexture(resource);
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, rainStrength);
        buffer.begin(GLConstants.QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(-scale, 100.0D, -scale).tex(0.0D, 0.0D).endVertex();
        buffer.pos(scale, 100.0D, -scale).tex(1.0D, 0.0D).endVertex();
        buffer.pos(scale, 100.0D, scale).tex(1.0D, 1.0D).endVertex();
        buffer.pos(-scale, 100.0D, scale).tex(0.0D, 1.0D).endVertex();
        tessellator.draw();
        GlStateManager.popMatrix();
    }

    private void renderSolar(ResourceLocation resource, float scale, boolean renderBlack, boolean renderConceal, float toDivide, float rainStrength)
    {
        Minecraft mc = Minecraft.getMinecraft();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        GlStateManager.pushMatrix();

        if (renderBlack)
        {
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            GlStateManager.disableTexture2D();
            GlStateManager.color(0.0F, 0.0F, 0.0F, 1.0F);
            float blackScale = scale / toDivide;
            buffer.begin(GLConstants.QUADS, DefaultVertexFormats.POSITION);
            buffer.pos(-blackScale, 99.9D, -blackScale).endVertex();
            buffer.pos(blackScale, 99.9D, -blackScale).endVertex();
            buffer.pos(blackScale, 99.9D, blackScale).endVertex();
            buffer.pos(-blackScale, 99.9D, blackScale).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }
        if (renderConceal)
        {
            float blackScale2 = scale / toDivide;// Some blanking to conceal the stars
            GlStateManager.disableTexture2D();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            GlStateManager.color(0.0F, 0.0F, 0.0F, 1.0F);
            buffer.begin(GLConstants.QUADS, DefaultVertexFormats.POSITION);
            buffer.pos(-blackScale2, 99.9D, -blackScale2).endVertex();
            buffer.pos(blackScale2, 99.9D, -blackScale2).endVertex();
            buffer.pos(blackScale2, 99.9D, blackScale2).endVertex();
            buffer.pos(-blackScale2, 99.9D, blackScale2).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
        }

        // render solar
        mc.getTextureManager().bindTexture(resource);
        GlStateManager.color(1.0F, 1.0F, 1.0F, rainStrength);
        buffer.begin(GLConstants.QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(-scale, 100.0D, -scale).tex(0.0D, 0.0D).endVertex();
        buffer.pos(scale, 100.0D, -scale).tex(1.0D, 0.0D).endVertex();
        buffer.pos(scale, 100.0D, scale).tex(1.0D, 1.0D).endVertex();
        buffer.pos(-scale, 100.0D, scale).tex(0.0D, 1.0D).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}