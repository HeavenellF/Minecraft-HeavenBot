package net.heavenell.heavenbot.Event;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.ActionResult;


public class OnDeathAutoRespawn {
    public static ActionResult onDeath() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player != null) {
            if (mc.player.isDead()) {
                mc.player.networkHandler.sendPacket(new ClientStatusC2SPacket(ClientStatusC2SPacket.Mode.PERFORM_RESPAWN));
            }
            if (mc.player.isSneaking()){
                mc.player.getAbilities().setFlySpeed(0.35f);
            }
            else mc.player.getAbilities().setFlySpeed(1f);
            mc.player.fallDistance = 0.0f;
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
        }
        return ActionResult.PASS;
    }
}