package stevekung.mods.moreplanets.module.planets.nibiru.client.renderer.entity;

import micdoodle8.mods.galacticraft.planets.mars.client.model.ModelSludgeling;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stevekung.mods.moreplanets.module.planets.nibiru.entity.EntityInfectedWorm;

@SideOnly(Side.CLIENT)
public class RenderInfectedWorm extends RenderLiving<EntityInfectedWorm>
{
    public RenderInfectedWorm(RenderManager render)
    {
        super(render, new ModelSludgeling(), 0.2F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityInfectedWorm entity)
    {
        return new ResourceLocation("moreplanets:textures/entity/infected_worm.png");
    }
}