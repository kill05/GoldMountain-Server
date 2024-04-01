package com.github.kill05.goldmountain.commands.registered;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.commands.Command;
import com.github.kill05.goldmountain.commands.senders.CommandSender;
import com.github.kill05.goldmountain.dimension.entity.player.ServerPlayer;
import com.github.kill05.goldmountain.utils.Utils;

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

            ServerPlayer player = server.getPlayerController().getPlayer(i);
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
