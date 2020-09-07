package me.orangemonkey68.MinersDream.Mixins;

import com.google.common.collect.ImmutableList;
import me.orangemonkey68.MinersDream.Items.TranslocationStoneItem;
import me.orangemonkey68.MinersDream.MinersDreamMod;
import me.orangemonkey68.MinersDream.util.inventory.InventoryUtil;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    @Inject(
            method = "onPlayerCollision",
            at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/entity/player/PlayerEntity;increaseStat(Lnet/minecraft/stat/Stat;I)V"))
    void injectItemPickupListener(PlayerEntity player, CallbackInfo ci){
        if(player instanceof ServerPlayerEntity){
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity)player;

            List<ItemStack> translocatorList = getAllTranslocationStones(player.inventory);
            if(!translocatorList.isEmpty()){
                if(translocatorList.size() > 1){
                    serverPlayer.sendMessage(new TranslatableText("chat.miners_dream.too_many_translocation_stones", translocatorList.size()), false);
                } else {
                    ItemStack stack = ((ItemEntity)(Object)this).getStack();

                    TranslocationStoneItem stone = (TranslocationStoneItem)translocatorList.get(0).getItem();
                    if(MinersDreamMod.oreItems.contains(stack.getItem())){
                        if(InventoryUtil.insertStack(stack, stone.getBoundInventory()) == ItemStack.EMPTY){
                            InventoryUtil.removeCountOfItem(stack.getCount(), stack.getItem(), serverPlayer.inventory);
                        }
                    }
                }
            }
        }
    }



    List<ItemStack> getAllTranslocationStones(PlayerInventory inv){
        List<DefaultedList<ItemStack>> combinedInventory = ImmutableList.of(inv.main, inv.offHand);
        List<ItemStack> translocationStones = new ArrayList<>();

        combinedInventory.forEach(list -> {
            list.forEach(itemStack -> {
                if(itemStack.getItem() instanceof TranslocationStoneItem){
                    translocationStones.add(itemStack);
                }
            });
        });

        return translocationStones;
    }

}
