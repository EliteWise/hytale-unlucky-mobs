package dev.hytalemodding;

import com.hypixel.hytale.builtin.buildertools.utils.FluidPatternHelper;
import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.spatial.SpatialResource;
import com.hypixel.hytale.math.block.BlockUtil;
import com.hypixel.hytale.math.util.ChunkUtil;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.protocol.packets.world.ServerSetFluid;
import com.hypixel.hytale.protocol.packets.world.SetFluids;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.asset.type.fluid.FireFluidTicker;
import com.hypixel.hytale.server.core.asset.type.fluid.Fluid;
import com.hypixel.hytale.server.core.entity.ExplosionConfig;
import com.hypixel.hytale.server.core.entity.ExplosionUtils;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.knockback.KnockbackComponent;
import com.hypixel.hytale.server.core.modules.collision.WorldUtil;
import com.hypixel.hytale.server.core.modules.entity.EntityModule;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathSystems;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.ParticleUtil;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.accessor.BlockAccessor;
import com.hypixel.hytale.server.core.universe.world.chunk.section.FluidSection;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import it.unimi.dsi.fastutil.objects.ObjectList;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nonnull;

public class DeathSystem extends DeathSystems.OnDeathSystem {

    @Nonnull
    @Override
    public Query<EntityStore> getQuery() {
        return Query.any();
    }

    @Override
    public void onComponentAdded(@Nonnull Ref ref, @Nonnull DeathComponent component, @Nonnull Store store, @Nonnull CommandBuffer commandBuffer) {
        Player player = (Player) store.getComponent(ref, Player.getComponentType());

        float chance = UnluckyMobs.getInstance().getConfig().get().getExplosionChance();
        Damage.EntitySource source = new Damage.EntitySource(ref);
        UnluckyExplosionConfig explosionConfig = new UnluckyExplosionConfig();

        World world = ((EntityStore) store.getExternalData()).getWorld();
        ComponentAccessor<ChunkStore> chunkStore = world.getChunkStore().getStore();

        TransformComponent transform = (TransformComponent) store.getComponent(ref, TransformComponent.getComponentType());
        if (transform == null) return;

        Vector3d deathLoc = transform.getPosition();

        SpatialResource<Ref<EntityStore>, EntityStore> playerSpatialResource =
                (SpatialResource) store.getResource(EntityModule.get().getPlayerSpatialResourceType());
        ObjectList<Ref<EntityStore>> playerRefs = SpatialResource.getThreadLocalReferenceList();
        playerSpatialResource.getSpatialStructure().collect(deathLoc, 75.0, playerRefs);

        System.out.println("Death triggered! chance=" + chance);
        if (Math.random() * 100 < chance) {
            ParticleUtil.spawnParticleEffect("Explosion_Big", deathLoc, playerRefs, store);

            ExplosionUtils.performExplosion(source, deathLoc, explosionConfig, ref, commandBuffer, chunkStore);
            System.out.println("Explosion!");

            int radius = 2;
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (dx * dx + dz * dz > radius * radius) continue;
                    int bx = (int)deathLoc.x + dx;
                    int bz2 = (int)deathLoc.z + dz;
                    long chunkKey = ChunkUtil.indexChunkFromBlock(bx, bz2);
                    BlockAccessor chunk = world.getChunkIfLoaded(chunkKey);
                    if (chunk == null) continue;

                    for (int dy = 0; dy >= -1; dy--) {
                        int by = (int)deathLoc.y + dy;
                        chunk.setBlock(bx, by, bz2, "Empty");
                    }

                    int by = (int)deathLoc.y - 1;
                    if (chunk.getBlock(bx, by - 1, bz2) != 0) {
                        chunk.setBlock(bx, by, bz2, "Deco_Fire");
                    }
                }
            }

            Damage deathInfo = component.getDeathInfo();
            if (deathInfo != null && deathInfo.getSource() instanceof Damage.EntitySource) {
                Ref attackerRef = ((Damage.EntitySource) deathInfo.getSource()).getRef();
                TransformComponent playerTransform = (TransformComponent) store.getComponent(attackerRef, TransformComponent.getComponentType());

                ComponentType<EntityStore, KnockbackComponent> knockbackComponentType = KnockbackComponent.getComponentType();
                KnockbackComponent knockbackComponent = (KnockbackComponent)commandBuffer.getComponent(attackerRef, knockbackComponentType);
                if (knockbackComponent == null) {
                    knockbackComponent = new KnockbackComponent();
                    knockbackComponent.setVelocity(new Vector3d(3, 0, 0));
                    commandBuffer.putComponent(attackerRef, knockbackComponentType, knockbackComponent);
                }
            }
        }
    }
}
