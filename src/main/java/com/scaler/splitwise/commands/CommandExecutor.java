package com.scaler.splitwise.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommandExecutor {
    private List<Command> commands = new ArrayList<>();

    @Autowired
    public CommandExecutor(SettleUpUser settleUpUser) {
        commands.add(settleUpUser);
    }

    public void register(Command command) {

    }

    public void removeCommand(Command command) {

    }

    public void execute(String inp) {
        for(Command command: commands) {
            if(command.matches(inp)) {
                command.execute(inp);
            }
        }
    }
}
