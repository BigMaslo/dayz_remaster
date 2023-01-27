package com.github.dayzminecraft.dayzminecraft.common.entities;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityZombieDayZ extends EntityMob {
    public String texture;

    public EntityZombieDayZ(World par1World) {
        super(par1World);
        texture = getRandomZombieTexture();
        setHealth(30F);
        float moveSpeed = 0.5F;
        getNavigator().setBreakDoors(true);
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayerMP.class, moveSpeed, false));
        tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, moveSpeed, false));
        tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityZombieDayZ.class, moveSpeed, true));
        tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityVillager.class, moveSpeed, true));
        tasks.addTask(5, new EntityAIMoveThroughVillage(this, moveSpeed, false));
        tasks.addTask(6, new EntityAIWander(this, 0.3F));
        tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8F));
        tasks.addTask(8, new EntityAILookIdle(this));
        tasks.addTask(9, new AIBreakDoors(this));
        targetTasks.addTask(10, new EntityAIHurtByTarget(this, false));
        targetTasks.addTask(11, new EntityAINearestAttackableTarget(this, EntityPlayerMP.class, 0, true));
        targetTasks.addTask(12, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
        targetTasks.addTask(13, new EntityAINearestAttackableTarget(this, EntityVillager.class, 0, false));
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4);
    }

    private String getRandomZombieTexture() {
        return "zombie" + rand.nextInt(7) + ".png";
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    protected boolean isAIEnabled() {
        return true;
    }

    @Override
    protected String getLivingSound() {
        return "mob.zombie.say";
    }

    @Override
    protected String getHurtSound() {
        return "mob.zombie.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.zombie.death";
    }

    @Override
    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
        this.playSound("mob.zombie.step", 0.15F, 1.0F);
    }

    @Override
    protected Entity findPlayerToAttack() {
        EntityPlayer entityplayer = worldObj.getClosestVulnerablePlayerToEntity(this, 16D);

        if (entityplayer != null && canEntityBeSeen(entityplayer)) {
            return entityplayer;
        } else {
            return null;
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float damage) {
        if (super.attackEntityFrom(damageSource, damage)) {
            Entity entity = damageSource.getEntity();

            if (riddenByEntity == entity || ridingEntity == entity) {
                return true;
            }

            if (entity != this) {
                entityToAttack = entity;
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!worldObj.isRemote && worldObj.difficultySetting.equals(EnumDifficulty.PEACEFUL)) {
            setDead();
        }
    }

    @Override
    protected void attackEntity(Entity entity, float distanceToEntity) {
        if (attackTime <= 0 && distanceToEntity < 2.0F && entity.boundingBox.maxY > boundingBox.minY && entity.boundingBox.minY < boundingBox.maxY) {
            attackTime = 20;
            attackEntityAsMob(entity);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
    }
}