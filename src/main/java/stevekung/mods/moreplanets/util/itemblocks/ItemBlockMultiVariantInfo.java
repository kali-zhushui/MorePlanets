package stevekung.mods.moreplanets.util.itemblocks;

import java.util.List;

import javax.annotation.Nullable;

import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stevekung.mods.moreplanets.util.blocks.IBlockDescription;
import stevekung.mods.moreplanets.util.blocks.IBlockVariants;
import stevekung.mods.moreplanets.util.helper.CommonRegisterHelper;

public class ItemBlockMultiVariantInfo extends ItemBlockBaseMP
{
    public ItemBlockMultiVariantInfo(Block block)
    {
        super(block);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, @Nullable World world, List<String> list, ITooltipFlag flag)
    {
        if (this.block instanceof IBlockDescription)
        {
            if (CommonRegisterHelper.isShiftKeyDown())
            {
                ((IBlockDescription)this.block).getDescription().addDescription(itemStack, list);
            }
            else
            {
                list.add(GCCoreUtil.translate("desc.shift_info.name"));
            }
        }
    }

    @Override
    protected String[] getBlockVariantsName()
    {
        if (this.block instanceof IBlockVariants)
        {
            return ((IBlockVariants)this.block).getVariantsName().getNameList();
        }
        return new String[] {};
    }
}