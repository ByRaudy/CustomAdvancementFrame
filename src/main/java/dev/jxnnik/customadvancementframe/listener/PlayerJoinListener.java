package dev.jxnnik.customadvancementframe.listener;

import com.google.gson.JsonObject;
import dev.jxnnik.customadvancementframe.CustomAdvancementFrame;
import dev.jxnnik.customadvancementframe.util.AdvancementFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        JsonObject jsonObject = CustomAdvancementFrame.getInstance().getPluginConfig().getJsonObject("player_join");

        if (jsonObject.get("enable").getAsBoolean())
            new AdvancementFrame("minecraft:" + jsonObject.get("material").getAsString(), jsonObject.get("message").getAsString().replace("%player%", event.getPlayer().getName()), event.getPlayer());
    }
}
