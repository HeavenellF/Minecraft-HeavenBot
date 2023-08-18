package net.heavenell.heavenbot.gamechat;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.Random;

public class AutoFarewell implements ClientReceiveMessageEvents.Chat{
    private stativ boolean response = false;

    @Override
    public void onReceiveChatMessage(Text message, @Nullable SignedMessage signedMessage, @Nullable GameProfile sender, MessageType.Parameters params, Instant receptionTimestamp) {
        String messageString = Text.Serializer.toJson(message).toLowerCase();
        if ((messageString.contains("matanene") || messageString.contains("abayo")) && !sender.getName().equals("Heavenell")) {
            sendchat();
        }
    }


    public static void sendchat() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (response) {
                if (responses != null && responses.length > 0) {
                    Random random = new Random();
                    int randomIndex = random.nextInt(responses.length);
                    String randomResponse = responses[randomIndex];
                    if (randomResponse.equals("chu")){
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
