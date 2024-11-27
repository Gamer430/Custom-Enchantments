package org.kosoc.customenchants.utils;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ParticleRegistry {
    public static final DefaultParticleType DASH_PARTICLE = FabricParticleTypes.simple();
    public static void registerParticles(){
        Registry.register(Registries.PARTICLE_TYPE, new Identifier("customenchants", "dash_particle"), DASH_PARTICLE);
    }
}
