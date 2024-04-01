package com.github.kill05.goldmountain.dimension.entity.player;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.protocol.PlayerConnection;
import com.github.kill05.goldmountain.protocol.packets.io.HumanUpdatePacket;

public class FakePlayer extends PlayerEntity {

    public FakePlayer(GMServer server, int id) {
        super(server, id);
    }


    @Override
    public PlayerConnection getConnection() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(HumanUpdatePacket packet) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getTotalLevel() {
        return 0;
    }
}
