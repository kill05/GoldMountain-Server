package com.github.kill05.goldmountain.commands.registered;

import com.github.kill05.goldmountain.commands.Command;
import com.github.kill05.goldmountain.commands.senders.CommandSender;
import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.protocol.ServerConnection;
import com.github.kill05.goldmountain.protocol.packets.out.PacketOutDimension;
import org.apache.commons.lang3.EnumUtils;

public class TeleportCommand extends Command {

    private final ServerConnection serverConnection;

    public TeleportCommand(ServerConnection serverConnection) {
        super("teleport", "dimension", "tp", "dim");
        this.serverConnection = serverConnection;
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
                id = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                try {
                    id = Integer.decode(args[0]);
                } catch (NumberFormatException ignored) {
                }
            }

        }

        if(id == null) {
            sender.sendMessage("Invalid dimension.");
            return;
        }

        serverConnection.sendPacket(new PacketOutDimension(id.byteValue()));
        sender.sendMessage(String.format("Teleported to dimension '%s'.", id));
    }

}
