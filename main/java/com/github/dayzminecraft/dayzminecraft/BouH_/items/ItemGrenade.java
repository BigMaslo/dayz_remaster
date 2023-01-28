package com.github.dayzminecraft.dayzminecraft.BouH_.items;

import com.github.dayzminecraft.dayzminecraft.BouH_.entity.EntityGrenade;
import com.github.dayzminecraft.dayzminecraft.DayZ;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGrenade extends Item {
    public ItemGrenade(String name) {
        this.setUnlocalizedName(name);
        this.setCreativeTab(DayZ.creativeTab);
        this.setMaxStackSize(1);
    }

    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
        if (!p_77659_3_.capabilities.isCreativeMode) {
            --p_77659_1_.stackSize;
        }

        p_77659_2_.playSoundAtEntity(p_77659_3_, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!p_77659_2_.isRemote) {
            p_77659_2_.spawnEntityInWorld(new EntityGrenade(p_77659_2_, p_77659_3_));
        }

        return p_77659_1_;
    }
}
