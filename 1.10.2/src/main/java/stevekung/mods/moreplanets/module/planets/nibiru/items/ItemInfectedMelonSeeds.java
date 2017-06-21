package stevekung.mods.moreplanets.module.planets.nibiru.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import stevekung.mods.moreplanets.module.planets.nibiru.blocks.NibiruBlocks;
import stevekung.mods.moreplanets.util.items.EnumSortCategoryItem;
import stevekung.mods.moreplanets.util.items.ItemBaseMP;

public class ItemInfectedMelonSeeds extends ItemBaseMP
{
    public ItemInfectedMelonSeeds(String name)
    {
        this.setUnlocalizedName(name);
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (side != EnumFacing.UP)
        {
            return false;
        }
        else if (!player.canPlayerEdit(pos.offset(side), side, itemStack))
        {
            return false;
        }
        else if (world.getBlockState(pos).getBlock() == NibiruBlocks.INFECTED_FARMLAND && world.isAirBlock(pos.up()))
        {
            world.setBlockState(pos.up(), NibiruBlocks.INFECTED_MELON_STEM.getDefaultState());
            --itemStack.stackSize;
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public EnumSortCategoryItem getItemCategory(int meta)
    {
        return EnumSortCategoryItem.PLANT_SEEDS;
    }

    @Override
    public String getName()
    {
        return "infected_melon_seeds";
    }
}