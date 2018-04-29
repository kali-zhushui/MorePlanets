package stevekung.mods.moreplanets.core.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.CreateFluidSourceEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import stevekung.mods.moreplanets.init.MPBiomes;
import stevekung.mods.moreplanets.init.MPPotions;
import stevekung.mods.moreplanets.module.planets.chalos.blocks.ChalosBlocks;
import stevekung.mods.moreplanets.module.planets.diona.blocks.DionaBlocks;
import stevekung.mods.moreplanets.module.planets.diona.items.DionaItems;
import stevekung.mods.moreplanets.module.planets.fronos.blocks.BlockFronosDirt;
import stevekung.mods.moreplanets.module.planets.fronos.blocks.FronosBlocks;
import stevekung.mods.moreplanets.module.planets.nibiru.blocks.NibiruBlocks;
import stevekung.mods.moreplanets.module.planets.nibiru.items.NibiruItems;
import stevekung.mods.moreplanets.network.PacketSimpleMP;
import stevekung.mods.moreplanets.network.PacketSimpleMP.EnumSimplePacketMP;
import stevekung.mods.moreplanets.utils.blocks.IFireBlock;
import stevekung.mods.stevekunglib.utils.CachedEnum;

public class GeneralEventHandler
{
    private static final List<BreakBlockData> INFECTED_BLOCK_LIST = new ArrayList<>();

    static
    {
        GeneralEventHandler.INFECTED_BLOCK_LIST.add(new BreakBlockData(NibiruBlocks.JUICER_EGG));
        GeneralEventHandler.INFECTED_BLOCK_LIST.add(new BreakBlockData(NibiruBlocks.OIL_ORE));
        GeneralEventHandler.INFECTED_BLOCK_LIST.add(new BreakBlockData(NibiruBlocks.SPORELILY));
    }

    @SubscribeEvent
    public void onFuelBurnTime(FurnaceFuelBurnTimeEvent event)
    {
        Item item = event.getItemStack().getItem();
        Block block = Block.getBlockFromItem(item);

        if (block == ChalosBlocks.CHEESE_SPORE_FLOWER || block == NibiruBlocks.NIBIRU_SAPLING)
        {
            event.setBurnTime(100);
        }
        if (item == DionaItems.INFECTED_CRYSTALLIZED_SHARD)
        {
            event.setBurnTime(400);
        }
        if (item == NibiruItems.INFECTED_COAL || item == NibiruItems.INFECTED_CHARCOAL)
        {
            event.setBurnTime(1600);
        }
    }

    @SubscribeEvent
    public void onCreateFluidSource(CreateFluidSourceEvent event)
    {
        Block block = event.getState().getBlock();

        if (block == DionaBlocks.CRYSTALLIZED_WATER_FLUID_BLOCK || block == ChalosBlocks.CHEESE_MILK_FLUID_BLOCK || block == NibiruBlocks.INFECTED_WATER_FLUID_BLOCK)
        {
            event.setResult(Result.ALLOW);
        }
    }

