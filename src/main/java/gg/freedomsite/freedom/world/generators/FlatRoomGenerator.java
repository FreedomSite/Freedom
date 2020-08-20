package gg.freedomsite.freedom.world.generators;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

import java.util.Random;

public class FlatRoomGenerator extends ChunkGenerator
{

    int currentHeight = 50;

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        //SimplexOctaveGenerator generator = new SimplexOctaveGenerator(new Random(world.getSeed()), 8);
        ChunkData chunk = createChunkData(world);
        //generator.setScale(0.005D);

        for (int X = 0; X < 16; X++)
        {
            for (int Z = 0; Z < 16; Z++)
            {
                //currentHeight = (int) ((generator.noise(x*16+X, z*16+Z, 0.5D, 0.5D, true)+1)*15D+50D);
                chunk.setBlock(X, currentHeight, Z, Material.GRASS_BLOCK);
                chunk.setBlock(X, currentHeight-1, Z, Material.DIRT);
                chunk.setBlock(X, currentHeight-2, Z, Material.DIRT);
                chunk.setBlock(X, currentHeight-3, Z, Material.DIRT);
                chunk.setBlock(X, currentHeight-4, Z, Material.DIRT);
                chunk.setBlock(X, currentHeight-5, Z, Material.DIRT);
                chunk.setBlock(X, currentHeight-6, Z, Material.DIRT);
                chunk.setBlock(X, currentHeight-7, Z, Material.DIRT);
                chunk.setBlock(X, currentHeight-8, Z, Material.STONE);
                chunk.setBlock(X, currentHeight-9, Z, Material.STONE);
                chunk.setBlock(X, currentHeight-10, Z, Material.STONE);
                chunk.setBlock(X, currentHeight-11, Z, Material.STONE);
                chunk.setBlock(X, currentHeight-12, Z, Material.STONE);
                chunk.setBlock(X, currentHeight-13, Z, Material.STONE);
                chunk.setBlock(X, currentHeight-14, Z, Material.STONE);
                chunk.setBlock(X, currentHeight-15, Z, Material.STONE);
                chunk.setBlock(X, currentHeight-16, Z, Material.STONE);
                chunk.setBlock(X, currentHeight-17, Z, Material.BEDROCK);


            }
        }
        return chunk;
    }

}
