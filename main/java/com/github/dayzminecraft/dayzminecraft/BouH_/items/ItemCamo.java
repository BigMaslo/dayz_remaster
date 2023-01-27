package com.github.dayzminecraft.dayzminecraft.BouH_.items;

import com.github.dayzminecraft.dayzminecraft.DayZ;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemCamo extends ItemArmor {
    private int layer;

    public ItemCamo(String name, ArmorMaterial p_i45325_1_, int p_i45325_2_, int p_i45325_3_, int layer) {
        super(p_i45325_1_, p_i45325_2_, p_i45325_3_);
        this.setUnlocalizedName(name);
        this.setCreativeTab(DayZ.creativeTab);
        this.layer = layer;
    }

    @Override
    public String getArmorTexture(ItemStack itemStack, Entity entity, int slot, String layer) {
        return DayZ.meta.modId + ":textures/armor/camo_" + this.layer + ".png";
    }
}
