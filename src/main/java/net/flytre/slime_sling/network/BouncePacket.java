package net.flytre.slime_sling.network;

import net.flytre.slime_sling.SlimeBootsItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.server.network.ServerPlayNetworkHandler;

public class BouncePacket implements Packet<ServerPlayPacketListener> {

    public BouncePacket() {

    }

    public BouncePacket(PacketByteBuf buf) {

    }

    @Override
    public void write(PacketByteBuf buf) {

    }

    @Override
    public void apply(ServerPlayPacketListener listener) {
        ServerPlayNetworkHandler handler = (ServerPlayNetworkHandler) listener;
        var player = handler.getPlayer();
        player.server.execute(() -> {
            if (player.getEquippedStack(EquipmentSlot.FEET).getItem() instanceof SlimeBootsItem)
                player.fallDistance = 0.0f;
        });
    }
}
