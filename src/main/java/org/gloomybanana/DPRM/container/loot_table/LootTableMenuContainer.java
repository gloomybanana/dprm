package org.gloomybanana.DPRM.container.loot_table;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import org.gloomybanana.DPRM.handler.Registry;

public class LootTableMenuContainer extends Container {
    private final PacketBuffer packetBuffer;

    public LootTableMenuContainer(int id, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        super(Registry.lootTableMenuContainer.get(), id);
        this.packetBuffer = packetBuffer;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    public PacketBuffer getPacketBuffer() {
        return packetBuffer;
    }
}
