/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands.AttendeeCommands;

import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.SimplePresenterMethods;

import java.util.Optional;

/**
 * A command issued by the user that enables them to change their username
 * @author Priyanka
 */
public class ChangeUsernameCmd extends Command {
    /**
     * Change your username
     */
    public ChangeUsernameCmd() {
        super("Change My Username");
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
     * Executes a command within this class.
     * @param simplePresenterMethods the command to be executed.
     * @return null optional
     */
    @Override
    public Optional<Command> execute(SimplePresenterMethods simplePresenterMethods) {
        simplePresenterMethods.changeUsername();
        return Optional.empty();
    }
}
