package com.dsh105.sparktrail.trail.type;

import com.dsh105.sparktrail.trail.EffectHolder;
import com.dsh105.sparktrail.trail.PacketEffect;
import com.dsh105.sparktrail.trail.ParticleType;


public class WaterDrip extends PacketEffect {

    public WaterDrip(EffectHolder effectHolder) {
        super(effectHolder, ParticleType.WATERDRIP);
    }

    @Override
    public String getNmsName() {
        return "dripWater";
    }

    @Override
    public float getSpeed() {
        return 0F;
    }

    @Override
    public int getParticleAmount() {
        return 50;
    }
}