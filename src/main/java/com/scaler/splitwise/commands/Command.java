package com.scaler.splitwise.commands;

public interface Command {

    boolean matches(String inp);
    void execute(String inp);
}
