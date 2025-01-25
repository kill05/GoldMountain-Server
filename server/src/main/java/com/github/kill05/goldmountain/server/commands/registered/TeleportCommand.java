package com.github.kill05.goldmountain.server.commands.registered;

import com.github.kill05.goldmountain.server.GMServer;
import com.github.kill05.goldmountain.server.commands.Command;
import com.github.kill05.goldmountain.server.commands.senders.CommandSender;
import com.github.kill05.goldmountain.server.dimension.DimensionType;
import com.github.kill05.goldmountain.server.entity.player.ServerPlayer;
import com.github.kill05.goldmountain.proxy.Identifiable;
import com.github.kill05.goldmountain.connection.packets.out.UpdateDimensionPacket;
import org.apache.commons.lang3.EnumUtils;

public class TeleportCommand extends Command {

    private final GMServer server;

    public TeleportCommand(GMServer server) {
        super("teleport", "dimension", "tp", "dim");
        this.server = server;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /teleport [dim NAME or ID] [allow invalid (true/false)]");
            return;
        }

        boolean allowInvalid = args.length > 1 && Boolean.parseBoolean(args[1]);

        DimensionType type = EnumUtils.getEnumIgnoreCase(DimensionType.class, args[0]);
        if (type == null) {
            try {
                int id = Integer.decode(args[0].startsWith("0x") ? args[0] : "0x" + args[0]);
                type = Identifiable.fromIdOrUnknown(DimensionType.class, id);

                // Send dimension packet without updating dimension
                if (type == DimensionType.UNKNOWN && allowInvalid) {
                    for (ServerPlayer player : server.getConnectionController().getPlayers()) {
                        player.getConnection().sendPacket(new UpdateDimensionPacket(id));
                    }

                    return;
                }
            } catch (NumberFormatException ignored) {
            }
        }


        if (type == null || (!type.isValid() && !allowInvalid)) {
            sender.sendMessage("Invalid dimension.");
            return;
        }

        for (ServerPlayer player : server.getConnectionController().getPlayers()) {
            player.setDimension(type);
        }
        //connection.sendPacket(new TestPacket("05 a007 c000 5f00 fb00 0000 0580 0080 0061 0000 00000006 8006 a003 0400 0680 03a0 0604 00"));
        //connection.sendPacket(new TestPacket("05 a007 c000 5f00 fb00 0000 0580 0080 0061 0000 00000006 8006 a003 0400 0680 03a0 0604 00"));

        sender.sendMessage(String.format("Teleported to '%s'.", type));
    }

}
