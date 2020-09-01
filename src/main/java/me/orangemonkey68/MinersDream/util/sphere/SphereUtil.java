package me.orangemonkey68.MinersDream.util.sphere;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SphereUtil {
    //Make it static
    private SphereUtil(){}

    //A cache of all the generated radii. This could be done better, probably.
    //It's also possible to just use a big list since the radii of the ProspectingPicks don't change
    //however this is more dynamic and responsive despite the static data.
    private static final HashMap<Integer, List<BlockPos>> cache = new HashMap<>();

    //generates a sphere centered around 0, 0, 0 with dimensions diameter*diameter*diameter
    public static List<BlockPos> generateSphere(int radius){
        List<BlockPos> blockPoses = new ArrayList<>();

        //Gets sphere from cache instead of generating a new one
        if(cache.containsKey(radius)){
            System.out.println("Getting sphere with radius %r from cache".replace("%r", String.valueOf(radius)));

            return cache.get(radius);
        } else {
            for(int x = -radius; x <= radius; x++){
                for(int y = -radius; y <= radius; y++){
                    for(int z = -radius; z <= radius; z++){
                        int distance = x*x + y*y + z*z;
                        if(radius*radius >= distance){
                            blockPoses.add(new BlockPos(x, y, z));
                        }
                    }
                }
            }

            //caches result
            cache.put(radius, blockPoses);

            return blockPoses;
        }
    }

    //gets blocks in world in a sphere shape, from cache if possible
    public static List<Block> getBlocksInSphere(BlockPos center, int radius, World world){
        List<Block> blocks = new ArrayList<>();

        //gets cached sphere, then adds each block's position to the center
        if(cache.containsKey(radius)){
            cache.get(radius).forEach(pos -> blocks.add(world.getBlockState(pos.add(center)).getBlock()));
        } else {
            //generates sphere then iterates over it
            generateSphere(radius).forEach(pos -> blocks.add(world.getBlockState(pos.add(center)).getBlock()));
        }

        return blocks;
    }
}
