package com.github.dayzminecraft.dayzminecraft.common.misc;

import com.github.dayzminecraft.dayzminecraft.BouH_.init.ItemsDayZ;
import com.github.dayzminecraft.dayzminecraft.common.blocks.ModBlocks;
import com.github.dayzminecraft.dayzminecraft.common.items.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraftforge.common.ChestGenHooks;

import java.util.ArrayList;
import java.util.Arrays;

public class LootManager {
    public static WeightedRandomChestContent[] loot = new WeightedRandomChestContent[]{};

    public static void init() {
        for (WeightedRandomChestContent content : StructureVillagePieces.House2.villageBlacksmithChestContents) {
            ChestGenHooks.removeItem(ChestGenHooks.VILLAGE_BLACKSMITH, content.theItemId);
        }

        for (WeightedRandomChestContent content : WorldServer.bonusChestContent) {
            ChestGenHooks.removeItem(ChestGenHooks.BONUS_CHEST, content.theItemId);
        }

        registerAllItems();
        loadDayZ();
    }

    private static void loadDayZ() {
        addLoot(new ItemStack(ItemsDayZ.gunCustom), 10);
        addLoot(new ItemStack(ItemsDayZ.ammoCustom), 20);
        addLoot(new ItemStack(ItemsDayZ.ammoCustom, 2), 5);
        addLoot(new ItemStack(ItemsDayZ.gunMakarov), 4);
        addLoot(new ItemStack(ItemsDayZ.ammoMakarov), 8);
        addLoot(new ItemStack(ItemsDayZ.gunRevolver), 2);
        addLoot(new ItemStack(ItemsDayZ.ammoRevolver), 4);
        addLoot(new ItemStack(ItemsDayZ.gunUzi), 3);
        addLoot(new ItemStack(ItemsDayZ.ammoUzi), 6);
        addLoot(new ItemStack(ItemsDayZ.gunShotgun), 1);
        addLoot(new ItemStack(ItemsDayZ.ammoShotgun), 3);
        addLoot(new ItemStack(ItemsDayZ.gunAkm), 1);
        addLoot(new ItemStack(ItemsDayZ.ammoAkm), 3);
        addLoot(new ItemStack(ItemsDayZ.gunMosin), 1);
        addLoot(new ItemStack(ItemsDayZ.ammoMosin), 3);
        addLoot(new ItemStack(ItemsDayZ.camoHelmet), 15);
        addLoot(new ItemStack(ItemsDayZ.camoChest), 15);
        addLoot(new ItemStack(ItemsDayZ.camoLegs), 15);
        addLoot(new ItemStack(ItemsDayZ.camoBoots), 15);
    }

