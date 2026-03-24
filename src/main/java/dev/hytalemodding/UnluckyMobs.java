package dev.hytalemodding;

import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.util.Config;
import dev.hytalemodding.commands.ExampleCommand;
import dev.hytalemodding.events.ExampleEvent;

import javax.annotation.Nonnull;

public class UnluckyMobs extends JavaPlugin {

    private static UnluckyMobs instance;
    private final Config<Configuration> config = this.withConfig("UnluckyMobsConfig", Configuration.CODEC);

    public UnluckyMobs(@Nonnull JavaPluginInit init) {
        super(init);
        instance = this;
    }

    @Override
    protected void setup() {
        config.save();
        this.getCommandRegistry().registerCommand(new ExampleCommand("example", "An example command"));
        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, ExampleEvent::onPlayerReady);
        this.getEntityStoreRegistry().registerSystem(new DeathSystem());
    }

    public static UnluckyMobs getInstance() { return instance; }

    public Config<Configuration> getConfig() { return config; }
}