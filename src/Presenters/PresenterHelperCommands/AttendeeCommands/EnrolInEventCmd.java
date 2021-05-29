/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands.AttendeeCommands;

import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.EventEnroller;

import java.util.Optional;

/**
 * A command to enrol in a new kind new event, issued by user.
 */
public class EnrolInEventCmd extends Command {

    /**
     * Enrol user into an event
     * @param title title of command
     * @param eventID Event ID
     */
    public EnrolInEventCmd(String title, int eventID) {
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
        enrolInEvent.enrolUser(entityID);
        return Optional.empty();
    }
}
