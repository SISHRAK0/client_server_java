package org.example.commands;

/**
 An interface that represents a command object that can be executed and checked for valid arguments.
 */
public interface CommandInterface {
    String getDescription();
    String getName();
    void execute(String input);
}