    public static void addLoot(ItemStack itemStack, int itemWorth) {
        ArrayList<WeightedRandomChestContent> contents = new ArrayList<WeightedRandomChestContent>(Arrays.asList(loot));
        contents.add(new WeightedRandomChestContent(itemStack, 1, 1, itemWorth));
        loot = contents.toArray(new WeightedRandomChestContent[contents.size()]);

        ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(itemStack, 1, 1, itemWorth));
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(itemStack, 1, 1, itemWorth));
    }

    public static void registerAllItems() {
        addLoot(new ItemStack(Items.rotten_flesh), 150);
        addLoot(new ItemStack(Blocks.deadbush), 150);
        addLoot(new ItemStack(ModBlocks.barbedWire), 3);
        addLoot(new ItemStack(Items.bow), 5);
        addLoot(new ItemStack(ModItems.meleeBaseballBatNailed), 12);
        addLoot(new ItemStack(ModItems.meleePipe), 12);
        addLoot(new ItemStack(ModItems.meleePlankNailed), 12);
        addLoot(new ItemStack(ModItems.meleeCrowbar), 12);
        addLoot(new ItemStack(ModItems.meleeMachete), 12);
        addLoot(new ItemStack(Items.map), 5);
        addLoot(new ItemStack(Items.coal), 5);
        addLoot(new ItemStack(Items.iron_ingot), 5);
        addLoot(new ItemStack(Items.writable_book), 15);
        addLoot(new ItemStack(Items.arrow), 15);
        addLoot(new ItemStack(Items.bone), 15);
        addLoot(new ItemStack(ModItems.healBloodbag), 1);
        addLoot(new ItemStack(Blocks.tripwire_hook), 15);
        addLoot(new ItemStack(Items.string), 15);
        addLoot(new ItemStack(Items.boat), 25);
        addLoot(new ItemStack(Items.emerald), 5);
        addLoot(new ItemStack(ModItems.healBandage), 2);
        addLoot(new ItemStack(ModItems.meleeBaseballBat), 12);
        addLoot(new ItemStack(ModItems.meleePlank), 12);
        addLoot(new ItemStack(ModItems.drinkWhiskeyBottle), 1);
        addLoot(new ItemStack(ModItems.drinkCiderBottle), 1);
        addLoot(new ItemStack(ModItems.drinkVodkaBottle), 1);
        addLoot(new ItemStack(ModBlocks.nailBlock), 10);
        addLoot(new ItemStack(ModBlocks.concrete, 4), 10);
        addLoot(new ItemStack(ItemsDayZ.painkillers), 1);
        addLoot(new ItemStack(Items.beef), 1);
        addLoot(new ItemStack(Items.cooked_beef), 1);
        addLoot(new ItemStack(Items.porkchop), 1);
        addLoot(new ItemStack(Items.cooked_porkchop), 1);
        addLoot(new ItemStack(Items.fish), 1);
        addLoot(new ItemStack(Items.cooked_fished), 1);
        addLoot(new ItemStack(Items.apple), 1);
        addLoot(new ItemStack(Items.mushroom_stew), 1);
        addLoot(new ItemStack(Items.melon), 1);
        addLoot(new ItemStack(Items.cookie), 1);
        addLoot(new ItemStack(Items.potato), 1);
        addLoot(new ItemStack(ModItems.healAntibiotics), 1);
        addLoot(new ItemStack(ModItems.cannedBeans), 1);
        addLoot(new ItemStack(ModItems.cannedSoup), 1);
        addLoot(new ItemStack(ModItems.cannedPasta), 1);
        addLoot(new ItemStack(ItemsDayZ.chocolatebar), 1);
        addLoot(new ItemStack(ItemsDayZ.sardines), 1);
        addLoot(new ItemStack(ModItems.cannedFruit), 1);
        addLoot(new ItemStack(ModItems.drinkCanBeer), 1);
        addLoot(new ItemStack(ModItems.drinkCanLemonSoda), 1);
        addLoot(new ItemStack(ModItems.drinkCanCola), 1);
        addLoot(new ItemStack(ModItems.drinkCanEnergyDrink), 1);
        addLoot(new ItemStack(ModItems.drinkCanOrangeSoda), 1);
        addLoot(new ItemStack(ModItems.cannedBeans, 1, 1), 1);
        addLoot(new ItemStack(ModItems.cannedSoup, 1, 1), 1);
        addLoot(new ItemStack(ModItems.cannedPasta, 1, 1), 1);
        addLoot(new ItemStack(ModItems.cannedFruit, 1, 1), 1);
        addLoot(new ItemStack(ModItems.drinkAnanas, 1, 1), 1);
        addLoot(new ItemStack(ModItems.drinkCanBeer, 1, 1), 1);
        addLoot(new ItemStack(ModItems.drinkCanLemonSoda, 1, 1), 1);
        addLoot(new ItemStack(ModItems.drinkCanCola, 1, 1), 1);
        addLoot(new ItemStack(ModItems.drinkCanEnergyDrink, 1, 1), 1);
        addLoot(new ItemStack(ModItems.drinkCanOrangeSoda, 1, 1), 1);
    }
}
