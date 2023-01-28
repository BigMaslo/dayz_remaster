package com.github.dayzminecraft.dayzminecraft.common.effects;

import com.github.dayzminecraft.dayzminecraft.DayZ;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

public class Effect extends Potion {
    public static Effect bleeding;
    public static int bleedingId;
    public static Effect zombification;
    public static int zombificationId;
    public static final ResourceLocation effectIcons = new ResourceLocation(DayZ.meta.modId + ":textures/gui/inventory.png");

    public Effect(int id, boolean isBadEffect, int color, String name) {
        super(id, isBadEffect, color);
        setPotionName("potion." + name);
        LanguageRegistry.instance().addStringLocalization(getName(), name);
    }

    public static void loadEffects() {
        bleeding = new Effect(bleedingId, true, 5149489, "Bleeding");
        zombification = new Effect(zombificationId, true, 5149489, "Zombification");
    }

    public static void effectConfig(Configuration config) {
        bleedingId = config.get("effect", "bleedingId", 29, "Bleeding Effect ID").getInt();
        zombificationId = config.get("effect", "zombificationId", 30, "Zombification Effect ID").getInt();
    }

    public static void register() {
        Potion.potionTypes[bleeding.getId()] = bleeding.setIconIndex(1, 0);
        Potion.potionTypes[zombification.getId()] = zombification.setIconIndex(2, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex() {
        Minecraft.getMinecraft().renderEngine.bindTexture(effectIcons);
        return super.getStatusIconIndex();
    }

    @Override
    public Potion setIconIndex(int x, int y) {
        super.setIconIndex(x, y);
        return this;
    }
}