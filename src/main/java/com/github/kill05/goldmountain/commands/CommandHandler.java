package com.github.kill05.goldmountain.commands;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.commands.registered.LogLastUpdate;
import com.github.kill05.goldmountain.commands.registered.TeleportCommand;
import com.github.kill05.goldmountain.commands.registered.TpsCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class CommandHandler implements Runnable {

    private final Thread readerThread;
    private final List<String> inputBuffer;
    private final Map<String, Command> commandMap;

    public CommandHandler(GMServer server) {
        this.readerThread = new Thread(null, this, "command-reader");
        this.inputBuffer = Collections.synchronizedList(new ArrayList<>());
        this.commandMap = new HashMap<>();

        readerThread.setDaemon(true);
        readerThread.start();

        registerCommand(new TpsCommand(server));
        registerCommand(new LogLastUpdate(server));
        registerCommand(new TeleportCommand(server.getConnection()));
    }


    public void registerCommand(Command command) {
        for(String name : command.getNames()) {
            commandMap.put(name, command);
        }
    }


    public void processInput() {
        for(String input : inputBuffer) {
            if (input == null || input.isBlank()) continue;

            if (input.startsWith("/")) input = input.substring(1);
            String[] strings = input.split("\\s+");
            if (strings.length == 0) continue;

            String name = strings[0];
            String[] args = Arrays.copyOfRange(strings, 1, strings.length);

            Command command = commandMap.get(name);
            if(command == null) {
                GMServer.logger.info(String.format("Unknown command: '%s'.", name));
                continue;
            }

            try {
                command.execute(GMServer.logger::info, args);
            } catch (Throwable e) {
                GMServer.logger.warn("An error occurred while executing a command.", e);
            }
        }

        inputBuffer.clear();
    }

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                String line = reader.readLine();
                inputBuffer.add(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Thread getReaderThread() {
        return readerThread;
    }

    public List<String> getInputBuffer() {
        return inputBuffer;
    }
}
