package com.github.dayzminecraft.dayzminecraft.BouH_.events.server;

import com.github.dayzminecraft.dayzminecraft.BouH_.en_data.Thirst;
import com.github.dayzminecraft.dayzminecraft.BouH_.guns.base.GunBase;
import com.github.dayzminecraft.dayzminecraft.common.effects.Effect;
import com.github.dayzminecraft.dayzminecraft.common.entities.EntityCrawler;
import com.github.dayzminecraft.dayzminecraft.common.entities.EntityZombieDayZ;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

import java.util.List;
import java.util.Random;

public class Events {
    private Random random = new Random();

    @SubscribeEvent()
    public void onUpdate(LivingEvent.LivingUpdateEvent ev) {
        EntityLivingBase ent = ev.entityLiving;
        if (ent != null && ent.isEntityAlive()) {
            if (ent instanceof EntityPlayer) {
                EntityPlayer pl = (EntityPlayer) ent;
                if (!pl.capabilities.isCreativeMode) {
                    if (!pl.worldObj.isRemote) {
                        this.updateStats(pl);
                    }
                }
            }
        }
    }

    private void updateStats(EntityPlayer pl) {
        Thirst thirst = Thirst.getThirst(pl);
        thirst.onUpdate();
        thirst.addExhaustion(0.001f);
        double i = Math.round(MathHelper.sqrt_double(pl.motionX * pl.motionX + pl.motionZ * pl.motionZ));
        thirst.addExhaustion((float) (i * 0.15f));
        if (pl.isSprinting()) {
            thirst.addExhaustion(0.02f);
        }
        if (pl.isInWater()) {
            thirst.setExhaustion(0);
        }
        if (!pl.isPotionActive(12)) {
            if (pl.isBurning() || pl.handleLavaMovement()) {
                thirst.addExhaustion(0.05f);
            }
        }
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent ev) {
        if (ev.target != null) {
            float const1 = 2.8f;
            if (ev.target instanceof EntityPlayer) {
                const1 += 2.2f;
            }
            if (ev.entityPlayer.getDistanceToEntity(ev.target) > const1) {
                ev.setCanceled(true);
            } else {
                Thirst thirst = Thirst.getThirst(ev.entityPlayer);
                thirst.addExhaustion(0.1f);
            }
        }
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent ev) {
        ItemStack stack = ev.entityPlayer.getHeldItem();
        if (stack != null && stack.getItem() instanceof GunBase) {
            ev.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onInteract(EntityInteractEvent ev) {
        ItemStack stack = ev.entityPlayer.getHeldItem();
        if (ev.target instanceof EntityVillager) {
            if (stack != null && stack.getItem() instanceof GunBase) {
                ev.setCanceled(true);
            }
        }
        if (ev.target instanceof EntityCow) {
            ev.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onConstruct(EntityEvent.EntityConstructing ev) {
        if (ev.entity instanceof EntityPlayer) {
            EntityPlayer pl = (EntityPlayer) ev.entity;
            if (Thirst.getThirst(pl) == null) {
                pl.registerExtendedProperties("Thirst", new Thirst(pl));
            }
        }
    }

    @SubscribeEvent
    public void onUsed(PlayerUseItemEvent.Finish ev) {
        if (ev.item != null) {
            if (!ev.entityPlayer.worldObj.isRemote) {
                if (ev.item.getItemUseAction() == EnumAction.drink) {
                    if (ev.item.getItem() instanceof ItemPotion) {
                        if (ev.item.getItemDamage() == 0) {
                            ev.entityPlayer.addPotionEffect(new PotionEffect(17, 1200));
                        }
                    }
                    Thirst thirst = Thirst.getThirst(ev.entityPlayer);
                    thirst.addThirst(2, 0.0f);
                }
                if (ev.item.getItem() == Items.rotten_flesh) {
                    switch (this.random.nextInt(4)) {
                        case 0: {
                            ev.entityPlayer.addPotionEffect(new PotionEffect(19, 360));
                            break;
                        }
                        case 1: {
                            ev.entityPlayer.addPotionEffect(new PotionEffect(17, 2400));
                            break;
                        }
                        case 2: {
                            ev.entityPlayer.addPotionEffect(new PotionEffect(9, 1200));
                            break;
                        }
                    }
                    if (!ev.entityPlayer.isPotionActive(Effect.zombificationId) && this.random.nextFloat() <= 0.05f) {
                        ev.entityPlayer.addPotionEffect(new PotionEffect(Effect.zombificationId, 6000));
                    }
                }
                if (ev.item.getItem() == Items.chicken || ev.item.getItem() == Items.fish || ev.item.getItem() == Items.beef || ev.item.getItem() == Items.porkchop) {
                    ev.entityPlayer.addPotionEffect((new PotionEffect(17, 1200)));
                }
            }
        }
    }

    @SubscribeEvent
    public void onHurt(LivingHurtEvent ev) {
        if (ev.entityLiving != null) {
            if (ev.entityLiving instanceof EntityPlayer) {
                EntityPlayer pl = (EntityPlayer) ev.entityLiving;
                if (ev.source.getEntity() instanceof EntityCrawler || ev.source.getEntity() instanceof EntityZombieDayZ) {
                    if (this.random.nextFloat() <= 0.01f) {
                        pl.addPotionEffect(new PotionEffect(Effect.zombificationId, 6000));
                    }
                }
                double bleedingChance = Math.max(0.05f * ev.ammount - (0.05f * pl.getTotalArmorValue()), 0.01f);
                if (ev.source != DamageSource.fall && ev.source != DamageSource.generic && !ev.source.isMagicDamage() && ev.source != DamageSource.starve && ev.source != DamageSource.drown && ev.ammount >= 1.0f && this.random.nextFloat() <= bleedingChance) {
                    pl.addPotionEffect(new PotionEffect(Effect.bleedingId, 2400));
                }
            }
        }
    }
}
