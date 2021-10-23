package com.github.crimsondawn45.fabricshieldlib.lib.object;

import com.github.crimsondawn45.fabricshieldlib.initializers.FabricShieldLib;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShieldDecorationRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class FabricShieldDecoratorRecipe extends ShieldDecorationRecipe {

    public FabricShieldDecoratorRecipe(Identifier identifier) {
        super(identifier);
    }


    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        ItemStack itemStack = ItemStack.EMPTY;
        ItemStack itemStack2 = ItemStack.EMPTY;

        for(int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack3 = craftingInventory.getStack(i);
            if (!itemStack3.isEmpty()) {
                if (itemStack3.getItem() instanceof BannerItem) {
                    if (!itemStack2.isEmpty()) {
                        return false;
                    }

                    itemStack2 = itemStack3;
                } else {

                    if (!(itemStack3.getItem() instanceof FabricShield)) {
                        return false;
                    }

                    if (!itemStack.isEmpty()) {
                        return false;
                    }

                    if (itemStack3.getSubNbt("BlockEntityTag") != null) {
                        return false;
                    }

                    itemStack = itemStack3;
                }
            }
        }

        return !itemStack.isEmpty() && !itemStack2.isEmpty();
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        ItemStack itemStack = ItemStack.EMPTY;
        ItemStack itemStack2 = ItemStack.EMPTY;

        for (int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack3 = craftingInventory.getStack(i);
            if (!itemStack3.isEmpty()) {
                if (itemStack3.getItem() instanceof BannerItem) {
                    itemStack = itemStack3;
                } else if (itemStack3.getItem() instanceof FabricShield) {
                    FabricShield theShieldItem = ((FabricShield) itemStack3.getItem());
                    if (theShieldItem.supportsBanner()){
                        itemStack2 = itemStack3.copy();
                    }
                }
            }
        }

        if (!itemStack2.isEmpty()) {
            NbtCompound nbtCompound = itemStack.getSubNbt("BlockEntityTag");
            NbtCompound nbtCompound2 = nbtCompound == null ? new NbtCompound() : nbtCompound.copy();
            nbtCompound2.putInt("Base", ((BannerItem) itemStack.getItem()).getColor().getId());
            itemStack2.setSubNbt("BlockEntityTag", nbtCompound2);
        }
        return itemStack2;
    }

    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FabricShieldLib.FABRIC_SHIELD_DECORATION_SERIALIZER;
    }


}

