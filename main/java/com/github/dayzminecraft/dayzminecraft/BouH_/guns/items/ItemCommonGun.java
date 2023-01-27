package com.github.dayzminecraft.dayzminecraft.BouH_.guns.items;

import com.github.dayzminecraft.dayzminecraft.BouH_.guns.base.GunBase;
import com.github.dayzminecraft.dayzminecraft.BouH_.guns.bullet.EntityBullet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCommonGun extends GunBase {

    public ItemCommonGun(String weaponName, GunBase.RenderType renderType, float inaccuracy, float recoil, float damage, int cdShoot, int maxAmmo, Item ammo, String reloadSound, String shootSound) {
        super(weaponName, renderType, inaccuracy, recoil, damage, cdShoot, maxAmmo, ammo, reloadSound, shootSound);
    }

    @Override
    protected void shoot(ItemStack stack, EntityPlayer player, float rotationPitch, float rotationYaw) {
        EntityBullet entityBullet = new EntityBullet(player.worldObj, player, this.getInaccuracy(), this.getDamage());
        player.worldObj.spawnEntityInWorld(entityBullet);
        this.consumeAmmo(stack);
    }

    @Override
    protected void reload(ItemStack stack, EntityPlayer player) {
        this.setAmmo(stack, this.getMaxAmmo());
    }
}
