package com.github.kill05.goldmountain.protocol.packets.out.actions;

public class PacketOutExecuteClientActionGeneric extends PacketOutExecuteClientAction {

    private short actionCode;

    @Override
    public short getActionCode() {
        return actionCode;
    }

    public void setActionCode(short renderCode) {
        this.actionCode = renderCode;
    }

}
