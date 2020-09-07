package me.orangemonkey68.MinersDream.Items;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

//TODO: store inv location instead of inv itself
//TODO: Use NBT tag for serialized info

//Current issue: Item is not instantiated per itemstack. Because of this, I need to not store info in the item class.

public class TranslocationStoneItem extends Item {
    public TranslocationStoneItem(Settings settings) {
        super(settings);
    }


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        BlockPos pos = context.getBlockPos();
        Block block = world.getBlockState(pos).getBlock();
        ItemStack stack = context.getStack();

        //TODO: rewrite this to put loc of inventory in an NBT tag, use that to get an inv and send the item

        if(!world.isClient()) {
            CompoundTag tag = stack.getOrCreateTag();
            boolean isBound = tag.getBoolean("isBound");

            if(block.hasBlockEntity()){
                BlockEntity blockEntity = world.getBlockEntity(pos);
                if(blockEntity instanceof Inventory){
                    if(isBound){
                        //unbinding logic
                        tag.putBoolean("isBound", false);
                        if(player != null){
                            player.sendMessage(new TranslatableText("chat.miners_dream.unbound_translocation_stone"), false);
                        }
                    } else {
                        //binding logic
                        tag.putBoolean("isBound", true);

                        tag.putDouble("x", pos.getX());
                        tag.putDouble("y", pos.getY());
                        tag.putDouble("z", pos.getZ());

                        if(player != null){
                            player.sendMessage(new TranslatableText("chat.miners_dream.bound_translocation_stone", pos.getX(), pos.getY(), pos.getZ()), false);
                        }
                    }
                    return ActionResult.SUCCESS;

                } else return ActionResult.FAIL;
            } else return ActionResult.FAIL;

        } else return ActionResult.PASS;
    }

    public Inventory getBoundInventory(ServerWorld world, ItemStack translocator){
        if(!world.isClient){
            CompoundTag tag = translocator.getOrCreateTag();
            boolean isBound = tag.getBoolean("isBound");
            if(isBound){
                double x = tag.getDouble("x");
                double y = tag.getDouble("y");
                double z = tag.getDouble("z");
                BlockPos pos = new BlockPos(x, y, z);

                if(world.getBlockState(pos).getBlock().hasBlockEntity()){
                    BlockEntity blockEntity = world.getBlockEntity(pos);
                    if(blockEntity instanceof Inventory){
                        return (Inventory)blockEntity;
                    } else return null;
                } else return null;
            }else return null;
        }else return null;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        CompoundTag tag = stack.getOrCreateTag();
        boolean isBound = tag.getBoolean("isBound");
        if(isBound){
            double x = tag.getDouble("x");
            double y = tag.getDouble("y");
            double z = tag.getDouble("z");

            tooltip.add(new TranslatableText("item.miners_dream.translocation_stone.tooltip_bound", x, y, z));
        } else {
            tooltip.add(new TranslatableText("item.miners_dream.translocation_stone.tooltip_unbound"));
        }
    }
}
