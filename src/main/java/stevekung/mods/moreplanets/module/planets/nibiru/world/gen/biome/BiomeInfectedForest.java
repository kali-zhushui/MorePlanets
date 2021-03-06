package stevekung.mods.moreplanets.module.planets.nibiru.world.gen.biome;

import static net.minecraftforge.common.BiomeDictionary.Type.DEAD;
import static net.minecraftforge.common.BiomeDictionary.Type.FOREST;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import stevekung.mods.moreplanets.module.planets.nibiru.blocks.NibiruBlocks;
import stevekung.mods.moreplanets.module.planets.nibiru.world.gen.feature.WorldGenInfectedBigTree;
import stevekung.mods.moreplanets.module.planets.nibiru.world.gen.feature.WorldGenInfectedTrees;
import stevekung.mods.moreplanets.module.planets.nibiru.world.gen.feature.WorldGenInfectedVinesDirt;
import stevekung.mods.moreplanets.util.helper.CommonRegisterHelper;
import stevekung.mods.moreplanets.util.helper.DecorateHelper;

public class BiomeInfectedForest extends BiomeNibiru
{
    public BiomeInfectedForest(BiomeProperties properties)
    {
        super(properties);
        this.topBlock = NibiruBlocks.INFECTED_GRASS.getDefaultState();
        this.fillerBlock = NibiruBlocks.INFECTED_DIRT.getDefaultState();
        this.stoneBlock = NibiruBlocks.NIBIRU_BLOCK.getDefaultState();
        this.getBiomeDecorator().infectedTallGrassPerChunk = 2;
        this.getBiomeDecorator().infectedTreesPerChunk = 10;
        this.getBiomeDecorator().orangeBushPerChunk = 3;
        this.getBiomeDecorator().reedsPerChunk = 10;
        this.decorator.treesPerChunk = -999;
    }

    @Override
    public void registerTypes(Biome biome)
    {
        CommonRegisterHelper.registerBiomeType(biome, FOREST, DEAD);
    }

    @Override
    public void decorate(World world, Random rand, BlockPos pos)
    {
        super.decorate(world, rand, pos);

        if (rand.nextInt(25) == 0)
        {
            new WorldGenInfectedVinesDirt().generate(world, rand, DecorateHelper.getSimplePos(world, pos, rand));
        }
    }

    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
        if (rand.nextInt(10) == 0)
        {
            return new WorldGenInfectedBigTree(true, NibiruBlocks.NIBIRU_LOG, 0, NibiruBlocks.NIBIRU_LEAVES, 0);
        }
        else if (rand.nextInt(20) == 0)
        {
            return rand.nextInt(5) == 0 ? new WorldGenInfectedBigTree(false, NibiruBlocks.NIBIRU_LOG, 0, NibiruBlocks.NIBIRU_LEAVES, 0) : new WorldGenInfectedTrees(false, NibiruBlocks.NIBIRU_LOG.getDefaultState(), NibiruBlocks.NIBIRU_LEAVES.getDefaultState());
        }
        else
        {
            return new WorldGenInfectedTrees(true, NibiruBlocks.NIBIRU_LOG.getDefaultState(), NibiruBlocks.NIBIRU_LEAVES.getDefaultState());
        }
    }
}