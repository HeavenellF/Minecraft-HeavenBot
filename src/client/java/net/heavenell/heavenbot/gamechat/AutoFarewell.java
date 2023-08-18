package net.heavenell.heavenbot.gamechat;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class AutoFarewell implements ClientReceiveMessageEvents.Chat{
    private static boolean response = false;
    private Instant lastFarewellTime = Instant.MIN;
    private static String farewell = "matanene!";

    @Override
    public void onReceiveChatMessage(Text message, @Nullable SignedMessage signedMessage, @Nullable GameProfile sender, MessageType.Parameters params, Instant receptionTimestamp) {
        String messageString = Text.Serializer.toJson(message).toLowerCase();
        if ((messageString.contains("matanene") || messageString.contains("abayo")) && !sender.getName().equals("Heavenell")) {
            Duration cooldownDuration = Duration.ofSeconds(10);
            Instant currentTime = Instant.now();
            Duration timeSinceLastFarewell = Duration.between(lastFarewellTime, currentTime);

            if (timeSinceLastFarewell.compareTo(cooldownDuration) >= 0) {
                response = true;
                lastFarewellTime = currentTime;
                sendchat();
            }
        }
    }

    public static void sendchat() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (response) {
                client.player.networkHandler.sendChatMessage(farewell);
                response = false;
            }
        });
    }
}
