package com.github.dayzminecraft.dayzminecraft.client;

import com.github.dayzminecraft.dayzminecraft.BouH_.entity.EntityGrenade;
import com.github.dayzminecraft.dayzminecraft.BouH_.events.client.ClientEvent;
import com.github.dayzminecraft.dayzminecraft.BouH_.guns.base.render.RenderBuckShot;
import com.github.dayzminecraft.dayzminecraft.BouH_.guns.base.render.RenderBullet;
import com.github.dayzminecraft.dayzminecraft.BouH_.guns.base.render.player.RenderPlayerDayZ;
import com.github.dayzminecraft.dayzminecraft.BouH_.guns.bullet.EntityBuckshot;
import com.github.dayzminecraft.dayzminecraft.BouH_.guns.bullet.EntityBullet;
import com.github.dayzminecraft.dayzminecraft.BouH_.init.ItemsDayZ;
import com.github.dayzminecraft.dayzminecraft.client.entities.*;
import com.github.dayzminecraft.dayzminecraft.common.CommonProxy;
import com.github.dayzminecraft.dayzminecraft.common.entities.EntityBandit;
import com.github.dayzminecraft.dayzminecraft.common.entities.EntityCrawler;
import com.github.dayzminecraft.dayzminecraft.common.entities.EntityZombieDayZ;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.model.ModelBiped;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
    @Override
    public void preload(FMLPreInitializationEvent event) throws IllegalAccessException {
        super.preload(event);
    }

    @Override
    public void load(FMLInitializationEvent event) {
        super.load(event);
        RenderingRegistry.registerEntityRenderingHandler(EntityZombieDayZ.class, new RenderZombieDayZ(new ModelZombieDayZ(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityCrawler.class, new RenderCrawler(new ModelCrawler(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(EntityBandit.class, new RenderBandit());
        RenderingRegistry.registerEntityRenderingHandler(EntityGrenade.class, new RenderThrowable(ItemsDayZ.grenade));
        RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderBullet());
        RenderingRegistry.registerEntityRenderingHandler(EntityBuckshot.class, new RenderBuckShot());

        RenderingRegistry.registerEntityRenderingHandler(EntityClientPlayerMP.class, new RenderPlayerDayZ());
        RenderingRegistry.registerEntityRenderingHandler(EntityOtherPlayerMP.class, new RenderPlayerDayZ());

        FMLCommonHandler.instance().bus().register(ClientEvent.instance);
        MinecraftForge.EVENT_BUS.register(ClientEvent.instance);
    }

    @Override
    public void postload(FMLPostInitializationEvent event) {
        super.postload(event);
    }

    @Override
    public void serverStarted(FMLServerStartedEvent event) {
        super.serverStarted(event);
    }
}