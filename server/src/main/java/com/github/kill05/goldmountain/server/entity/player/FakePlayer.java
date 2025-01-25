package com.github.kill05.goldmountain.server.entity.player;

import com.github.kill05.goldmountain.server.GMServer;
import com.github.kill05.goldmountain.server.connection.PlayerConnection;
import com.github.kill05.goldmountain.connection.packets.io.HumanUpdatePacket;
import org.apache.commons.lang3.NotImplementedException;

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
        throw new NotImplementedException();
    }

    @Override
    public int getTotalLevel() {
        return 0;
    }
}
