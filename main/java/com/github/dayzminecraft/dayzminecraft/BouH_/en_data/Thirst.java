package com.github.dayzminecraft.dayzminecraft.BouH_.en_data;

import com.github.dayzminecraft.dayzminecraft.BouH_.network.NetworkHandler;
import com.github.dayzminecraft.dayzminecraft.BouH_.network.packets.data.PacketThirst;
import com.github.dayzminecraft.dayzminecraft.common.misc.DamageType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class Thirst implements IExtendedEntityProperties {
    private final EntityPlayer player;
    public float thirstSaturationLevel;
    public float thirstExhaustionLevel;
    public int thirstTime;
    private int thirst;

    public Thirst(EntityPlayer player) {
        this.player = player;
        this.thirst = 20;
        this.thirstSaturationLevel = 5.0f;
    }

    public static Thirst getThirst(EntityPlayer player) {
        return (Thirst) player.getExtendedProperties("Thirst");
    }

    @Override
    public void init(Entity entity, World world) {
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound properties = new NBTTagCompound();
        properties.setInteger("thirst", this.thirst);
        properties.setInteger("thirstTime", this.thirstTime);
        properties.setFloat("thirstSaturationLevel", this.thirstSaturationLevel);
        properties.setFloat("thirstExhaustionLevel", this.thirstExhaustionLevel);
        compound.setTag("Thirst", properties);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
            NBTTagCompound properties = (NBTTagCompound) compound.getTag("Thirst");
            if (properties != null) {
            this.thirst = properties.getInteger("thirst");
            this.thirstTime = properties.getInteger("thirstTime");
            this.thirstSaturationLevel = properties.getFloat("thirstSaturationLevel");
            this.thirstExhaustionLevel = properties.getFloat("thirstExhaustionLevel");
        }
    }

    public void addThirst(int amount, float mod) {
        this.thirst = (Math.min(this.thirst + amount, 20));
        this.thirstSaturationLevel = Math.min(this.thirstSaturationLevel + (float) amount * mod, 5.0f);
    }

    public void removeThirst(int amount) {
        this.thirst = MathHelper.clamp_int(this.thirst - amount, 0, 20);
        this.thirstSaturationLevel = 0.0f;
    }

    public void onUpdate() {
        EnumDifficulty enumdifficulty = this.player.worldObj.difficultySetting;
        if (this.thirstExhaustionLevel > 4.0F) {
            this.addExhaustion(-4.0f);
            if (this.thirstSaturationLevel > 0.0F) {
                this.thirstSaturationLevel = Math.max(this.thirstSaturationLevel - 4.0F, 0.0F);
            } else if (enumdifficulty != EnumDifficulty.PEACEFUL) {
                this.removeThirst(1);
            }
        }
        if (this.player.hurtTime > 0) {
            this.thirstTime = 0;
        }
        if (this.thirst <= 0) {
            ++this.thirstTime;
            if (this.thirstTime >= 100) {
                if (this.player.getHealth() > 10.0F || enumdifficulty == EnumDifficulty.HARD || this.player.getHealth() > 1.0F && enumdifficulty == EnumDifficulty.NORMAL) {
                    this.player.attackEntityFrom(DamageType.thirstDeath, 1.0F);
                }
                this.thirstTime = 0;
            }
        } else {
            this.thirstTime = 0;
        }
        this.packet();
    }

    public void addExhaustion(float p_75113_1_) {
        this.thirstExhaustionLevel = Math.min(this.thirstExhaustionLevel + p_75113_1_, 40.0F);
    }

    public void setExhaustion(float p_75113_1_) {
        this.thirstExhaustionLevel = p_75113_1_;
    }

    public void setSaturation(float p_75113_1_) {
        this.thirstSaturationLevel = p_75113_1_;
    }

    public int getThirst() {
        return this.thirst;
    }

    @SideOnly(Side.CLIENT)
    public void setThirst(int thirst) {
        this.thirst = thirst;
    }

    public void packet() {
        if (this.player instanceof EntityPlayerMP) {
            NetworkHandler.NETWORK.sendTo(new PacketThirst(this.getThirst(), this.thirstSaturationLevel), (EntityPlayerMP) this.player);
        }
    }
}
