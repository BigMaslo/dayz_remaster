package com.github.dayzminecraft.dayzminecraft;

import com.github.dayzminecraft.dayzminecraft.BouH_.init.ItemsDayZ;
import com.github.dayzminecraft.dayzminecraft.common.CommonProxy;
import com.github.dayzminecraft.dayzminecraft.common.items.ModItems;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

@Mod(modid = "DayZ++", name = "DayZ++ Remastered")
public class DayZ {
    public static CreativeTabs creativeTab = new CreativeTabs("creativeTabDayZ") {
        @Override
        public Item getTabIconItem() {
            return ItemsDayZ.gunMakarov;
        }
    };

    @Instance("DayZ++")
    public static DayZ INSTANCE;

    @Metadata
    public static ModMetadata meta;

    @SidedProxy(clientSide = "com.github.dayzminecraft.dayzminecraft.client.ClientProxy", serverSide = "com.github.dayzminecraft.dayzminecraft.common.CommonProxy")
    public static CommonProxy proxy;

    public static boolean isServer() {
        return FMLCommonHandler.instance().getEffectiveSide().isServer();
    }

    public static boolean isClient() {
        return FMLCommonHandler.instance().getEffectiveSide().isClient();
    }

    @EventHandler
    public void preload(FMLPreInitializationEvent event) throws IllegalAccessException {
        proxy.preload(event);
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        proxy.load(event);
    }

    @EventHandler
    public void postload(FMLPostInitializationEvent event) {
        proxy.postload(event);
    }

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        proxy.serverStarted(event);
    }
}