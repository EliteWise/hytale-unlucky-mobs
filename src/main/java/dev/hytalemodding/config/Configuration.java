package dev.hytalemodding.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class Configuration {

    public static final BuilderCodec<Configuration> CODEC = BuilderCodec.builder(Configuration.class, Configuration::new)
            .append(new KeyedCodec<Integer>("Explosion-Chance", Codec.INTEGER),
                    (config, value) -> config.explosionChance = value,
                    (config) -> config.explosionChance).add()
            .append(new KeyedCodec<Integer>("Explosion-Radius", Codec.INTEGER),
                    (config, value) -> config.explosionRadius = value,
                    (config) -> config.explosionRadius).add()
            .append(new KeyedCodec<Float>("Explosion-Damage", Codec.FLOAT),
                    (config, value) -> config.explosionDamage = value,
                    (config) -> config.explosionDamage).add()
            .build();

    private int explosionChance = 50;
    private int explosionRadius = 7;
    private float explosionDamage = 15.0f;

    public Configuration() {
    }

    public int getExplosionChance() {
        return explosionChance;
    }

    public void setExplosionChance(int explosionChance) {
        this.explosionChance = explosionChance;
    }

    public int getExplosionRadius() {
        return explosionRadius;
    }

    public void setExplosionRadius(int explosionRadius) {
        this.explosionRadius = explosionRadius;
    }

    public float getExplosionDamage() {
        return explosionDamage;
    }

    public void setExplosionDamage(float explosionDamage) {
        this.explosionDamage = explosionDamage;
    }
}
