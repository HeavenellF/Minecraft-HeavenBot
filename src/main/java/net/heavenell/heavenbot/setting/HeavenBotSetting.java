package net.heavenell.heavenbot.setting;

import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.SimpleOption;

public class HeavenBotSetting {

    private static boolean autoGreeting = true;
    private static boolean autoResponse = true;
    private static boolean autoAccept = true;

    public static boolean isAutoGreeting() {
        return autoGreeting;
    }

    public static boolean isAutoResponse() {
        return autoResponse;
    }

    public static boolean isAutoAccept() {
        return autoAccept;
    }

    public static void setAutoGreeting(boolean autoGreeting) {
        if(autoGreeting) {
            HeavenBotSetting.autoGreeting = false;
        }
        else{
            HeavenBotSetting.autoGreeting = true;
        }
    }
}
