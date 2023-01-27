package com.github.dayzminecraft.dayzminecraft.BouH_.guns.bullet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class EntityBullet extends Entity implements IProjectile {
    public Entity shootingEntity;
    private int field_145791_d = -1;
    private int field_145792_e = -1;
    private int field_145789_f = -1;
    private Block field_145790_g;
    private int inData;
    private int ticksInAir;
    private float damage;
    protected int maxLifeTime = 300;

    public EntityBullet(World p_i1753_1_) {
        super(p_i1753_1_);
        this.renderDistanceWeight = 10.0D;
        this.setSize(0.5F, 0.5F);
    }

    public EntityBullet(World p_i1756_1_, EntityLivingBase p_i1756_2_, float inaccuracy, float damage) {
        super(p_i1756_1_);
        this.renderDistanceWeight = 10.0D;
        this.shootingEntity = p_i1756_2_;
        this.damage = damage;
        this.setSize(0.5F, 0.5F);
        this.setLocationAndAngles(p_i1756_2_.posX, p_i1756_2_.posY + (double) p_i1756_2_.getEyeHeight(), p_i1756_2_.posZ, p_i1756_2_.rotationYaw, p_i1756_2_.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        this.posY -= 0.10000000149011612D;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * 0.16F;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0F * (float) Math.PI);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 3.9F, inaccuracy);
    }

    protected void entityInit() {
        this.dataWatcher.addObject(16, (byte) 0);
    }

    public void setThrowableHeading(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_) {
        float f2 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
        p_70186_1_ /= f2;
        p_70186_3_ /= f2;
        p_70186_5_ /= f2;
        p_70186_1_ += this.rand.nextGaussian() * (double) (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double) p_70186_8_;
        p_70186_3_ += this.rand.nextGaussian() * (double) (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double) p_70186_8_;
        p_70186_5_ += this.rand.nextGaussian() * (double) (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * (double) p_70186_8_;
        p_70186_1_ *= p_70186_7_;
        p_70186_3_ *= p_70186_7_;
        p_70186_5_ *= p_70186_7_;
        this.motionX = p_70186_1_;
        this.motionY = p_70186_3_;
        this.motionZ = p_70186_5_;
        float f3 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_5_ * p_70186_5_);
        this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(p_70186_1_, p_70186_5_) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(p_70186_3_, f3) * 180.0D / Math.PI);
    }

    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_, float p_70056_8_, int p_70056_9_) {
        this.setPosition(p_70056_1_, p_70056_3_, p_70056_5_);
        this.setRotation(p_70056_7_, p_70056_8_);
    }

    @SideOnly(Side.CLIENT)
    public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
        this.motionX = p_70016_1_;
        this.motionY = p_70016_3_;
        this.motionZ = p_70016_5_;

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt_double(p_70016_1_ * p_70016_1_ + p_70016_5_ * p_70016_5_);
            this.rotationYaw = (float) (Math.atan2(p_70016_1_, p_70016_5_) * 180.0D / Math.PI);
            this.rotationPitch = (float) (Math.atan2(p_70016_3_, f) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        }
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.ticksExisted < 3) {
            if (this.worldObj.isRemote) {
                this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, this.motionX * 0.1f, this.motionY * 0.1f, this.motionZ * 0.1f);
            }
        }
        if (this.ticksExisted == this.maxLifeTime) {
            this.setDead();
        }
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, f) * 180.0D / Math.PI);
        }

        ++this.ticksInAir;
        Vec3 vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        Vec3 vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition movingobjectposition = this.worldObj.func_147447_a(vec31, vec3, false, true, false);
        vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

        if (movingobjectposition != null) {
            vec3 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }

        Entity entity = null;
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
        double d0 = 0.0D;
        int i;
        float f1;

        for (i = 0; i < list.size(); ++i) {
            Entity entity1 = (Entity) list.get(i);

            if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5)) {
                f1 = 0.3F;
                AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand(f1, f1, f1);
                MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);

                if (movingobjectposition1 != null) {
                    double d1 = vec31.distanceTo(movingobjectposition1.hitVec);

                    if (d1 < d0 || d0 == 0.0D) {
                        entity = entity1;
                        d0 = d1;
                    }
                }
            }
        }

        if (entity != null) {
            movingobjectposition = new MovingObjectPosition(entity);
        }

        if (movingobjectposition != null && movingobjectposition.entityHit instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) movingobjectposition.entityHit;

            if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer) this.shootingEntity).canAttackPlayer(entityplayer)) {
                movingobjectposition = null;
            }
        }

        float f2;
        float f4;

        if (movingobjectposition != null) {
            if (movingobjectposition.entityHit != null) {

                DamageSource damagesource = DamageSource.generic;

                if (this.shootingEntity != null) {
                    //damagesource = DamageSource.causePlayerDamage((EntityPlayer) this.shootingEntity);
                }

                if (movingobjectposition.entityHit.attackEntityFrom(damagesource, this.damage)) {
                    movingobjectposition.entityHit.hurtResistantTime = 0;
                    if (!(movingobjectposition.entityHit instanceof EntityEnderman)) {
                        if (movingobjectposition.entityHit.isEntityAlive()) {
                            this.setDead();
                        }
                    }
                }
            } else {
                this.field_145791_d = movingobjectposition.blockX;
                this.field_145792_e = movingobjectposition.blockY;
                this.field_145789_f = movingobjectposition.blockZ;
                this.field_145790_g = this.worldObj.getBlock(this.field_145791_d, this.field_145792_e, this.field_145789_f);
                this.inData = this.worldObj.getBlockMetadata(this.field_145791_d, this.field_145792_e, this.field_145789_f);
                this.motionX = (float) (movingobjectposition.hitVec.xCoord - this.posX);
                this.motionY = (float) (movingobjectposition.hitVec.yCoord - this.posY);
                this.motionZ = (float) (movingobjectposition.hitVec.zCoord - this.posZ);
                f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                this.posX -= this.motionX / (double) f2 * 0.05000000074505806D;
                this.posY -= this.motionY / (double) f2 * 0.05000000074505806D;
                this.posZ -= this.motionZ / (double) f2 * 0.05000000074505806D;
                if (this.worldObj.isRemote) {
                    this.spawnParticles();
                }
                if (this.field_145790_g.getMaterial() == Material.glass) {
                    worldObj.func_147480_a(this.field_145791_d, this.field_145792_e, this.field_145789_f, false);
                }
                this.setDead();
                this.playSound(this.field_145790_g.stepSound.getBreakSound(), 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                if (this.field_145790_g.getMaterial() != Material.air) {
                    this.field_145790_g.onEntityCollidedWithBlock(this.worldObj, this.field_145791_d, this.field_145792_e, this.field_145789_f, this);
                }
            }
        }

        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        for (this.rotationPitch = (float) (Math.atan2(this.motionY, f2) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
        }

        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
        float f3 = 0.99F;

        if (this.isInWater()) {
            for (int l = 0; l < 4; ++l) {
                f4 = 0.25F;
                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double) f4, this.posY - this.motionY * (double) f4, this.posZ - this.motionZ * (double) f4, this.motionX, this.motionY, this.motionZ);
            }

            f3 = 0.8F;
        }

        this.motionX *= f3;
        this.motionY *= f3;
        this.motionZ *= f3;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.func_145775_I();
    }

    @SideOnly(Side.CLIENT)
    private void spawnParticles() {
        for (int s = 0; s < 12; s++) {
            Minecraft.getMinecraft().effectRenderer.addEffect((new EntityDiggingFX(this.worldObj, this.posX, this.posY, this.posZ, -this.motionX * 0.05f, -this.motionY * 0.05f, -this.motionZ * 0.05f, this.field_145790_g, this.inData)).applyColourMultiplier(this.field_145791_d, this.field_145792_e, this.field_145789_f).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {

    }

    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 0.0F;
    }
}
