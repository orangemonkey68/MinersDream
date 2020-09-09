package me.orangemonkey68.MinersDream.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class ItemVoid extends Item {
    public ItemVoid(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        CompoundTag tag = stack.getOrCreateTag();
        String boundItem = tag.getString("boundItem");
        if(!boundItem.isEmpty()){
            Item item = Registry.ITEM.get(new Identifier(boundItem));
            tooltip.add(new TranslatableText("item.miners_dream.item_void.tooltip.contains", item.getName().getString()));
        } else {
            //put the "how to bind me tooltip"
        }
    }
}
