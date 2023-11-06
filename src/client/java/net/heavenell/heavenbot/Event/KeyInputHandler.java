package net.heavenell.heavenbot.Event;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdatePlayerAbilitiesC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.GameModeCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.world.GameMode;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class KeyInputHandler{
    public static final String KEY_CATEGORY_TUTORIAL = "key.category.tutorialmod.tutorial";
    public static final String KEY_CATEGORY_TUTORIAL2 = "key.category.tutorialmod.tutorial2";
    public static final String KEY_DRINK_WATER = "key.tutorialmod.drink_water";
    public static final String KEY_DRINK_WATER2 = "key.tutorialmod.drink_water2";


    public static KeyBinding spectatorKey;
    public static KeyBinding survivalKey;
    public static boolean AutoRoll = false;

    public static void registerKeyInputs() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if(spectatorKey.wasPressed()) {
                // This happens when our custom key is pressed
                client.player.networkHandler.sendChatMessage("/roll");
//                mc.interactionManager.setGameModes(GameMode.SURVIVAL, GameMode.CREATIVE);
//                client.interactionManager.setGameMode(GameMode.SURVIVAL);
//                client.player.getAbilities().allowFlying = false;
//                client.setScreen(new GameMenuScreen(true));
//                client.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(100.0f,100.0f,0.0f,false));
//                client.player.fallDistance = 60.0f;
//                client.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
            }
            else if (survivalKey.wasPressed()) {
//                PlayerAbilities playerAbilities = client.player.getAbilities();
//                playerAbilities.setFlySpeed(1f);
//                mc.interactionManager.setGameModes(GameMode.CREATIVE, GameMode.SURVIVAL);
//                client.interactionManager.setGameMode(GameMode.CREATIVE);
//                client.player.getAbilities().flying = true;
//                client.player.swingHand(Hand.OFF_HAND);
                if (AutoRoll == false){
                    AutoRoll = true;
                    client.player.sendMessage(Text.literal("Auto roll On"));
                } else AutoRoll = false;
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