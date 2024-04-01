package com.github.kill05.goldmountain.commands.registered;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.commands.Command;
import com.github.kill05.goldmountain.commands.senders.CommandSender;
import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.protocol.ConnectionController;
import com.github.kill05.goldmountain.protocol.packets.out.UpdateDimensionPacket;
import org.apache.commons.lang3.EnumUtils;

public class TeleportCommand extends Command {

    private final GMServer server;

    public TeleportCommand(GMServer server) {
        super("teleport", "dimension", "tp", "dim");
        this.server = server;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            sender.sendMessage("Usage: /teleport [dim NAME or ID]");
            return;
        }

        Integer id = null;
        DimensionType type = EnumUtils.getEnumIgnoreCase(DimensionType.class, args[0]);
        if(type != null && type.isValid()) {
            id = type.getId();
        } else {
            try {
                id = Integer.decode(args[0].startsWith("0x") ? args[0] : "0x" + args[0]);
            } catch (NumberFormatException ignored) {
            }

        }

        if(id == null) {
            sender.sendMessage("Invalid dimension.");
            return;
        }

        ConnectionController connection = server.getPlayerController();
        connection.broadcastPacket(new UpdateDimensionPacket(id.byteValue()));
        //connection.sendPacket(new TestPacket("05 a007 c000 5f00 fb00 0000 0580 0080 0061 0000 00000006 8006 a003 0400 0680 03a0 0604 00"));
        //connection.sendPacket(new TestPacket("05 a007 c000 5f00 fb00 0000 0580 0080 0061 0000 00000006 8006 a003 0400 0680 03a0 0604 00"));

        sender.sendMessage(String.format("Teleported to dimension '%s'.", id));
    }

}
