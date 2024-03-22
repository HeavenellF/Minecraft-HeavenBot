package net.heavenell.heavenbot.Event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.heavenell.heavenbot.gui.SettingScreen;
import net.heavenell.heavenbot.setting.HeavenBotSetting;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class RegisterKey {

    public static final String KEY_CATEGORY_TUTORIAL = "HeavenBot";
    public static final String KEY_CONFIG = "Open setting";
    private static final HeavenBotSetting setting = new HeavenBotSetting();

    public static KeyBinding configKey;
    public static void registerKeyInputs(){
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (configKey.wasPressed()) {
                client.setScreen(new SettingScreen(setting));
            }
        });
    }
    public static void register() {
        configKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_CONFIG,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                KEY_CATEGORY_TUTORIAL
        ));

        registerKeyInputs();
    }
}
