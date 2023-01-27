package com.github.dayzminecraft.dayzminecraft.BouH_.init;

import com.github.dayzminecraft.dayzminecraft.BouH_.guns.base.GunBase;
import com.github.dayzminecraft.dayzminecraft.BouH_.guns.items.ItemCommonGun;
import com.github.dayzminecraft.dayzminecraft.BouH_.guns.items.ItemShotgun;
import com.github.dayzminecraft.dayzminecraft.BouH_.items.ItemCamo;
import com.github.dayzminecraft.dayzminecraft.BouH_.items.ItemCommon;
import com.github.dayzminecraft.dayzminecraft.BouH_.items.ItemPainkiller;
import com.github.dayzminecraft.dayzminecraft.DayZ;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraftforge.common.util.EnumHelper;

public class ItemsDayZ {
    public static ItemArmor.ArmorMaterial enumArmorMaterialCamo = EnumHelper.addArmorMaterial("camo", 29, new int[]{2, 6, 5, 2}, 9);

    public static ItemFood sardines;
    public static ItemFood chocolatebar;
    public static Item painkillers;

    public static Item gunMakarov;
    public static Item ammoMakarov;
    public static Item gunUzi;
    public static Item ammoUzi;
    public static Item gunShotgun;
    public static Item ammoShotgun;
    public static Item gunCustom;
    public static Item ammoCustom;
    public static Item gunAkm;
    public static Item ammoAkm;
    public static Item gunMosin;
    public static Item ammoMosin;
    public static Item gunRevolver;
    public static Item ammoRevolver;

    public static Item camoBoots;
    public static Item camoChest;
    public static Item camoLegs;
    public static Item camoHelmet;


    public static void init() {
        chocolatebar = new net.minecraft.item.ItemFood(4, 1.0f, false);
        chocolatebar.setUnlocalizedName("chocolatebar");
        chocolatebar.setCreativeTab(DayZ.creativeTab);

        sardines = new net.minecraft.item.ItemFood(4, 1.0f, false);
        sardines.setUnlocalizedName("sardines");
        sardines.setCreativeTab(DayZ.creativeTab);

        painkillers = new ItemPainkiller();
        painkillers.setUnlocalizedName("painkillers");
        painkillers.setCreativeTab(DayZ.creativeTab);

        ammoMakarov = new ItemCommon("ammoMakarov");
        ammoMakarov.setMaxStackSize(1);
        gunMakarov = new ItemCommonGun("gunMakarov", GunBase.RenderType.Pistol, 1.0f, 4.0f, 8.0f, 4, 8, ammoMakarov, "gun.reload", "gun.makarov");

        ammoUzi = new ItemCommon("ammoUzi");
        ammoUzi.setMaxStackSize(1);
        gunUzi = new ItemCommonGun("gunUzi", GunBase.RenderType.Pistol, 3.0f, 2.0f, 2.5f, 3, 30, ammoUzi, "gun.reload", "gun.uzi").setAuto();

        ammoShotgun = new ItemCommon("ammoShotgun");
        ammoShotgun.setMaxStackSize(1);
        gunShotgun = new ItemShotgun("gunShotgun", GunBase.RenderType.Rifle, 10.0f, 8.0f, 2.0f, 20, 6, ammoShotgun, "gun.reload", "gun.shotgun");

        ammoCustom = new ItemCommon("ammoCustom");
        ammoCustom.setMaxStackSize(4);
        gunCustom = new ItemCommonGun("gunCustom", GunBase.RenderType.Pistol, 8.0f, 8.0f, 12.0f, 10, 1, ammoCustom, "gun.reload", "gun.custom");

        ammoRevolver = new ItemCommon("ammoRevolver");
        ammoRevolver.setMaxStackSize(1);
        gunRevolver = new ItemCommonGun("gunRevolver", GunBase.RenderType.Pistol, 1.0f, 8.0f, 12.0f, 10, 6, ammoRevolver, "gun.reload", "gun.custom");

        ammoAkm = new ItemCommon("ammoAkm");
        ammoAkm.setMaxStackSize(1);
        gunAkm = new ItemCommonGun("gunAkm", GunBase.RenderType.Rifle, 2.0f, 2.5f, 4.0f, 3, 30, ammoAkm, "gun.reload", "gun.akm").setAuto();

        ammoMosin = new ItemCommon("ammoMosin");
        ammoMosin.setMaxStackSize(1);
        gunMosin = new ItemCommonGun("gunMosin", GunBase.RenderType.Rifle, 0.25f, 6.0f, 20.0f, 20, 6, ammoMosin, "gun.reload", "gun.mosin");

        camoHelmet = new ItemCamo("camoHelmet", enumArmorMaterialCamo, 1, 0, 1);
        camoHelmet.setCreativeTab(DayZ.creativeTab);

        camoChest = new ItemCamo("camoChest", enumArmorMaterialCamo, 1, 1, 1);
        camoChest.setCreativeTab(DayZ.creativeTab);

        camoLegs = new ItemCamo("camoLegs", enumArmorMaterialCamo, 2, 2, 1);
        camoLegs.setCreativeTab(DayZ.creativeTab);

        camoBoots = new ItemCamo("camoBoots", enumArmorMaterialCamo, 1, 3, 2);
        camoBoots.setCreativeTab(DayZ.creativeTab);
    }

    public static void register() {
        registerItem(gunMakarov);
        registerItem(ammoMakarov);
        registerItem(gunRevolver);
        registerItem(ammoRevolver);
        registerItem(gunCustom);
        registerItem(ammoCustom);
        registerItem(gunUzi);
        registerItem(ammoUzi);
        registerItem(gunAkm);
        registerItem(ammoAkm);
        registerItem(gunShotgun);
        registerItem(ammoShotgun);
        registerItem(gunMosin);
        registerItem(ammoMosin);
        registerItem(camoHelmet);
        registerItem(camoChest);
        registerItem(camoLegs);
        registerItem(camoBoots);

        registerItem(chocolatebar);
        registerItem(sardines);
        registerItem(painkillers);
    }

    public static void registerItem(Item item) {
        GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
        item.setTextureName(DayZ.meta.modId + ":" + item.getUnlocalizedName().substring(5));
    }

    @SuppressWarnings("unused")
    public static void registerItem(Item item, String textureName) {
        GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
        item.setTextureName(DayZ.meta.modId + ":" + textureName);
    }
}
