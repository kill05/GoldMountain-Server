package com.github.kill05.goldmountain.server.commands.registered;

import com.github.kill05.goldmountain.server.GMServer;
import com.github.kill05.goldmountain.server.commands.Command;
import com.github.kill05.goldmountain.server.commands.senders.CommandSender;

public class TpsCommand extends Command {

    private final GMServer server;

    public TpsCommand(GMServer server) {
        super("tps");
        this.server = server;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        GMServer.LOGGER.info("TPS: {}", server.getTps());
    }
}
