package net.heavenell.heavenbot.gamechat;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

public class AutoResponse implements ClientReceiveMessageEvents.Chat{
    static boolean response = false;
    @Override
    public void onReceiveChatMessage(Text message, @Nullable SignedMessage signedMessage, @Nullable GameProfile sender, MessageType.Parameters params, Instant receptionTimestamp) {
        String messageString = Text.Serializer.toJson(message).toLowerCase();
        if (messageString.contains("heaven")) {
            response = true;
        }
    }

    public static void  autoresponse() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (response) {
                // This happens when our custom key is pressed
                client.player.networkHandler.sendChatMessage("Yeah?");
                response = false;
            }
        });
    }
}
