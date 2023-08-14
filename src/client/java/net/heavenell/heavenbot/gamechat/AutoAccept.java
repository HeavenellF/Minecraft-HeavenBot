package net.heavenell.heavenbot.gamechat;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.text.Text;

public class AutoAccept implements ClientReceiveMessageEvents.Game{
    private static boolean response = false;
    @Override
    public void onReceiveGameMessage(Text message, boolean overlay) {
        String messageString = Text.Serializer.toJson(message).toLowerCase();
        if (messageString.contains("has sent a request to teleport to you")) {
            response = true;
        }
    }

    public static void sendchat() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (response) {
                client.player.networkHandler.sendChatCommand("tpaccept");
                response = false;
            }
        });
    }
}
