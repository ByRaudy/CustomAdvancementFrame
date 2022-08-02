package dev.jxnnik.customadvancementframe.listener;

import com.google.gson.JsonObject;
import dev.jxnnik.customadvancementframe.CustomAdvancementFrame;
import dev.jxnnik.customadvancementframe.util.AdvancementFrame;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void handlePlayerQuit(PlayerQuitEvent event) {
        JsonObject jsonObject = CustomAdvancementFrame.getInstance().getPluginConfig().getJsonObject("player_quit");

        if (jsonObject.get("enable").getAsBoolean()) {
            if (jsonObject.get("only_with_permission").getAsBoolean()) {
                Bukkit.getOnlinePlayers().stream().filter(player -> player.getUniqueId() != event.getPlayer().getUniqueId()).filter(player -> player.hasPermission(jsonObject.get("permission").getAsString())).forEach(player -> new AdvancementFrame("minecraft:" + jsonObject.get("material").getAsString(), jsonObject.get("message").getAsString().replace("%player%", event.getPlayer().getName()), player));
            } else {
                if ((Bukkit.getOnlinePlayers().size() - 1) > 0) {
                    Bukkit.getOnlinePlayers().stream().filter(player -> player.getUniqueId() != event.getPlayer().getUniqueId()).forEach(player -> new AdvancementFrame("minecraft:" + jsonObject.get("material").getAsString(), jsonObject.get("message").getAsString().replace("%player%", event.getPlayer().getName()), player));
                }
            }
        }
    }
}
