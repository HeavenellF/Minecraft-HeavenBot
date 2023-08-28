package net.heavenell.heavenbot.Event;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.util.ActionResult;


public class OnDeathAutoRespawn {
    public static ActionResult onDeath() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player != null) {
            if (mc.player.isDead()) {
                mc.player.networkHandler.sendPacket(new ClientStatusC2SPacket(ClientStatusC2SPacket.Mode.PERFORM_RESPAWN));
            }
            if (mc.player.fallDistance >= 2.0f) {
                mc.player.fallDistance = 0.0f;
            }
        }
        return ActionResult.PASS;
    }
}
