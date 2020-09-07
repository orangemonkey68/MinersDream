package me.orangemonkey68.MinersDream.util.inventory;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryUtil {
    //Makes class unable to be instantiated
    private InventoryUtil(){}


    //Tries to insert stack. If it can't, then it returns the stack. If it can, it returns an empty stack.
    public static ItemStack insertStack(ItemStack stack, Inventory to){
        for(int i = 0; i < to.size(); i++){
            ItemStack currentStack = to.getStack(i);

            if(currentStack.isEmpty()){
                to.setStack(i, stack);
                return ItemStack.EMPTY;
            } else if (currentStack.getItem() == stack.getItem()){
                if (currentStack.getCount() + stack.getCount() <= currentStack.getMaxCount()){
                    stack.setCount(currentStack.getCount() + stack.getCount());
                    to.setStack(i, stack);
                    return ItemStack.EMPTY;
                } else if (!(currentStack.getCount() == currentStack.getMaxCount()) && currentStack.getCount() + stack.getCount() < currentStack.getMaxCount()){
                    int remainder = currentStack.getCount() + stack.getCount() - currentStack.getMaxCount();
                    stack.setCount(remainder);

                    currentStack.setCount(currentStack.getMaxCount());
                    to.setStack(i, currentStack);

                    return insertStack(stack, to);
                }
            }
        }

        return stack;
    }

    public static void removeCountOfItem(int count, Item item, Inventory inv){
        for(int i = 0; i < inv.size(); i++){
            ItemStack currentStack = inv.getStack(i);

            if(currentStack.getItem() == item){
                if(currentStack.getCount() - count == 0){
                    inv.removeStack(i);
                    return;
                } else if(currentStack.getCount() > count){
                    currentStack.setCount(currentStack.getCount() - count);
                    inv.setStack(i, currentStack);
                    return;
                } else {
                    count = count - currentStack.getCount();
                    inv.removeStack(i);
                }
            }
        }
    }
}
