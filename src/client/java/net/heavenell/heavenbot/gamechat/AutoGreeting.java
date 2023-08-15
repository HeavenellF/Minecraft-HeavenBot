package net.heavenell.heavenbot.gamechat;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.text.Text;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class AutoGreeting implements ClientReceiveMessageEvents.Game{
    private static boolean response = false;
    private static String playerName;
    private static final Map<String, String> greetingsMap = new HashMap<>();
    @Override
    public void onReceiveGameMessage(Text message, boolean overlay) {
        String messageString = Text.Serializer.toJson(message);
        if (messageString.contains("multiplayer.player.joined")) {
            playerName = extractPlayerNameFromMessage(messageString);
            response = true;
            sendchat();
        }
    }

    public static void sendchat() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (response) {
                if (playerName != null) {
                    String greeting = greetingsMap.getOrDefault(playerName, "Hi");
                    client.player.networkHandler.sendChatMessage(greeting);
                }
                response = false;
            }
        });
    }

    public static void loadGreetings() {
        InputStream inputStream = AutoGreeting.class.getResourceAsStream("/greeting.json");

        if (inputStream != null) {
            try {
                JsonObject greetingsJson = JsonParser.parseReader(new InputStreamReader(inputStream)).getAsJsonObject();
                for (Map.Entry<String, JsonElement> entry : greetingsJson.entrySet()) {
                    greetingsMap.put(entry.getKey(), entry.getValue().getAsString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String extractPlayerNameFromMessage(String messageJson) {
        try {
            JsonObject json = JsonParser.parseString(messageJson).getAsJsonObject();
            if (json.has("with")) {
                JsonArray withArray = json.getAsJsonArray("with");
                if (withArray.size() > 0) {
                    JsonObject playerObject = withArray.get(0).getAsJsonObject();
                    if (playerObject.has("insertion")) {
                        return playerObject.get("insertion").getAsString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
