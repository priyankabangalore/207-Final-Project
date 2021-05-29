/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands.ManagementCommands;

import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.DynamicMenuOperatorHub;

import java.util.Optional;

/**
 * A command that opens the event management menu.
 */
public class EventManagementCmd extends Command {

    /**
     * Manage the events
     */
    public EventManagementCmd() {
        super("Manage Events");
    }

    /**
     * Gets location of the command
     * @return location of command as a String
     */
    @Override
    public String executionLocation() {
        return "Dynamic Menu Operator Hub";
    }

    /**
     * Executes a command within this class.
     * @param dynamicMenuOperatorHub the command to be executed.
     * @return null optional
     */
    @Override
    public Optional<Command> execute(DynamicMenuOperatorHub dynamicMenuOperatorHub){
        return dynamicMenuOperatorHub.openEventManagement();
    }
}
