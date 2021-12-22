package net.flytre.slime_sling.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.flytre.slime_sling.SlimeSling;
import net.minecraft.entity.player.PlayerEntity;

@Environment(EnvType.CLIENT)
public class SlimeSlingClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(SlimeSling.SLING_PACKET, (client, handler, buf, responseSender) -> {
            double x = buf.readDouble(), y = buf.readDouble(), z = buf.readDouble();
            float yaw = buf.readFloat(), pitch = buf.readFloat();
            PlayerEntity player = client.player;
            assert player != null;
            client.execute(() -> {
                player.setVelocity(x, y, z);
                player.setYaw(yaw);
                player.setPitch(pitch);
            });
        });
    }
}
