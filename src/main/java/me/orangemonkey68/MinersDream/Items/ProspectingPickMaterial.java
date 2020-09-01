package me.orangemonkey68.MinersDream.Items;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Lazy;

import java.util.function.Supplier;



public enum ProspectingPickMaterial implements ToolMaterial {
    IRON(0, 250, 1, 4, 0, () -> {
        return Ingredient.ofItems(Items.IRON_INGOT);
    }, 3),
    GOLD(0, 32, 1, 2, 0, () -> {
        return Ingredient.ofItems(Items.GOLD_INGOT);
    }, 4),
    DIAMOND(0, 1561, 1, 5, 0, () -> {
        return Ingredient.ofItems(Items.DIAMOND);
    }, 5),
    NETHERITE(0, 2031, 1, 4, 0, () -> {
        return Ingredient.ofItems(Items.NETHERITE_INGOT);
    }, 6);


    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeedMultiplier;
    private final float attackDamage;
    private final int enchantability;
    private final int radius;
    private final Lazy<Ingredient> repairIngredient;

    ProspectingPickMaterial(int miningLevel, int itemDurability, float miningSpeedMultiplier, float attackDamage, int enchantability, Supplier<Ingredient> repairIngredient, int radius){
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this. miningSpeedMultiplier = miningSpeedMultiplier;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairIngredient = new Lazy<>(repairIngredient);
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeedMultiplier;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

}
