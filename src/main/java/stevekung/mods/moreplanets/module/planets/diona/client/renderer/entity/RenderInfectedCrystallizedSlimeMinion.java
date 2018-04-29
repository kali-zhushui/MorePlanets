package stevekung.mods.moreplanets.module.planets.diona.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import stevekung.mods.moreplanets.module.planets.diona.client.model.ModelInfectedCrystallizedSlimeBoss;
import stevekung.mods.moreplanets.module.planets.diona.entity.EntityInfectedCrystallizedSlimeMinion;
import stevekung.mods.moreplanets.utils.client.renderer.entity.layer.LayerGlowingTexture;

public class RenderInfectedCrystallizedSlimeMinion extends RenderLiving<EntityInfectedCrystallizedSlimeMinion>
{
    public RenderInfectedCrystallizedSlimeMinion(RenderManager manager)
    {
        super(manager, new ModelInfectedCrystallizedSlimeBoss(), 1.0F);
        this.addLayer(new LayerGlowingTexture(this, "infected_crystallize_slime_boss_glow", true));
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityInfectedCrystallizedSlimeMinion entity)
    {
        return new ResourceLocation("moreplanets:textures/entity/infected_crystallize_slime_boss.png");
    }

    @Override
    public void doRender(EntityInfectedCrystallizedSlimeMinion entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.shadowSize = 0.25F * entity.getSlimeSize();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected void preRenderCallback(EntityInfectedCrystallizedSlimeMinion entity, float partialTicks)
    {
        float f = entity.getSlimeSize();
        float f1 = (entity.prevSquishFactor + (entity.squishFactor - entity.prevSquishFactor) * partialTicks) / (f * 0.5F + 1.0F);
        float f2 = 1.0F / (f1 + 1.0F);
        GlStateManager.scale(f2 * f, 1.0F / f2 * f, f2 * f);
    }
}