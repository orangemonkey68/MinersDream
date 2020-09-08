package me.orangemonkey68.MinersDream.items;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CircleDebugItem extends Item {
    public CircleDebugItem(Settings settings) {
        super(settings);
    }
    private int radius = 3;

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand){


        if(user.isSneaking() && !world.isClient){
            radius++;
            user.sendMessage(new LiteralText("radius = " + radius), false);
            return TypedActionResult.success(user.getStackInHand(hand));
        }else {
            Vec3d playerPos = user.getPos();
            Vec3d center = new Vec3d(Math.floor(playerPos.x) + 0.5, Math.floor(playerPos.y) + 0.5, Math.floor(playerPos.z) + 0.5);
            if(!world.isClient){
                double bx = center.x;
                double by = center.y;
                double bz = center.z;

                for(double x = bx - radius; x <= bx + radius; x++){
                    for(double y = by - radius; y <= by + radius; y++){
                        for(double z = bz - radius; z <= bz + radius; z++){
                            double distance = ((bx-x)*(bx-x)+(bz-z)*(bz-z)+(by-y)*(by-y));
                            if(distance < radius*radius){
                                world.setBlockState(new BlockPos(x, y, z), Blocks.GOLD_BLOCK.getDefaultState());

                                System.out.println("[" + (x-bx) + ", " + (y-by) + ", " + (z-bz) + "]");
                            }
                        }
                    }
                }
                return TypedActionResult.success(user.getStackInHand(hand));
            }else {
                return TypedActionResult.pass(user.getStackInHand(hand));
            }
        }
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if(attacker.isSneaking() && !attacker.getEntityWorld().isClient){
            if(attacker instanceof PlayerEntity){
                PlayerEntity player = (PlayerEntity)attacker;
                radius += -1;
                player.sendMessage(new LiteralText("radius = " + radius), false);
            }
        }
        return super.postHit(stack, target, attacker);
    }
}
