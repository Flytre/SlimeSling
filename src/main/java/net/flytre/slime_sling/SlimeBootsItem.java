package net.flytre.slime_sling;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class SlimeBootsItem extends ArmorItem {
    public SlimeBootsItem(Settings settings) {
        super(new SlimeArmorMaterial(), EquipmentSlot.FEET, settings);
    }

    public static class SlimeArmorMaterial implements ArmorMaterial {
        private static final Ingredient EMPTY_REPAIR_MATERIAL = Ingredient.ofItems(Items.AIR);

        public SlimeArmorMaterial() {
        }

        @Override
        public int getDurability(EquipmentSlot slot) {
            return 0;
        }

        @Override
        public int getProtectionAmount(EquipmentSlot slot) {
            return 0;
        }


        @Override
        public int getEnchantability() {
            return 0;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.BLOCK_SLIME_BLOCK_PLACE;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return EMPTY_REPAIR_MATERIAL;
        }

        @Override
        public String getName() {
            return "slime";
        }

        @Override
        public float getToughness() {
            return 0;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    }
}
