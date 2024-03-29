package com.github.kill05.goldmountain.protocol.packets.out.actions;

import com.github.kill05.goldmountain.dimension.tile.OreType;

public class PacketOutPlaceTile extends PacketOutExecuteClientAction {

    private OreType ore;


    public PacketOutPlaceTile(OreType ore) {
        this.ore = ore;
    }

    public PacketOutPlaceTile() {
    }


    @Override
    public short getActionCode() {
        return (short) ore.getId();
    }

}
