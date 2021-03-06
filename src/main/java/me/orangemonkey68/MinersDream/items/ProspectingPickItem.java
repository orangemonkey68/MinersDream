package me.orangemonkey68.MinersDream.items;

import me.orangemonkey68.MinersDream.MinersDreamMod;
import me.orangemonkey68.MinersDream.config.ModConfig;
import me.orangemonkey68.MinersDream.util.sphere.SphereUtil;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolItem;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ProspectingPickItem extends ToolItem {
    private final int radius;
    private final List<Block> ores;

    public ProspectingPickItem(ProspectingPickMaterial material, Settings settings) {
        super(material, settings);
        this.radius = material.getRadius();
        this.ores = MinersDreamMod.oreBlocks;
    }


    //I'm genuinely sorry for anyone having to read this function
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos center = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();

        if(world.isClient){
            return ActionResult.PASS;
        }else{
            HashMap<Block, Integer> freqMap = new HashMap<>();
            List<Block> fetchedBlocks = SphereUtil.getBlocksInSphere(center, radius, world);

            //counts how many times each ore appears
            fetchedBlocks.forEach(block -> {
                if (ores.contains(block) && block != null) {
                    if (freqMap.containsKey(block)) {
                        freqMap.replace(block, freqMap.get(block) + 1);
                    } else {
                        freqMap.put(block, 1);
                    }
                }
            });

            //Send messages for all present ores
            if(player != null){
                if(freqMap.isEmpty()){
                    player.sendMessage(new TranslatableText("chat.miners_dream.nothing"), false);
                }else {
                    freqMap.forEach((block, i) -> player.sendMessage(getTextToSend(i, block.getName().getString()), false));
                }

                //damage tool
                if(context.getStack().isDamageable()){
                    context.getStack().damage(1, player, bc -> bc.sendToolBreakStatus(context.getHand()));
                }
            }

            return ActionResult.SUCCESS;
        }
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    private List<Block> getAllOres(){
        List<Block> blockList = new ArrayList<>();

        Registry.BLOCK.forEach(block -> {
            String name = block.getName().getString();
            if(name.contains("ore") || name.contains("Ore")){
                System.out.println(name);
                blockList.add(block);
            }
        });
        return blockList;
    }


    public Text getTextToSend(float count, String ore) {
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        System.out.println("Ore: " + ore + ", count: " + count);

        if (count >= config.smallCount && count < config.mediumCount) {
            return new TranslatableText("chat.miners_dream.small", ore);
        } else if (count >= config.mediumCount && count < config.largeCount) {
            return new TranslatableText("chat.miners_dream.medium", ore);
        } else if (count >= config.largeCount && count < config.motherload) {
            return new TranslatableText("chat.miners_dream.large", ore);
        } else if (count >= config.motherload) {
            return new TranslatableText("chat.miners_dream.motherload", ore);
        }else if (count >= 0 && count < config.smallCount){
            return new TranslatableText("chat.miners_dream.trace", ore);
        } else {
            return new LiteralText("An error occurred");
        }
    }
}