package me.orangemonkey68.MinersDream.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.TorchBlock;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class AutonomousTorchBlock extends TorchBlock {
    public AutonomousTorchBlock(Settings settings, ParticleEffect particleEffect) {
        super(settings, particleEffect);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        double d = (double)pos.getX() + 0.5D;
        double e = (double)pos.getY() + 0.7D;
        double f = (double)pos.getZ() + 0.5D;
        world.addParticle(new DustParticleEffect(230, 0, 0, 1f), d, e, f, 0, 0, 0);
    }
}
