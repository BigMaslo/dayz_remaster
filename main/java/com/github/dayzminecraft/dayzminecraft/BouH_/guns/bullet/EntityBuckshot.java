package com.github.dayzminecraft.dayzminecraft.BouH_.guns.bullet;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityBuckshot extends EntityBullet {
    public EntityBuckshot(World p_i1753_1_) {
        super(p_i1753_1_);
        this.renderDistanceWeight = 10.0D;
        this.setSize(0.5F, 0.5F);
    }

    public EntityBuckshot(World p_i1756_1_, EntityLivingBase p_i1756_2_, float inaccuracy, float damage) {
        super(p_i1756_1_, p_i1756_2_, inaccuracy, damage);
        this.maxLifeTime = 6;
    }
}
