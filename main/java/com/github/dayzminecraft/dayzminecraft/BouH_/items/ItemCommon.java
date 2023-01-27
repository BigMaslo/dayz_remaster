package com.github.dayzminecraft.dayzminecraft.BouH_.items;

import com.github.dayzminecraft.dayzminecraft.DayZ;
import net.minecraft.item.Item;

public class ItemCommon extends Item {
    public ItemCommon(String name) {
        this.setUnlocalizedName(name);
        this.setCreativeTab(DayZ.creativeTab);
    }
}
