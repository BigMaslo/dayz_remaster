package com.github.dayzminecraft.dayzminecraft.BouH_.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityGrenade extends EntityThrowable {
    public EntityGrenade(World p_i1773_1_) {
        super(p_i1773_1_);
    }

    public EntityGrenade(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    @Override
    protected void onImpact(MovingObjectPosition p_70184_1_) {
        if (!this.worldObj.isRemote) {
            this.worldObj.createExplosion(this.getThrower(), this.posX, this.posY, this.posZ, 3.0f, true);
        }
        this.setDead();
    }
}
