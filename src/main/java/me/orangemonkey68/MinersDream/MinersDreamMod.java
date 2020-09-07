package me.orangemonkey68.MinersDream;

import me.orangemonkey68.MinersDream.Items.CircleDebugItem;
import me.orangemonkey68.MinersDream.Items.ProspectingPickItem;
import me.orangemonkey68.MinersDream.Items.ProspectingPickMaterial;
import me.orangemonkey68.MinersDream.Items.TranslocationStoneItem;
import me.orangemonkey68.MinersDream.config.ModConfig;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class MinersDreamMod implements ModInitializer {
    public static List<Block> oreBlocks = new ArrayList<>();
    public static List<Item> oreItems = new ArrayList<>();

    @Override
    public void onInitialize() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        initializeOreLists(oreBlocks, oreItems);
        registerItems();
    }

    //Because there's no "ores" tag within the game, this checks if each Item is
    //1) a block item
    //2) contains "ore"
    //3) does NOT have a block entity.
    // I'm aware this method isn't foolproof, but I have yet to find a better solution.
    private void initializeOreLists(List<Block> blocks, List<Item> items){
        Registry.ITEM.forEach(item -> {
            if(item instanceof BlockItem && item.getName().getString().toLowerCase().contains("ore")){
                BlockItem blockItem = (BlockItem) item;
                if(!blockItem.getBlock().hasBlockEntity()){
                    items.add(item);
                    blocks.add(blockItem.getBlock());
                }
            }
        });
    }

    public final ProspectingPickItem ironPick = new ProspectingPickItem(ProspectingPickMaterial.IRON, new Item.Settings().group(ItemGroup.TOOLS));
    public final ProspectingPickItem goldPick = new ProspectingPickItem(ProspectingPickMaterial.GOLD, new Item.Settings().group(ItemGroup.TOOLS));
    public final ProspectingPickItem diamondPick = new ProspectingPickItem(ProspectingPickMaterial.DIAMOND, new Item.Settings().group(ItemGroup.TOOLS));
    public final ProspectingPickItem netheritePick = new ProspectingPickItem(ProspectingPickMaterial.NETHERITE, new Item.Settings().group(ItemGroup.TOOLS));
    public final CircleDebugItem circleDebugItem = new CircleDebugItem(new Item.Settings().group(ItemGroup.TOOLS));

    public final TranslocationStoneItem translocationStoneItem = new TranslocationStoneItem(new Item.Settings().group(ItemGroup.TOOLS).maxCount(1).rarity(Rarity.RARE));

    void registerItems(){
        Registry.register(Registry.ITEM, new Identifier("miners_dream", "circle_test_item"), circleDebugItem);
        Registry.register(Registry.ITEM, new Identifier("miners_dream", "iron_prospecting_pick"), ironPick);
        Registry.register(Registry.ITEM, new Identifier("miners_dream", "gold_prospecting_pick"), goldPick);
        Registry.register(Registry.ITEM, new Identifier("miners_dream", "diamond_prospecting_pick"), diamondPick);
        Registry.register(Registry.ITEM, new Identifier("miners_dream", "netherite_prospecting_pick"), netheritePick);
        Registry.register(Registry.ITEM, new Identifier("miners_dream", "translocation_stone"), translocationStoneItem);
    }
}
