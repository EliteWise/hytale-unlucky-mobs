package dev.hytalemodding;

import com.hypixel.hytale.server.core.entity.ExplosionConfig;

public class UnluckyExplosionConfig extends ExplosionConfig {

    public UnluckyExplosionConfig() {
        this.damageBlocks = true;
        this.damageEntities = true;
        this.blockDamageRadius = 7;
        this.entityDamageRadius = 4.0f;
        this.entityDamage = 15.0f;
        this.blockDamageFalloff = 7.0f;
    }
}
