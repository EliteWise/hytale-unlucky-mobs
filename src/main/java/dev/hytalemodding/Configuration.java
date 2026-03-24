package dev.hytalemodding;

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
            .append(new KeyedCodec<Boolean>("Damage-Player", Codec.BOOLEAN),
                    (config, value) -> config.damagePlayer = value,
                    (config) -> config.damagePlayer).add()
            .build();

    private int explosionChance = 50;
    private int explosionRadius = 20;
    private boolean damagePlayer = true;

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

    public boolean isDamagePlayer() {
        return damagePlayer;
    }

    public void setDamagePlayer(boolean damagePlayer) {
        this.damagePlayer = damagePlayer;
    }
}
