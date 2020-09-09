package me.orangemonkey68.MinersDream.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;

import java.util.List;

public class TorchVoidItem extends Item {
    public TorchVoidItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        CompoundTag tag = stack.getOrCreateTag();
        boolean isOn = tag.getBoolean("isOn");
        long torchCount = tag.getLong("torchCount");

        tooltip.add(new TranslatableText("chat.miners_dream.torch_void.tooltip.desc"));
        tooltip.add(new TranslatableText("chat.miners_dream.torch_void.tooltip.contains", torchCount));
        tooltip.add(new TranslatableText("item.miners_dream.torch_void.tooltip.isOn", String.valueOf(isOn).toUpperCase()));
    }
}
