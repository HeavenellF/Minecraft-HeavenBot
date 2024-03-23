package net.heavenell.heavenbot.gamechat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.heavenell.heavenbot.setting.HeavenBotSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;

public class AutoFarewell implements ClientReceiveMessageEvents.Chat{
    private static final HeavenBotSetting setting = HeavenBotSetting.getInstance();
    private static boolean response = false;
    private Instant lastFarewellTime = Instant.MIN;
    private static String farewell = "matanene!";

    @Override
    public void onReceiveChatMessage(Text message, @Nullable SignedMessage signedMessage, @Nullable GameProfile sender, MessageType.Parameters params, Instant receptionTimestamp) {
        String messageString = Text.Serialization.toJsonString(message).toLowerCase();
        // this part check the "translate" if its not command.message.display or whisper
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(messageString).getAsJsonObject();
        if ((messageString.contains("matanene") || messageString.contains("abayo")) && !sender.getName().equals("Heavenell") && !jsonObject.get("translate").getAsString().contains("commands.message.display")) {
            Duration cooldownDuration = Duration.ofSeconds(10);
            Instant currentTime = Instant.now();
            Duration timeSinceLastFarewell = Duration.between(lastFarewellTime, currentTime);

            if (timeSinceLastFarewell.compareTo(cooldownDuration) >= 0 && setting.isAutoFarewell()) {
                response = true;
                lastFarewellTime = currentTime;
            }
        }
    }

    public static void sendchat(MinecraftClient client) {
        if (response) {
            client.player.networkHandler.sendChatMessage(farewell);
            response = false;
        }
    }
}
