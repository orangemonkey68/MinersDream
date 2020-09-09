package me.orangemonkey68.MinersDream.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Random;

public class AutonomousWallTorchBlock extends WallTorchBlock {
    public AutonomousWallTorchBlock(Settings settings, ParticleEffect particleEffect) {
        super(settings, particleEffect);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        Direction direction = (Direction)state.get(FACING).getOpposite();
        double d = (double)pos.getX() + 0.5D;
        double e = (double)pos.getY() + 0.7D;
        double f = (double)pos.getZ() + 0.5D;
        double g = 0.22D;
        double h = 0.27D;
        world.addParticle(new DustParticleEffect(230, 0, 0, 1f), d + 0.27D * (double)direction.getOffsetX(), e + 0.22D, f + 0.27D * (double)direction.getOffsetZ(), 0.0D, 0.0D, 0.0D);
    }
}
