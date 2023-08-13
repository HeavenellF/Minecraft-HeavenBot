package net.heavenell.heavenbot.gamechat;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.io.InputStreamReader;
import java.time.Instant;
import java.time.Duration;

public class AutoResponse implements ClientReceiveMessageEvents.Chat{
    private static boolean response = false;
    private Instant lastResponseTime = Instant.MIN;

    private static final String STRING_ONEMOMENT = ",onemoment";
    private static final String KOYORIOP_PATH = "list/KoyoriOP.json";
    private static String[] responses;
    @Override
    public void onReceiveChatMessage(Text message, @Nullable SignedMessage signedMessage, @Nullable GameProfile sender, MessageType.Parameters params, Instant receptionTimestamp) {
        String messageString = Text.Serializer.toJson(message).toLowerCase();
        if (messageString.contains("heaven") && !sender.getName().equals("Heavenell")) {
            Duration cooldownDuration = Duration.ofSeconds(10);
            Instant currentTime = Instant.now();
            Duration timeSinceLastResponse = Duration.between(lastResponseTime, currentTime);

            if (timeSinceLastResponse.compareTo(cooldownDuration) >= 0) {
                response = true;
                lastResponseTime = currentTime;
            }
        }
    }

    public static void loadResponses() {
        try {
            JsonArray responsesArray = new JsonParser().parse(
                    new InputStreamReader(AutoResponse.class.getResourceAsStream(KOYORIOP_PATH))
            ).getAsJsonArray();

            responses = new String[responsesArray.size()];
            for (int i = 0; i < responsesArray.size(); i++) {
                responses[i] = responsesArray.get(i).getAsString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void  autoresponse() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (response) {
                client.player.networkHandler.sendChatMessage(",onemoment");
                client.player.networkHandler.sendChatMessage("Konkoyo~!");
                response = false;
            }
        });
    }
}
