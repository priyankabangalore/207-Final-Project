/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands.AttendeeCommands;

import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.SimplePresenterMethods;

import java.util.Optional;

/**
 * A command issued by the user that enables them to change their email
 * @author Priyanka
 */
public class ChangeEmailCmd extends Command {
    /**
     * Change your email
     */
    public ChangeEmailCmd() {
        super("Change My Email");
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
        simplePresenterMethods.changeEmail();
        return Optional.empty();
    }
}
