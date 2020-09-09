package me.orangemonkey68.MinersDream;

import me.orangemonkey68.MinersDream.blocks.AutonomousTorchBlock;
import me.orangemonkey68.MinersDream.blocks.AutonomousWallTorchBlock;
import me.orangemonkey68.MinersDream.config.ModConfig;
import me.orangemonkey68.MinersDream.items.CircleDebugItem;
import me.orangemonkey68.MinersDream.items.ProspectingPickItem;
import me.orangemonkey68.MinersDream.items.ProspectingPickMaterial;
import me.orangemonkey68.MinersDream.items.TranslocationStoneItem;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
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
        renderLayers();
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

    public static final ProspectingPickItem goldPick = new ProspectingPickItem(ProspectingPickMaterial.GOLD, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ProspectingPickItem diamondPick = new ProspectingPickItem(ProspectingPickMaterial.DIAMOND, new Item.Settings().group(ItemGroup.TOOLS));
    public static final ProspectingPickItem netheritePick = new ProspectingPickItem(ProspectingPickMaterial.NETHERITE, new Item.Settings().group(ItemGroup.TOOLS));
    public static final CircleDebugItem circleDebugItem = new CircleDebugItem(new Item.Settings().group(ItemGroup.TOOLS));
    public static final TranslocationStoneItem translocationStoneItem = new TranslocationStoneItem(new Item.Settings().group(ItemGroup.TOOLS).maxCount(1).rarity(Rarity.RARE));

    public static final AutonomousTorchBlock autonomousTorchBlock = new AutonomousTorchBlock(AbstractBlock.Settings.of(Material.SUPPORTED).noCollision().breakInstantly().lightLevel((state) -> 14).sounds(BlockSoundGroup.WOOD).nonOpaque(), ParticleTypes.FLAME);

    public static final AutonomousWallTorchBlock autonomousWallTorchBlock = new AutonomousWallTorchBlock(AbstractBlock.Settings.of(Material.SUPPORTED).noCollision().breakInstantly().lightLevel((state) -> 14).sounds(BlockSoundGroup.WOOD).nonOpaque(), ParticleTypes.FLAME);
    public static final WallStandingBlockItem autonomousTorchBlockItem = new WallStandingBlockItem(autonomousTorchBlock, autonomousWallTorchBlock, new Item.Settings().group(ItemGroup.TOOLS));
    void registerItems(){
        Registry.register(Registry.ITEM, new Identifier("miners_dream", "circle_test_item"), circleDebugItem);
        Registry.register(Registry.ITEM, new Identifier("miners_dream", "iron_prospecting_pick"), ironPick);
        Registry.register(Registry.ITEM, new Identifier("miners_dream", "gold_prospecting_pick"), goldPick);
        Registry.register(Registry.ITEM, new Identifier("miners_dream", "diamond_prospecting_pick"), diamondPick);
        Registry.register(Registry.ITEM, new Identifier("miners_dream", "netherite_prospecting_pick"), netheritePick);
        Registry.register(Registry.ITEM, new Identifier("miners_dream", "translocation_stone"), translocationStoneItem);


        Registry.register(Registry.ITEM, new Identifier("miners_dream", "autonomous_torch"), autonomousTorchBlockItem);
        Registry.register(Registry.BLOCK, new Identifier("miners_dream","autonomous_torch"), autonomousTorchBlock);
        Registry.register(Registry.BLOCK, new Identifier("miners_dream", "autonomous_wall_torch"), autonomousWallTorchBlock);
    }

    @Environment(EnvType.CLIENT)
    void renderLayers() {
        System.out.println("renderLayers");
        BlockRenderLayerMap.INSTANCE.putBlock(autonomousTorchBlock, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(autonomousWallTorchBlock, RenderLayer.getCutout());
    }
}
