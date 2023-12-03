package com.github.kill05.goldmountain.commands.registered;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.commands.Command;
import com.github.kill05.goldmountain.commands.senders.CommandSender;

public class TestCommand extends Command {

    private final GMServer server;

    public TestCommand(GMServer server) {
        super("test");
        this.server = server;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

    }

}
