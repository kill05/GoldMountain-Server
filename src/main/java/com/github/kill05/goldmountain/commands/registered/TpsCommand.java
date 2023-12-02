package com.github.kill05.goldmountain.commands.registered;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.commands.Command;
import com.github.kill05.goldmountain.commands.senders.CommandSender;

public class TpsCommand extends Command {

    private final GMServer server;

    public TpsCommand(GMServer server) {
        super("tps");
        this.server = server;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        GMServer.logger.info(String.format("TPS: %s", server.getTps()));
    }
}
