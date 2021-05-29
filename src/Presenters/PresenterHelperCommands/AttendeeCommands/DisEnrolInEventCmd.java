/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands.AttendeeCommands;

import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.EventEnroller;

import java.util.Optional;

/**
 * A command issued by a user to dis-enroll in a specific event.
 */
public class DisEnrolInEventCmd extends Command {

    /**
     * Remove user from event UI
     * @param title title of command
     * @param eventID ID of the event
     */
    public DisEnrolInEventCmd(String title, int eventID) {
        super(title, eventID);
    }

    /**
     * Gets location of the command
     * @return location of command as a String
     */
    @Override
    public String executionLocation() {
        return "Event Enroller";
    }

    /**
     * Executes a command within this class.
     * @param enrolInEvent the command to be executed.
     * @return null optional
     */
    @Override
    public Optional<Command> execute(EventEnroller enrolInEvent){
        enrolInEvent.disEnrolUser(entityID);
        return Optional.empty();
    }
}
