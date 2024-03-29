package net.heavenell.heavenbot.gamechat;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

public class ReviveOnCall implements ClientReceiveMessageEvents.Chat{
    private static boolean response = false;
    @Override
    public void onReceiveChatMessage(Text message, @Nullable SignedMessage signedMessage, @Nullable GameProfile sender, MessageType.Parameters params, Instant receptionTimestamp) {
        String messageString = Text.Serialization.toJsonString(message).toLowerCase();
        MinecraftClient mc = MinecraftClient.getInstance();
        if (messageString.contains("respawn")) {
//            mc.player.requestRespawn();
            mc.player.networkHandler.sendPacket((Packet<?>)new ClientStatusC2SPacket(ClientStatusC2SPacket.Mode.PERFORM_RESPAWN));
            response = true;
            sendchat();
        }
    }

    public static void sendchat() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (response) {
                client.player.networkHandler.sendChatMessage("Thanks!");
                response = false;
            }
        });
    }
}
