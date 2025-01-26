package com.github.kill05.goldmountain.server.commands.registered;

import com.github.kill05.goldmountain.server.GMServer;
import com.github.kill05.goldmountain.server.commands.Command;
import com.github.kill05.goldmountain.server.commands.senders.CommandSender;
import com.github.kill05.goldmountain.server.entity.player.ServerPlayer;
import com.github.kill05.goldmountain.dimension.TileType;
import com.github.kill05.goldmountain.connection.packet.packets.ExecuteActionPacket;
import org.joml.Vector2f;

public class ActionCommand extends Command {

    private final GMServer server;

    public ActionCommand(GMServer server) {
        super("action");
        this.server = server;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        /*
        float x = Float.parseFloat(args[0]);
        float y = Float.parseFloat(args[1]);

        int code = Integer.parseInt(args[2]);
        int id = Integer.parseInt(args[3]);

        for (ServerPlayer player : server.getConnectionController().getPlayers()) {
            player.sendPacket(new ExecuteActionPacket(new Vector2f(x, y), code, id));
        }

         */

        int i = 0;
        for (TileType value : TileType.values()) {
            if (!value.isValid()) continue;

            for (ServerPlayer player : server.getConnectionController().getPlayers()) {
                player.sendPacket(new ExecuteActionPacket(new Vector2f(i, 1), value, i));
            }
            i++;
        }
    }
}
