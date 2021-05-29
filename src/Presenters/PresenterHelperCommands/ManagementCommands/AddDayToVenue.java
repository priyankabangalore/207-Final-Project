package Presenters.PresenterHelperCommands.ManagementCommands;

import Controllers.MenuAdapters.VenueMenuOperator;
import Presenters.PresenterHelperCommands.Command;

import java.util.Optional;

public class AddDayToVenue extends Command {

    /**
     * Adds a day to the venue
     */
    public AddDayToVenue() {
        super("Add Another Day to The Venue");
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
        venueMenuOperator.addNewDay();
        return Optional.empty();
    }
}
