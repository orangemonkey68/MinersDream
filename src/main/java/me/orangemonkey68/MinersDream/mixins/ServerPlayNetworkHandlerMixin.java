package me.orangemonkey68.MinersDream.mixins;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {

    @Shadow public ServerPlayerEntity player;

    @Inject(method = "onPlayerMove", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;updatePositionAndAngles(DDDFF)V", ordinal = 1))
    void injectPlayerMovementListener(PlayerMoveC2SPacket packet, CallbackInfo ci){
//        HashMap<Integer, ItemStack> items = InventoryUtil.getAllOfItem(MinersDreamMod.autonomousTorchBlockItem, player.inventory);
//        BlockPos pos = player.getBlockPos();
//        if(player.world.getBlockState(pos).getLuminance() <= 5 && !items.isEmpty()){
//            ItemStack stack = (ItemStack) items.values().toArray()[0];
//            stack.decrement(1);
//            player.world.setBlockState(pos, MinersDreamMod.autonomousTorchBlock.getDefaultState());
//            player.inventory.setStack((Integer) items.keySet().toArray()[0], stack);
//        }


    }
}
