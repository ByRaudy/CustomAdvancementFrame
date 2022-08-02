package dev.jxnnik.customadvancementframe.listener;

import com.google.gson.JsonObject;
import dev.jxnnik.customadvancementframe.CustomAdvancementFrame;
import dev.jxnnik.customadvancementframe.util.AdvancementFrame;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void hanldePlayerDeath(PlayerDeathEvent event) {
        JsonObject jsonObject = CustomAdvancementFrame.getInstance().getPluginConfig().getJsonObject("player_death");

        if (jsonObject.get("enable").getAsBoolean()) {
            if (event.getPlayer().getKiller() != null) {
                new AdvancementFrame("minecraft:" + jsonObject.get("material").getAsString(), jsonObject.get("player_message").getAsString().replace("%killer%", event.getPlayer().getKiller().getName()), event.getPlayer());

                Bukkit.getScheduler().runTaskLater(CustomAdvancementFrame.getInstance(), () -> {
                    new AdvancementFrame("minecraft:" + jsonObject.get("material").getAsString(), jsonObject.get("killer_message").getAsString().replace("%player%", event.getPlayer().getName()), event.getPlayer().getKiller());
                }, 5);
            }
        }
    }
}
