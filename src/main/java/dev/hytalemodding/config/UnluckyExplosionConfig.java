package dev.hytalemodding.config;

import com.hypixel.hytale.server.core.entity.ExplosionConfig;

public class UnluckyExplosionConfig extends ExplosionConfig {

    public UnluckyExplosionConfig(Configuration config) {
        this.damageBlocks = true;
        this.damageEntities = true;
        this.blockDamageRadius = config.getExplosionRadius();
        this.entityDamageRadius = 4.0f;
        this.entityDamage = config.getExplosionDamage();
        this.blockDamageFalloff = 7.0f;
    }
}
