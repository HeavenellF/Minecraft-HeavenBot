package net.heavenell.heavenbot.Event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_TUTORIAL = "key.category.tutorialmod.tutorial";
    public static final String KEY_CATEGORY_TUTORIAL2 = "key.category.tutorialmod.tutorial2";
    public static final String KEY_DRINK_WATER = "key.tutorialmod.drink_water";
    public static final String KEY_DRINK_WATER2 = "key.tutorialmod.drink_water2";


    public static KeyBinding spectatorKey;
    public static KeyBinding survivalKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(spectatorKey.wasPressed()) {
                // This happens when our custom key is pressed
//                client.interactionManager.setGameMode(GameMode.CREATIVE);
                client.player.networkHandler.sendChatMessage("/roll");
            }
            else if (survivalKey.wasPressed()){
//                client.interactionManager.setGameMode(GameMode.SURVIVAL);
            }
        });
    }
    public static void register() {
        spectatorKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_DRINK_WATER,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                KEY_CATEGORY_TUTORIAL
        ));
        survivalKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_DRINK_WATER2,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                KEY_CATEGORY_TUTORIAL2
        ));

        registerKeyInputs();
    }
}