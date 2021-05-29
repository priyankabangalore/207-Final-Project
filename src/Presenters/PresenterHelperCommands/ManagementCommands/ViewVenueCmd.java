package Presenters.PresenterHelperCommands.ManagementCommands;

import Controllers.MenuAdapters.DynamicMenuOperatorHub;
import Presenters.PresenterHelperCommands.Command;

import java.util.Optional;

public class ViewVenueCmd extends Command {

    /**
     * View the venue
     */
    public ViewVenueCmd() {
        super("Manage Dates");
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
    public Optional<Command> execute(DynamicMenuOperatorHub dynamicMenuOperatorHub){
        dynamicMenuOperatorHub.showVenueManagement();
        return Optional.empty();
    }
}
