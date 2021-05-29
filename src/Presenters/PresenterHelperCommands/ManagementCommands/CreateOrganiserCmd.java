/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands.ManagementCommands;

import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.SimplePresenterMethods;


import java.util.Optional;

/**
 * A command that allows the user to create a new organiser
 */
public class CreateOrganiserCmd extends Command {
    /**
     * Create a new organiser
     */
    public CreateOrganiserCmd() {
        super("Create a New Organizer");
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
     * Execute the EventManager to create organiser
     * @param simplePresenterMethods the command to be executed.
     * @return the Command to be Executed
     */
    @Override
    public Optional<Command> execute(SimplePresenterMethods simplePresenterMethods){
        simplePresenterMethods.runSignUpNoLogin(2);
        return Optional.empty();
    }
}
