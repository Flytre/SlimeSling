package net.flytre.slime_sling.network;

import net.flytre.flytre_lib.api.base.util.InventoryUtils;
import net.flytre.slime_sling.SlimeSling;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;

public class SlimeSlingPacket implements Packet<ClientPlayPacketListener> {

    private final double velX;
    private final double velY;
    private final double velZ;
    private final float yaw;
    private final float pitch;


    public SlimeSlingPacket(PlayerEntity player) {
        velX = player.getVelocity().x;
        velY = player.getVelocity().y;
        velZ = player.getVelocity().z;
        yaw = player.getYaw();
        pitch = player.getPitch();
    }

    public SlimeSlingPacket(double velX, double velY, double velZ, float yaw, float pitch) {
        this.velX = velX;
        this.velY = velY;
        this.velZ = velZ;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public SlimeSlingPacket(PacketByteBuf buf) {
        this.velX = buf.readDouble();
        this.velY = buf.readDouble();
        this.velZ = buf.readDouble();
        this.yaw = buf.readFloat();
        this.pitch = buf.readFloat();
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeDouble(velX);
        buf.writeDouble(velY);
        buf.writeDouble(velZ);
        buf.writeFloat(yaw);
        buf.writeFloat(pitch);
    }

    @Override
    public void apply(ClientPlayPacketListener listener) {
        ClientPacketApplicators.apply(this);
    }


    public double getVelX() {
        return velX;
    }

    public double getVelY() {
        return velY;
    }

    public double getVelZ() {
        return velZ;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}
