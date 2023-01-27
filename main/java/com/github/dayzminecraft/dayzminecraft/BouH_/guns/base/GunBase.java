package com.github.dayzminecraft.dayzminecraft.BouH_.guns.base;

import com.github.dayzminecraft.dayzminecraft.BouH_.guns.base.render.GunItemRender;
import com.github.dayzminecraft.dayzminecraft.BouH_.network.NetworkHandler;
import com.github.dayzminecraft.dayzminecraft.BouH_.network.packets.gun.PacketShoot;
import com.github.dayzminecraft.dayzminecraft.DayZ;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public abstract class GunBase extends Item {
    private final float inaccuracy;
    private final float recoil;
    private final float damage;
    private final int cdShoot;
    private final int maxAmmo;
    private final Item ammo;
    private boolean isAutomatic;

    private final String reloadSound;
    private final String shootSound;

    private RenderType gunRenderType;

    protected GunBase(String weaponName, RenderType renderType, float inaccuracy, float recoil, float damage, int cdShoot, int maxAmmo, Item ammo, String reloadSound, String shootSound) {
        if (FMLLaunchHandler.side().isClient()) {
            MinecraftForgeClient.registerItemRenderer(this, GunItemRender.instance);
        }
        this.gunRenderType = renderType;
        this.setCreativeTab(DayZ.creativeTab);
        this.setMaxStackSize(1);
        this.setUnlocalizedName(weaponName);
        this.isAutomatic = false;
        this.inaccuracy = inaccuracy;
        this.recoil = recoil;
        this.damage = damage;
        this.cdShoot = cdShoot;
        this.maxAmmo = maxAmmo;
        this.ammo = ammo;
        this.shootSound = shootSound;
        this.reloadSound = reloadSound;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!stack.hasTagCompound()) {
            stack.setTagInfo(DayZ.meta.modId, new NBTTagCompound());
        } else {
            if (entityIn instanceof EntityPlayer) {
                int i2 = stack.getTagCompound().getCompoundTag(DayZ.meta.modId).getInteger("shoot");
                if (!world.isRemote) {
                    int i1 = stack.getTagCompound().getCompoundTag(DayZ.meta.modId).getInteger("reload");
                    if (this.isReloading(stack)) {
                        if (i1 == 1) {
                            this.reload(stack, (EntityPlayer) entityIn);
                        }
                        stack.getTagCompound().getCompoundTag(DayZ.meta.modId).setInteger("reload", i1 - 1);
                    }
                }
                if (i2 > 0) {
                    stack.getTagCompound().getCompoundTag(DayZ.meta.modId).setInteger("shoot", i2 - 1);
                }
            }
        }
    }

    protected abstract void shoot(ItemStack stack, EntityPlayer player, float rotationPitch, float rotationYaw);
    protected abstract void reload(ItemStack stack, EntityPlayer player);

    public RenderType getGunRenderType() {
        return this.gunRenderType;
    }

    public void setGunRenderType(RenderType renderType) {
        this.gunRenderType = renderType;
    }

    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        return this.getShootTicks(stack) > 0 || this.isReloading(stack);
    }

    public GunBase setAuto() {
        this.isAutomatic = true;
        return this;
    }

    public boolean isReloading(ItemStack stack) {
        return stack.getTagCompound().getCompoundTag(DayZ.meta.modId).getInteger("reload") > 0;
    }

    public int getShootTicks(ItemStack stack) {
        return stack.getTagCompound().getCompoundTag(DayZ.meta.modId).getInteger("shoot");
    }

    public boolean canShoot(ItemStack stack) {
        return !this.isReloading(stack) && this.getAmmo(stack) > 0 && stack.getTagCompound().getCompoundTag(DayZ.meta.modId).getInteger("shoot") <= 0;
    }

    public boolean canReload(EntityPlayer player, ItemStack stack) {
        return !this.isReloading(stack) && this.getAmmo(stack) < this.getMaxAmmo() && player.inventory.hasItem(this.ammo) && stack.getTagCompound().getCompoundTag(DayZ.meta.modId).getInteger("shoot") <= 0;
    }

    public void startReloading(EntityPlayer player, ItemStack stack) {
        if (this.canReload(player, stack)) {
            if (!player.capabilities.isCreativeMode) {
                player.inventory.consumeInventoryItem(this.getAmmoItem());
            }
            player.worldObj.playSoundAtEntity(player, DayZ.meta.modId + ":" + this.getReloadSound(), 6.0f, 1.0f);
            stack.getTagCompound().getCompoundTag(DayZ.meta.modId).setInteger("reload", 80);
        }
    }

    public void startShooting(EntityPlayer player, ItemStack stack) {
        if (this.canShoot(stack)) {
            if (!player.worldObj.isRemote) {
                player.worldObj.playSoundAtEntity(player, DayZ.meta.modId + ":" + this.getShootSound(), 6.0f, 1.0f + itemRand.nextFloat() * 0.3f);
                this.shoot(stack, player, player.rotationPitch, player.rotationYaw);
            } else {
                player.rotationPitch -= this.getRecoil();
                player.cameraPitch += this.getRecoil();
                GunItemRender.instance.recoil = 4;
                NetworkHandler.NETWORK.sendToServer(new PacketShoot());
            }
            stack.getTagCompound().getCompoundTag(DayZ.meta.modId).setInteger("shoot", this.getCdShoot());
        }
    }

    public void consumeAmmo(ItemStack stack) {
        stack.getTagCompound().getCompoundTag(DayZ.meta.modId).setInteger("ammo", this.getAmmo(stack) - 1);
    }

    public void setAmmo(ItemStack stack, int ammo) {
        stack.getTagCompound().getCompoundTag(DayZ.meta.modId).setInteger("ammo", ammo);
    }

    public int getAmmo(ItemStack stack) {
        return stack.getTagCompound().getCompoundTag(DayZ.meta.modId).getInteger("ammo");
    }

    public boolean isAutomatic() {
        return this.isAutomatic;
    }

    public int getMaxAmmo() {
        return this.maxAmmo;
    }

    public Item getAmmoItem() {
        return this.ammo;
    }

    public int getCdShoot() {
        return this.cdShoot;
    }

    public float getDamage() {
        return this.damage;
    }

    public float getInaccuracy() {
        return this.inaccuracy;
    }

    public float getRecoil() {
        return this.recoil;
    }

    public String getReloadSound() {
        return this.reloadSound;
    }

    public String getShootSound() {
        return this.shootSound;
    }

    public enum RenderType {
        Pistol,
        Rifle
    }
}
