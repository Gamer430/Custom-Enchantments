package org.kosoc.customenchants.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.jetbrains.annotations.Nullable;

public class DashParticle extends SpriteBillboardParticle {
    protected DashParticle(ClientWorld level, double xCoord, double yCoord, double zCoord,
                           SpriteProvider spriteset, double xd, double yd, double zd){
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);

        this.velocityMultiplier = 06f;
        this.x = xd;
        this.y = yd;
        this.z = zd;
        this.scale *= 0.75F;
        this.maxAge = 20;
        this.setSpriteForAge(spriteset);

        this.red = 1f;
        this.blue = 1f;
        this.green = 1f;
    }
    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider sprites;
        public Factory(SpriteProvider spriteset){
            this.sprites = spriteset;
        }

        @Override
        public @Nullable Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z,
                                                 double velocityX, double velocityY, double velocityZ) {
            return new DashParticle(world, x, y, z, this.sprites, velocityX, velocityY, velocityZ);
        }
    }

}
