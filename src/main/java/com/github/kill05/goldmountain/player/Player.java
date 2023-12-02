package com.github.kill05.goldmountain.player;

import com.github.kill05.goldmountain.protocol.PlayerConnection;
import com.github.kill05.goldmountain.protocol.enums.IdentifiableEnumHelper;
import com.github.kill05.goldmountain.protocol.packets.in.PacketInPlayerUpdate;
import org.joml.Vector2f;

public class Player {

    private final PlayerConnection connection;
    private final Vector2f prevLocation;
    private final Vector2f location;
    private PlayerCostume costume;
    private PlayerAction action;

    public Player(PlayerConnection connection) {
        this.connection = new PlayerConnection();
        this.prevLocation = new Vector2f();
        this.location = new Vector2f();
    }

    public void update(PacketInPlayerUpdate packet) {
        location.set(packet.getFuturePos());
        prevLocation.set(packet.getCurrentPos());
        costume = packet.getCostume();
        action = IdentifiableEnumHelper.fromId(PlayerAction.class, packet.getCurrentAction());
    }

    public PlayerConnection getConnection() {
        return connection;
    }

    public Vector2f getPrevLocation() {
        return prevLocation;
    }

    public Vector2f getLocation() {
        return location;
    }

    public PlayerCostume getCostume() {
        return costume;
    }

    public PlayerAction getAction() {
        return action;
    }
}
