package dev.jxnnik.customadvancementframe.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.*;

public class PluginConfig {

    private final File file;
    private final Gson gson;
    private final ExecutorService pool;
    private JsonObject json;

    public PluginConfig() {
        this.file = new File("plugins/CustomAdvancementFrame/config.json");
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.pool = Executors.newFixedThreadPool(2);
        this.initFile();
    }

    private void initFile() {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (!file.exists()) {
            try (final PrintWriter writer = new PrintWriter(file)) {
                writer.print(gson.toJson(json = new JsonObject()));
                JsonObject joinEvent = new JsonObject();
                json.add("player_join", createDefaultEventObject("totem_of_undying", "§7Hello§8! §7This is §eCustomAdvancementFrame §7by §bjxnnik(.dev)", true));
                json.add("player_quit", createDefaultEventObjectPermission("player_head", "§e%player% §7has left the server§8.", "frame.show.quit", true, true));
                json.add("player_change_world", createDefaultEventObject("grass_block", "§7You changed the world§8. §c%old_world% §8→ §e%new_world%", true));
                json.add("player_death", createDefaultEventObjectTwoMessages("netherite_sword", "§7You successfully killed §e%player%§8!", "§7You has been killed by §c%killer%§8.", true));
                save();
            } catch (FileNotFoundException ignored) { }
        } else {
            try {
                json = new JsonParser().parse(new FileReader(file)).getAsJsonObject();
            } catch (FileNotFoundException ignored) { }
        }
    }

    public void save() {
        pool.execute(() -> {
            try (final PrintWriter writer = new PrintWriter(file)) {
                writer.print(gson.toJson(json));
                writer.flush();
            } catch (FileNotFoundException ignored) { }
        });
    }

    private JsonObject createDefaultEventObject(String material, String message, boolean enable) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("enable", enable);
        jsonObject.addProperty("material", material);
        jsonObject.addProperty("message", message);
        return jsonObject;
    }

    private JsonObject createDefaultEventObjectTwoMessages(String material, String message, String playerMessage, boolean enable) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("enable", enable);
        jsonObject.addProperty("material", material);
        jsonObject.addProperty("player_message", playerMessage);
        jsonObject.addProperty("killer_message", message);
        return jsonObject;
    }

    private JsonObject createDefaultEventObjectPermission(String material, String message, String permission, boolean enable, boolean opPlayers) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("enable", enable);
        jsonObject.addProperty("material", material);
        jsonObject.addProperty("message", message);
        jsonObject.addProperty("only_with_permission", opPlayers);
        jsonObject.addProperty("permission", permission);
        return jsonObject;
    }

    public JsonObject getJsonObject(String eventKey) {
        return json.get(eventKey).getAsJsonObject();
    }
}
