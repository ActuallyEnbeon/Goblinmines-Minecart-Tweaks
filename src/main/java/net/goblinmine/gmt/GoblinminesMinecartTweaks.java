package net.goblinmine.gmt;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoblinminesMinecartTweaks implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("Goblinmine's Minecart Tweaks");
    public static final String MOD_ID = "goblinmines_minecart_tweaks";

    private static final Identifier copperRailID = Identifier.of(MOD_ID, "copper_rail");

    public static final Block COPPER_RAIL_BLOCK = Blocks.register(
            RegistryKey.of(RegistryKeys.BLOCK, copperRailID),
            CopperRailBlock::new,
            AbstractBlock.Settings.create()
                    .strength(0.7f, 0.7f)
                    .sounds(BlockSoundGroup.METAL)
                    .noCollision()
    );

    public static final BlockItem COPPER_RAIL_ITEM = (BlockItem) Items.register(COPPER_RAIL_BLOCK);

    public static final GameRules.Key<GameRules.IntRule> POWERED_RAIL_SPEED =
            GameRuleRegistry.register("poweredRailSpeed", GameRules.Category.MISC,
                    GameRuleFactory.createIntRule(24, 0, 100));
    public static final GameRules.Key<GameRules.IntRule> COPPER_RAIL_SPEED =
            GameRuleRegistry.register("copperRailSpeed", GameRules.Category.MISC,
                    GameRuleFactory.createIntRule(10, 0, 100));

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Hello Fabric world!");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(content ->
                content.addAfter(Items.RAIL, COPPER_RAIL_ITEM)
        );
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content ->
                content.addAfter(Items.RAIL, COPPER_RAIL_ITEM)
        );
    }
}