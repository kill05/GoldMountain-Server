package com.github.kill05.goldmountain.commands;

import com.github.kill05.goldmountain.commands.senders.CommandSender;
import org.apache.commons.lang3.Validate;

public abstract class Command {

    private final String[] names;

    public Command(String... names) {
        Validate.notNull(names, "Names can't be null.");
        if(names.length == 0) throw new IllegalArgumentException("Commands must have at least a name.");
        this.names = names;
    }


    public abstract void execute(CommandSender sender, String[] args);


    public String[] getNames() {
        return names;
    }
}
