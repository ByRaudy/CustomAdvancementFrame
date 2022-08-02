package dev.jxnnik.customadvancementframe;

import dev.jxnnik.customadvancementframe.listener.PlayerChangedWorldListener;
import dev.jxnnik.customadvancementframe.listener.PlayerDeathListener;
import dev.jxnnik.customadvancementframe.listener.PlayerJoinListener;
import dev.jxnnik.customadvancementframe.listener.PlayerQuitListener;
import dev.jxnnik.customadvancementframe.util.PluginConfig;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class CustomAdvancementFrame extends JavaPlugin {

    private static CustomAdvancementFrame instance;
    private PluginConfig pluginConfig;

    @Override
    public void onEnable() {
        instance = this;
        pluginConfig = new PluginConfig();

        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChangedWorldListener(), this);
    }

    public static CustomAdvancementFrame getInstance() {
        return instance;
    }
}