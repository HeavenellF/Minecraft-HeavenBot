package net.heavenell.heavenbot.Event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler{
    public static final String KEY_CATEGORY_TUTORIAL = "HeavenBot";
    public static final String KEY_TOGGLE_AUTOROLL = "Toggle Autoroll Key";
    public static final String KEY_FAKE_ROLL = "Fake Roll Key";


    public static KeyBinding fakerollKey;
    public static KeyBinding autorollKey;
    public static boolean AutoRoll = false;

    public static boolean FlyHack = false;

    public static void registerKeyInputs() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if(fakerollKey.wasPressed()) {
                // This happens when our custom key is pressed
                client.player.networkHandler.sendChatMessage("/roll");
//                if (FlyHack == false){
//                    FlyHack = true;
//                    client.player.getAbilities().allowFlying = true;
//                } else{
//                    FlyHack = false;
//                    client.player.getAbilities().allowFlying = false;
//                }
//                mc.interactionManager.setGameModes(GameMode.SURVIVAL, GameMode.CREATIVE);
//                client.interactionManager.setGameMode(GameMode.SURVIVAL);
//                client.player.getAbilities().allowFlying = false;
//                client.setScreen(new GameMenuScreen(true));
//                client.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(100.0f,100.0f,0.0f,false));
//                client.player.fallDistance = 60.0f;
//                client.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
            }
            else if (autorollKey.wasPressed()) {
//                PlayerAbilities playerAbilities = client.player.getAbilities();
//                playerAbilities.setFlySpeed(1f);
//                mc.interactionManager.setGameModes(GameMode.CREATIVE, GameMode.SURVIVAL);
//                client.interactionManager.setGameMode(GameMode.CREATIVE);
//                client.player.getAbilities().flying = true;
//                client.player.swingHand(Hand.OFF_HAND);
                if (AutoRoll == false){
                    AutoRoll = true;
                    client.player.sendMessage(Text.literal("AutoRoll on"));
                } else{
                    AutoRoll = false;
                    client.player.sendMessage(Text.literal("AutoRoll off"));
                }

//                client.player.getAbilities().allowFlying = true;
//                client.player.getAbilities().invulnerable = true;
//                MinecraftClient.getInstance().player.networkHandler.sendPacket(new UpdatePlayerAbilitiesC2SPacket(MinecraftClient.getInstance().player.getAbilities()));
//                MinecraftClient.getInstance().player.networkHandler.sendPacket(new PlayerAbilitiesS2CPacket(MinecraftClient.getInstance().player.getAbilities()));
//                client.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.Full(45d,45d, 45d, 45.0f,45.0f, true));
//                client.setScreen(null);
            }
        });
    }
    public static void register() {
        fakerollKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_TOGGLE_AUTOROLL,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                KEY_CATEGORY_TUTORIAL
        ));
        autorollKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_FAKE_ROLL,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                KEY_CATEGORY_TUTORIAL
        ));

        registerKeyInputs();
    }

}