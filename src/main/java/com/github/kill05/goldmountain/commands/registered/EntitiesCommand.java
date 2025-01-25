package com.github.kill05.goldmountain.commands.registered;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.commands.Command;
import com.github.kill05.goldmountain.commands.senders.CommandSender;
import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.dimension.ServerDimension;

public class EntitiesCommand extends Command {

    private final GMServer server;

    public EntitiesCommand(GMServer server) {
        super("entities");
        this.server = server;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        for(DimensionType type : DimensionType.values()) {
            for(int i = 0; i < type.getMaxFloor(); i++) {
                ServerDimension dimension = server.getDimensionController().getDimension(type, i);
                if(dimension == null) continue;
                //entities.addAll(dimension.getEntityCount());
            }
        }

    }
}
