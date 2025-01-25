package com.github.kill05.goldmountain.server.commands.registered;

import com.github.kill05.goldmountain.server.GMServer;
import com.github.kill05.goldmountain.server.commands.Command;
import com.github.kill05.goldmountain.server.commands.senders.CommandSender;
import com.github.kill05.goldmountain.server.entity.player.ServerPlayer;
import com.github.kill05.goldmountain.server.utils.Utils;

public class PlayerInfoCommand extends Command {

    private final GMServer server;

    public PlayerInfoCommand(GMServer server) {
        super("player", "playerinfo", "pl");
        this.server = server;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        try {
            int i = Integer.parseInt(args[0]);

            ServerPlayer player = server.getConnectionController().getPlayerFromId(i);
            if(player == null) {
                sender.sendMessage("That player is not connected.");
                return;
            }

            System.out.println("=======================================================================================");
            System.out.println("player tileId:     " + player.getId());
            System.out.println("total level:   " + player.getTotalLevel());
            System.out.println("position:      " + Utils.vecToString(player.getCheckpoints()[0]));
            System.out.println("next position: " + Utils.vecToString(player.getCheckpoints()[1]));
            System.out.println("speed:         " + player.getSpeed());
            //logPacket("action info:  ", IdentifiableEnumHelper.fromId(PlayerAction.class, player.getUnknown_0()));
            System.out.println("costume:       " + player.getCostume());
            System.out.println("breaking info: " + player.getTargetTile());
            System.out.println("=======================================================================================");
        } catch (NumberFormatException e) {
            sender.sendMessage("Please provide a valid player tileId.");
        }
    }
}