    @SubscribeEvent
    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event)
    {
        BlockPos firePos = event.getPos().offset(event.getFace());

        if (event.getWorld().getBlockState(firePos).getBlock() instanceof IFireBlock)
        {
            GalacticraftCore.packetPipeline.sendToServer(new PacketSimpleMP(EnumSimplePacketMP.S_FIRE_EXTINGUISH, GCCoreUtil.getDimensionID(event.getWorld()), firePos));
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event)
    {
        EntityPlayer player = event.getEntityPlayer();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        ItemStack heldItem = event.getItemStack();

        if (!heldItem.isEmpty() && (heldItem.getItem() instanceof ItemSpade || heldItem.getItem().getToolClasses(heldItem) == Collections.singleton("shovel")))
        {
            if (event.getFace() != EnumFacing.DOWN && world.getBlockState(pos.up()).getMaterial() == Material.AIR)
            {
                if (world.getBlockState(pos).getBlock() == NibiruBlocks.INFECTED_GRASS_BLOCK)
                {
                    if (!world.isRemote)
                    {
                        world.playSound(null, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        world.setBlockState(pos, NibiruBlocks.NIBIRU_GRASS_PATH.getDefaultState(), 11);
                        heldItem.damageItem(1, player);
                    }
                    player.swingArm(event.getHand());
                }
                else if (world.getBlockState(pos).getBlock() == NibiruBlocks.GREEN_VEIN_GRASS_BLOCK)
                {
                    if (!world.isRemote)
                    {
                        world.playSound(null, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        world.setBlockState(pos, NibiruBlocks.NIBIRU_GRASS_PATH.getStateFromMeta(1), 11);
                        heldItem.damageItem(1, player);
                    }
                    player.swingArm(event.getHand());
                }
            }
        }
    }

    @SubscribeEvent
    public void onBreakSpeed(BreakSpeed event)
    {
        Block block = event.getState().getBlock();
        EntityPlayer player = event.getEntityPlayer();

        if (this.isShears(player))
        {
            if (block == FronosBlocks.CANDY_CANE_1 || block == FronosBlocks.CANDY_CANE_2)
            {
                event.setNewSpeed(7.5F);
            }
        }
    }

    @SubscribeEvent
    public void onBlockBreak(BreakEvent event)
    {
        IBlockState sourceState = event.getState();
        Block source = sourceState.getBlock();
        EntityPlayer player = event.getPlayer();

        if (source == NibiruBlocks.INFECTED_FARMLAND && event.getWorld().getBiomeForCoordsBody(event.getPos()) == MPBiomes.GREEN_VEIN)
        {
            return;
        }

        GeneralEventHandler.INFECTED_BLOCK_LIST.forEach(data ->
        {
            Block block = data.getBlock();

            if (source == block && !player.isPotionActive(MPPotions.INFECTED_SPORE_PROTECTION) && !player.capabilities.isCreativeMode)
            {
                player.addPotionEffect(new PotionEffect(MPPotions.INFECTED_SPORE, 60));
            }
        });

        if (source.getRegistryName().toString().startsWith("moreplanets"))
        {
            String sourceName = source.getUnlocalizedName().substring(5);

            if (sourceName.contains("infected_crystallized"))
            {
                return;
            }
            else
            {
                if (sourceName.contains("infected") || sourceName.contains("nibiru"))
                {
                    if (!player.isPotionActive(MPPotions.INFECTED_SPORE_PROTECTION) && !player.capabilities.isCreativeMode)
                    {
                        player.addPotionEffect(new PotionEffect(MPPotions.INFECTED_SPORE, 60));
                    }
                }
            }
        }
        if (this.isShears(player))
        {
            if (source == FronosBlocks.CANDY_CANE_1 || source == FronosBlocks.CANDY_CANE_2)
            {
                player.getActiveItemStack().damageItem(1, player);
            }
        }
    }

    @SubscribeEvent //TODO advancement
    public void onPickupItem(ItemPickupEvent event)
    {
        ItemStack itemStack = event.getOriginalEntity().getItem();
        Item item = itemStack.getItem();
        Block block = Block.getBlockFromItem(item);

        if (block == ChalosBlocks.CHEESE_SPORE_STEM || block == NibiruBlocks.NIBIRU_LOG)
        {
            //            event.player.addStat(AchievementList.MINE_WOOD);
        }
    }

    @SubscribeEvent //TODO advancement
    public void onCraftItem(ItemCraftedEvent event)
    {
        Item item = event.crafting.getItem();
        Block block = Block.getBlockFromItem(item);

        if (block == ChalosBlocks.CHEESE_SPORE_CRAFTING_TABLE || block == NibiruBlocks.NIBIRU_CRAFTING_TABLE)
        {
            //            event.player.addStat(AchievementList.BUILD_WORK_BENCH);
        }
        if (item == NibiruItems.NIBIRU_STONE_PICKAXE)
        {
            //            event.player.addStat(AchievementList.BUILD_BETTER_PICKAXE);
        }
        if (block == NibiruBlocks.NIBIRU_FURNACE || block == NibiruBlocks.TERRASTONE_FURNACE)
        {
            //            event.player.addStat(AchievementList.BUILD_FURNACE);
        }
    }

    @SubscribeEvent
    public void onUseHoe(UseHoeEvent event)
    {
        if (event.getResult() != Result.DEFAULT || event.isCanceled())
        {
            return;
        }

        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (world.isAirBlock(pos.up()))
        {
            if (block == ChalosBlocks.CHEESE_DIRT || block == ChalosBlocks.CHEESE_COARSE_DIRT || block == ChalosBlocks.CHEESE_GRASS_BLOCK)
            {
                this.setFarmland(event, world, pos, state, ChalosBlocks.CHEESE_COARSE_DIRT, ChalosBlocks.CHEESE_DIRT, ChalosBlocks.CHEESE_FARMLAND);
            }
            //            else if (block == ChalosBlocks.CHEESE_GRASS)
            //            {
            //                this.setFarmland(event, world, pos, ChalosBlocks.CHEESE_FARMLAND);
            //            }
            else if (block == NibiruBlocks.INFECTED_DIRT || block == NibiruBlocks.INFECTED_COARSE_DIRT)
            {
                this.setFarmland(event, world, pos, state, NibiruBlocks.INFECTED_COARSE_DIRT, NibiruBlocks.INFECTED_DIRT, NibiruBlocks.INFECTED_FARMLAND);
            }
            else if (block == NibiruBlocks.INFECTED_GRASS_BLOCK || block == NibiruBlocks.GREEN_VEIN_GRASS_BLOCK)
            {
                this.setFarmland(event, world, pos, NibiruBlocks.INFECTED_FARMLAND);
            }
            else if (block == FronosBlocks.FRONOS_GRASS)
            {
                this.setFarmland(event, world, pos, FronosBlocks.FRONOS_FARMLAND);
            }
            else if (block == FronosBlocks.FRONOS_DIRT)
            {
                this.setFarmland(event, world, pos, state, BlockFronosDirt.VARIANT, BlockFronosDirt.BlockType.FRONOS_COARSE_DIRT, FronosBlocks.FRONOS_DIRT, FronosBlocks.FRONOS_FARMLAND);
            }
        }
    }

    private void setFarmland(UseHoeEvent event, World world, BlockPos pos, IBlockState state, Block coarse, Block dirt, Block farmland)
    {
        if (state.getBlock() == coarse)
        {
            world.setBlockState(pos, dirt.getDefaultState());
        }
        else
        {
            world.setBlockState(pos, farmland.getDefaultState());
        }

        event.setResult(Result.ALLOW);
        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundType.GROUND.getStepSound(), SoundCategory.BLOCKS, (SoundType.GROUND.getVolume() + 1.0F) / 2.0F, SoundType.GROUND.getPitch() * 0.8F);

        for (EnumHand hand : CachedEnum.handValues)
        {
            event.getEntityPlayer().swingArm(hand);
        }
    }

    @Deprecated //TODO Remove 1.13
    private void setFarmland(UseHoeEvent event, World world, BlockPos pos, IBlockState state, IProperty<?> property, Object value, Block dirt, Block farmland)
    {
        if (state.getValue(property) == value)
        {
            world.setBlockState(pos, dirt.getDefaultState());
        }
        else
        {
            world.setBlockState(pos, farmland.getDefaultState());
        }

        event.setResult(Result.ALLOW);
        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundType.GROUND.getStepSound(), SoundCategory.BLOCKS, (SoundType.GROUND.getVolume() + 1.0F) / 2.0F, SoundType.GROUND.getPitch() * 0.8F);

        for (EnumHand hand : CachedEnum.handValues)
        {
            event.getEntityPlayer().swingArm(hand);
        }
    }

    @Deprecated //TODO Remove 1.13
    private void setFarmland(UseHoeEvent event, World world, BlockPos pos, Block farmland)
    {
        world.setBlockState(pos, farmland.getDefaultState());
        event.setResult(Result.ALLOW);
        world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundType.GROUND.getStepSound(), SoundCategory.BLOCKS, (SoundType.GROUND.getVolume() + 1.0F) / 2.0F, SoundType.GROUND.getPitch() * 0.8F);

        for (EnumHand hand : CachedEnum.handValues)
        {
            event.getEntityPlayer().swingArm(hand);
        }
    }

    private boolean isShears(EntityPlayer player)
    {
        return !player.getActiveItemStack().isEmpty() && player.getActiveItemStack().getItem() instanceof ItemShears;
    }

    static class BreakBlockData
    {
        private Block block;

        public BreakBlockData(Block block)
        {
            this.block = block;
        }

        public Block getBlock()
        {
            return this.block;
        }
    }
}