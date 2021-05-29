package Presenters.PresenterHelperCommands.ManagementCommands;

import Controllers.MenuAdapters.VenueMenuOperator;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.ExitCmd;

import java.util.Optional;

public class SelectStartDayCmd extends Command {

    public SelectStartDayCmd() {
        super("Select a Start Day for the Event");
    }

    /**
     * Gets location of the command
     * @return location of command as a String
     */
    @Override
    public String executionLocation() {
        return null;
    }

    /**
     * Executes a command within this class.
     * @param venueMenuOperator the command to be executed.
     * @return null optional
     */
    @Override
    public Optional<Command> execute(VenueMenuOperator venueMenuOperator){
        venueMenuOperator.selectStartDay();
        return Optional.of(new ExitCmd());
    }
}
