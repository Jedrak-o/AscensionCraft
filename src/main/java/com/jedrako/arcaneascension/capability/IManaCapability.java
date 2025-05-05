package com.jedrako.arcaneascension.capability;

import net.minecraft.nbt.CompoundTag;

public interface IManaCapability {
    int getMana();
    void setMana(int mana);

    void saveNBTData(CompoundTag nbt);
    void loadNBTData(CompoundTag nbt);
}