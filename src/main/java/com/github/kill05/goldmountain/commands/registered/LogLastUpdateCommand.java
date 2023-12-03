package com.github.kill05.goldmountain.commands.registered;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.commands.Command;
import com.github.kill05.goldmountain.commands.senders.CommandSender;
import com.github.kill05.goldmountain.player.PlayerAction;
import com.github.kill05.goldmountain.protocol.enums.IdentifiableEnumHelper;
import com.github.kill05.goldmountain.protocol.packets.io.PacketInOutPlayerUpdate;
import org.joml.Vector2f;

public class LogLastUpdateCommand extends Command {

    private final GMServer server;

    public LogLastUpdateCommand(GMServer server) {
        super("log");
        this.server = server;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        PacketInOutPlayerUpdate lastUpdate = server.getConnection().lastUpdate;
        System.out.println("=======================================================================================");
        logPacket("player id:    ", lastUpdate.getPlayerId());
        logPacket("metadata:     ", lastUpdate.getMetadata());
        logPacket("prev position:", lastUpdate.getCurrentPos());
        logPacket("position 2:   ", lastUpdate.getFuturePos());
        logPacket("position 3:   ", lastUpdate.getLocation3());
        logPacket("position 4:   ", lastUpdate.getLocation4());
        logPacket("data:         ", lastUpdate.getYaw());
        logPacket("action info:  ", IdentifiableEnumHelper.fromId(PlayerAction.class, lastUpdate.getCurrentAction()));
        logPacket("costume:      ", lastUpdate.getCostume());
        logPacket("breaking info:", lastUpdate.getUnknown_1());
        System.out.println("=======================================================================================");
    }

    private void logPacket(String name, long value) {
        System.out.printf("%s 0x%016x%n", name, value);
    }

    private void logPacket(String name, int value) {
        System.out.printf("%s 0x%08x%n", name, value);
    }

    private void logPacket(String name, short value) {
        System.out.printf("%s 0x%04x%n", name, value);
    }

    private void logPacket(String name, byte value) {
        System.out.printf("%s 0x%02x%n", name, value);
    }

    private void logPacket(String name, Object value) {
        if(value instanceof Vector2f vec) {
            System.out.printf("%s x:%s, y:%s%n", name, vec.x, vec.y);
            return;
        }
        System.out.printf("%s %s%n", name, value);
    }
}
