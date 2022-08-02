package dev.jxnnik.customadvancementframe.listener;

import com.google.gson.JsonObject;
import dev.jxnnik.customadvancementframe.CustomAdvancementFrame;
import dev.jxnnik.customadvancementframe.util.AdvancementFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerChangedWorldListener implements Listener {

    @EventHandler
    public void handlePlayerChangedWorld(PlayerChangedWorldEvent event) {
        JsonObject jsonObject = CustomAdvancementFrame.getInstance().getPluginConfig().getJsonObject("player_change_world");

        if (jsonObject.get("enable").getAsBoolean())
            new AdvancementFrame("minecraft:" + jsonObject.get("material").getAsString(), jsonObject.get("message").getAsString().replace("%old_world%", event.getFrom().getName()).replace("%new_world%", event.getPlayer().getWorld().getName()), event.getPlayer());
    }
}
