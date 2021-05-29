/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands.ManagementCommands;

import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.EventManager;

import java.util.Optional;

/**
 * A command that allows a user to add an attendee to an event.
 */
public class AddAttendeeCmd extends Command {

    /**
     * Add a new attendee UI
     * @param title title of command
     * @param attendeeID ID of the attendee
     */
    public AddAttendeeCmd(String title, int attendeeID) {
        super(title, attendeeID);
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
     * Execute the EventManager to add attendee
     * @param builder the EventManager usecase
     * @return the Command to be Executed
     */
    public Optional<Command> execute(EventManager builder){
        builder.addAttendee(entityID);
        return Optional.empty();
    }
}
