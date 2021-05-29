/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands.ManagementCommands;

import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.SimplePresenterMethods;


import java.util.Optional;

/**
 * A command that allows the user to create a new speaker account
 */
public class CreateSpeakerCmd extends Command {
    /**
     * Create a new speaker
     */
    public CreateSpeakerCmd() {
        super("Create a New Speaker");
    }

    /**
     * Gets location of the command
     * @return location of command as a String
     */
    @Override
    public String executionLocation() {
        return "Simple Presenter Methods";
    }

    /**
     * Execute the EventManager to add attendee
     * @param simplePresenterMethods the command to be executed.
     * @return the Command to be Executed
     */
    @Override
    public Optional<Command> execute(SimplePresenterMethods simplePresenterMethods){
        simplePresenterMethods.runSignUpNoLogin(1);
        return Optional.empty();
    }
}
