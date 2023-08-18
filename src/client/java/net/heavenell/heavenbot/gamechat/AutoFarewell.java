package net.heavenell.heavenbot.gamechat;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;

public class AutoFarewell implements ClientReceiveMessageEvents.Chat{


    @Override
    public void onReceiveChatMessage(Text message, @Nullable SignedMessage signedMessage, @Nullable GameProfile sender, MessageType.Parameters params, Instant receptionTimestamp) {
        String messageString = Text.Serializer.toJson(message).toLowerCase();
        if ((messageString.contains("matanene") || messageString.contains("abayo")) && !sender.getName().equals("Heavenell")) {
    }
}
