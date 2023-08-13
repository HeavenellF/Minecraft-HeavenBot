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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.Duration;
import java.util.Random;

public class AutoResponse implements ClientReceiveMessageEvents.Chat{
    private static boolean response = false;
    private Instant lastResponseTime = Instant.MIN;

    private static final String STRING_ONEMOMENT = ",onemoment";
    private static final String STRING_CHU = ",chu";
    private static final String STRING_RARE_RESPONSE = "mwwah!";
    private static final String KOYORIOP_PATH = "/KoyoriOP.json";
    private static String[] responses;
    private static String senderName;
    @Override
    public void onReceiveChatMessage(Text message, @Nullable SignedMessage signedMessage, @Nullable GameProfile sender, MessageType.Parameters params, Instant receptionTimestamp) {
        String messageString = Text.Serializer.toJson(message).toLowerCase();
        if (messageString.contains("heaven") && !sender.getName().equals("Heavenell")) {
            Duration cooldownDuration = Duration.ofSeconds(1);
            Instant currentTime = Instant.now();
            Duration timeSinceLastResponse = Duration.between(lastResponseTime, currentTime);

            if (timeSinceLastResponse.compareTo(cooldownDuration) >= 0) {
                response = true;
                lastResponseTime = currentTime;
                senderName = sender.getName();
            }
        }
    }

    public static void loadResponses() {
        try {
            InputStream inputStream = AutoResponse.class.getResourceAsStream(KOYORIOP_PATH);
            JsonArray responsesArray = new JsonParser().parse(
                    new InputStreamReader(inputStream)
            ).getAsJsonArray();

            responses = new String[responsesArray.size()];
            for (int i = 0; i < responsesArray.size(); i++) {
                responses[i] = responsesArray.get(i).getAsString();
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendchat() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (response) {
                if (responses != null && responses.length > 0) {
                    Random random = new Random();
                    int randomIndex = random.nextInt(responses.length);
                    String randomResponse = responses[randomIndex];
                    if (randomResponse == "chu"){
                        client.player.networkHandler.sendChatMessage(STRING_CHU);
                        client.player.networkHandler.sendChatMessage(senderName + ", " + STRING_RARE_RESPONSE);
                    }
                    else {
                        client.player.networkHandler.sendChatMessage(STRING_ONEMOMENT);
                        client.player.networkHandler.sendChatMessage(randomResponse);
                    }
                }
                response = false;
            }
        });
    }
}
