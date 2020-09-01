package me.orangemonkey68.MinersDream.Items;

import net.fabricmc.fabric.mixin.resource.loader.MixinKeyedResourceReloadListener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;

import java.util.Collections;
import java.util.List;

//TODO: Unbind logic
//TODO: Find a way to listen for items in the player's inv, add teleport logic.

public class TranslocationStoneItem extends Item {
    public TranslocationStoneItem(Settings settings) {
        super(settings);
        tooltip = new TranslatableText("item.miners_dream.translocation_stone.tooltip_unbound");
    }

    private Inventory boundInventory;
    private TranslatableText tooltip;

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        BlockPos pos = context.getBlockPos();
        Block block = world.getBlockState(pos).getBlock();

        if(!world.isClient()) {
            Inventory inventory = (Inventory) world.getBlockEntity(pos);

            if(this.boundInventory != null && this.boundInventory == inventory){
                //unbidning logic
                this.boundInventory = null;
                this.tooltip = new TranslatableText("item.miners_dream.translocation_stone.tooltip_unbound");

                if(player != null){
                    player.sendMessage(new TranslatableText("chat.miners_dream.unbound_translocation_stone"), false);
                }

                return ActionResult.SUCCESS;

            } else if (inventory != null){
                //Binding logic
                this.boundInventory = inventory;
                this.tooltip = new TranslatableText("item.miners_dream.translocation_stone.tooltip_bound", pos.getX(), pos.getY(), pos.getZ());

                if(player != null){
                    player.sendMessage(new TranslatableText("chat.miners_dream.bound_translocation_stone", pos.getX(), pos.getY(), pos.getZ()), false);
                }

                return ActionResult.SUCCESS;
            } else return ActionResult.FAIL;


        } else return ActionResult.PASS;
    }

    public void bindInventory(Inventory inventory){
        this.boundInventory = inventory;
    }

    public boolean bindInventory(BlockPos pos, World world){
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if(block.hasBlockEntity()){
            BlockEntity entity = world.getBlockEntity(pos);
            Inventory inventory = (Inventory)entity;
            if(inventory != null){
                //set bound inv
                this.boundInventory = inventory;

                //Change tooltip
                this.tooltip = new TranslatableText("item.miners_dream.translocation_stone.tooltip_bound");

                return true;
            } else return false;
        }else return false;
    }



    Inventory getBoundInventory(){
        return boundInventory;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(this.tooltip);
    }
}
