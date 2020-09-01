package me.orangemonkey68.MinersDream.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;

@Config(name = "miners_dream")
public class ModConfig implements ConfigData {
    //levels: NOTHING, TRACE, SMALL SAMPLE, MEDIUM SAMPLE, LARGE SAMPLE, MOTHER LOAD
    public int smallCount = 4;
    public int mediumCount = 6;
    public int largeCount = 8;
    public int motherload = 20;
}
