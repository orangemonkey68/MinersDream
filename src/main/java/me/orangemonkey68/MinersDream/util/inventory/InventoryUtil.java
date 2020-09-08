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
                System.out.println("stack was empty");

                to.setStack(i, stack);
                return ItemStack.EMPTY;
            } else if (currentStack.getItem() == stack.getItem()){
                if (currentStack.getCount() + stack.getCount() <= currentStack.getMaxCount()){
                    System.out.println("adding stacks together");
                    currentStack.setCount(currentStack.getCount() + stack.getCount());
                    to.setStack(i, stack);
                    return ItemStack.EMPTY;
                } else if (!(currentStack.getCount() == currentStack.getMaxCount()) && currentStack.getCount() + stack.getCount() < currentStack.getMaxCount()){
                    System.out.println("stacks more than max");
                    int remainder = currentStack.getCount() + stack.getCount() - currentStack.getMaxCount();
                    currentStack.setCount(remainder);

                    currentStack.setCount(currentStack.getMaxCount());
                    to.setStack(i, currentStack);

                    System.out.println("RECURSIVE CALL");
                    return insertStack(stack, to);
                }
            }
        }

        return stack;
    }

    public static void removeCountOfItem(int countToRemove, Item item, Inventory inv){

        for(int i = 0; i < inv.size(); i++){
            ItemStack currentStack = inv.getStack(i);
            if(currentStack.getItem() == item){
                if(currentStack.getCount() == countToRemove){
                    inv.removeStack(i);
                    countToRemove = 0;
                    return;
                } else if (currentStack.getCount() > countToRemove){
                    currentStack.setCount(currentStack.getCount() - countToRemove);
                    inv.setStack(i, currentStack);
                    return;
                } else {
                    countToRemove = countToRemove - currentStack.getCount();
                    inv.removeStack(i);
                }
            }
        }
    }
}
