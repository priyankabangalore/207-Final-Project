/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands.ManagementCommands;

import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.SimplePresenterMethods;

import java.util.Optional;


/**
 * A command that allows the user to create a new attendee account
 * @author Priyanka
 */
public class CreateAttendeeCmd extends Command{

    /**
     * Create a new attendee
     */
    public CreateAttendeeCmd() {
        super("Create a New Attendee");
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
        simplePresenterMethods.runSignUpNoLogin(0);
        return Optional.empty();
    }
}
