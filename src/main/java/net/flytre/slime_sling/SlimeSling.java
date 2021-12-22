package net.flytre.slime_sling;

import net.fabricmc.api.ModInitializer;
import net.flytre.flytre_lib.api.base.util.PacketUtils;
import net.flytre.slime_sling.network.BouncePacket;
import net.flytre.slime_sling.network.SlimeSlingPacket;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SlimeSling implements ModInitializer {

    public static final Item SLIME_SLING = new SlimeSlingItem(new Item.Settings().group(ItemGroup.TOOLS).maxCount(1));
    public static final Item SLIME_BOOTS = new SlimeBootsItem(new Item.Settings().group(ItemGroup.TOOLS));

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier("slime_sling", "slime_sling"), SLIME_SLING);
        Registry.register(Registry.ITEM, new Identifier("slime_sling", "slime_boots"), SLIME_BOOTS);

        PacketUtils.registerS2CPacket(SlimeSlingPacket.class, SlimeSlingPacket::new);
        PacketUtils.registerC2SPacket(BouncePacket.class, BouncePacket::new);

    }
}
