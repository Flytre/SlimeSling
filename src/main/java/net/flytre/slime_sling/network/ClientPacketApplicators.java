package net.flytre.slime_sling.network;

import net.flytre.flytre_lib.api.base.util.InventoryUtils;
import net.flytre.slime_sling.SlimeSling;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class ClientPacketApplicators {

    public static void apply(SlimeSlingPacket packet) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        assert player != null;
        MinecraftClient.getInstance().execute(() -> {
            if (InventoryUtils.getHoldingStack(player, i -> i.getItem() == SlimeSling.SLIME_SLING) != null) {
                player.setVelocity(packet.getVelX(), packet.getVelY(), packet.getVelZ());
                player.setYaw(packet.getYaw());
                player.setPitch(packet.getPitch());
            }
        });
    }
}
