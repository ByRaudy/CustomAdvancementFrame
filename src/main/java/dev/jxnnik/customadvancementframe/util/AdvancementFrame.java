package dev.jxnnik.customadvancementframe.util;

import dev.jxnnik.customadvancementframe.CustomAdvancementFrame;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class AdvancementFrame {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final JavaPlugin plugin;
    private final NamespacedKey namespacedKey;
    private final String icon;
    private final String title;

    public AdvancementFrame(String icon, String title, Player player) {
        this.plugin = CustomAdvancementFrame.getInstance();
        this.namespacedKey = new NamespacedKey(this.plugin, UUID.randomUUID().toString().split("-")[0]);
        this.icon = icon;
        this.title = title;
        try {
            Bukkit.getUnsafe().loadAdvancement(namespacedKey, json());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        send(player);
    }

    public void send(Player player) {
        grant(player);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            revoke(player);
        }, 20);
    }

    private void grant(Player player) {
        Advancement advancement = Bukkit.getAdvancement(namespacedKey);
        assert advancement != null;
        AdvancementProgress progress = player.getAdvancementProgress(advancement);
        if (!progress.isDone())
            for (String criteria : progress.getRemainingCriteria()) progress.awardCriteria(criteria);
    }

    private void revoke(Player player) {
        Advancement advancement = Bukkit.getAdvancement(namespacedKey);
        assert advancement != null;
        AdvancementProgress progress = player.getAdvancementProgress(advancement);
        if (progress.isDone()) for (String criteria : progress.getAwardedCriteria()) progress.revokeCriteria(criteria);
        Bukkit.getUnsafe().removeAdvancement(namespacedKey);
    }

    private String json() {
        JsonObject json = new JsonObject();
        JsonObject icon = new JsonObject();
        JsonObject display = new JsonObject();
        JsonObject criteria = new JsonObject();
        JsonObject trigger = new JsonObject();
        icon.addProperty("item", this.icon);
        display.add("icon", icon);
        display.addProperty("title", this.title);
        display.addProperty("description", "test description");
        display.addProperty("background", "minecraft:textures/gui/advancements/backgrounds/adventure.png");
        display.addProperty("frame", "goal");
        display.addProperty("announce_to_chat", false);
        display.addProperty("show_toast", true);
        display.addProperty("hidden", true);
        trigger.addProperty("trigger", "minecraft:impossible");
        criteria.add("impossible", trigger);
        json.add("criteria", criteria);
        json.add("display", display);
        return gson.toJson(json);
    }
}