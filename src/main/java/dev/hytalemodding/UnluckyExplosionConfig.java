package dev.hytalemodding;

import com.hypixel.hytale.server.core.entity.ExplosionConfig;

public class UnluckyExplosionConfig extends ExplosionConfig {

    public UnluckyExplosionConfig() {
        this.damageBlocks = true;
        this.damageEntities = true;
        this.blockDamageRadius = 10;
        this.entityDamageRadius = 20.0f;
        this.entityDamage = 200.0f;
        this.blockDamageFalloff = 10.0f;
    }
}
