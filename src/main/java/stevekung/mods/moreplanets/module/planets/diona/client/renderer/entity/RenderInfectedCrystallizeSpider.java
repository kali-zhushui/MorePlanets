package stevekung.mods.moreplanets.module.planets.diona.client.renderer.entity;

import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stevekung.mods.moreplanets.module.planets.diona.entity.EntityInfectedCrystallizeSpider;
import stevekung.mods.moreplanets.util.client.renderer.entity.layer.LayerGlowingTexture;

@SideOnly(Side.CLIENT)
public class RenderInfectedCrystallizeSpider extends RenderLiving<EntityInfectedCrystallizeSpider>
{
    public RenderInfectedCrystallizeSpider(RenderManager manager)
    {
        super(manager, new ModelSpider(), 1.0F);
        this.shadowSize *= 0.7F;
        this.addLayer(new LayerGlowingTexture(this, "infected_crystallize_spider_eyes", true));
    }

    @Override
    protected float getDeathMaxRotation(EntityInfectedCrystallizeSpider entity)
    {
        return 180.0F;
    }

    @Override
    protected void preRenderCallback(EntityInfectedCrystallizeSpider entity, float partialTicks)
    {
        GlStateManager.scale(0.7F, 0.7F, 0.7F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityInfectedCrystallizeSpider entity)
    {
        return new ResourceLocation("moreplanets:textures/entity/infected_crystallize_spider.png");
    }
}