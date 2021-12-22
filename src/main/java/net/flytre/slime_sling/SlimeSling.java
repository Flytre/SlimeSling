package net.flytre.slime_sling;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SlimeSling implements ModInitializer {

    public static final Item SLIME_SLING = new SlimeSlingItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1));
    public static final Item SLIME_BOOTS = new SlimeBootsItem(new Item.Settings().group(ItemGroup.TOOLS));

    public static final Identifier SLING_PACKET = new Identifier("slime_sling","slung");
    public static final Identifier BOUNCE_PACKET = new Identifier("slime_sling","bounced");

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("slime_sling", "slime_sling"), SLIME_SLING);
        Registry.register(Registry.ITEM, new Identifier("slime_sling", "slime_boots"), SLIME_BOOTS);

        ServerPlayNetworking.registerGlobalReceiver(BOUNCE_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                if(player.getEquippedStack(EquipmentSlot.FEET).getItem() instanceof SlimeBootsItem)
                    player.fallDistance = 0.0f;
            });
        });
    }
}
