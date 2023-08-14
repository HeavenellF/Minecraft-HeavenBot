package net.heavenell.heavenbot.gamechat;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.text.Text;

public class AutoGreeting implements ClientReceiveMessageEvents.Game{
    private static boolean response = false;
    @Override
    public void onReceiveGameMessage(Text message, boolean overlay) {
        String messageString = Text.Serializer.toJson(message).toLowerCase();
        if (messageString.contains("joined the game")) {
            response = true;
        }
    }
}
