package com.github.kill05.goldmountain.server.commands;

import com.github.kill05.goldmountain.server.GMServer;
import com.github.kill05.goldmountain.server.commands.registered.*;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class CommandHandler {

    private final Thread readerThread;
    private final List<String> inputBuffer;
    private final Map<String, Command> commandMap;

    public CommandHandler(GMServer server) {
        this.readerThread = new ReaderThread();
        this.inputBuffer = Collections.synchronizedList(new ArrayList<>());
        this.commandMap = new HashMap<>();

        registerCommand(new TpsCommand(server));
        registerCommand(new PlayerInfoCommand(server));
        registerCommand(new TeleportCommand(server));
        registerCommand(new ActionCommand(server));
    }


    public void registerCommand(@NotNull Command command) {
        for (String name : command.getNames()) {
            if (commandMap.containsKey(name)) {
                throw new IllegalArgumentException(String.format("Duplicate command name: %s. (Command: %s)", name, command.getClass()));
            }
            commandMap.put(name, command);
        }
    }

    public void processInput() {
        synchronized (inputBuffer) {
            for (String input : inputBuffer) {
                if (input == null || input.isBlank()) continue;
                input = input.trim();

                if (input.startsWith("/")) input = input.substring(1);
                String[] strings = input.split("\\s+");
                if (strings.length == 0) continue;

                String name = strings[0];
                String[] args = Arrays.copyOfRange(strings, 1, strings.length);

                Command command = commandMap.get(name);
                if (command == null) {
                    GMServer.LOGGER.info("Unknown command: '{}'.", name);
                    continue;
                }

                try {
                    command.execute(GMServer.LOGGER::info, args);
                } catch (Throwable e) {
                    GMServer.LOGGER.warn("An error occurred while executing a command.", e);
                }
            }

            inputBuffer.clear();
        }
    }


    public void executeCommand(String command) {
        synchronized (inputBuffer) {
            inputBuffer.add(command);
        }
    }


    private class ReaderThread extends Thread {
        public ReaderThread() {
            super("command-reader");
            setDaemon(true);
            start();
        }

        @Override
        public void run() {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                try {
                    executeCommand(reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
