/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands.ManagementCommands;

import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.EventManager;

import java.util.Optional;

/**
 * A command that allows a user to add a speaker to an event.
 */
public class AddRemoveSpeakerCmd extends Command {

    /**
     * Add a new attendee UI
     * @param title title of command
     *
     * @param speakerID ID of the attendee
     */
    public AddRemoveSpeakerCmd(String title, int speakerID) {
        super(title, speakerID);
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
     * @param builder the command to be executed.
     * @return the Command to be Executed
     */
    public Optional<Command> execute(EventManager builder){
        builder.addRemoveSpeaker(entityID);
        return Optional.empty();
    }
}
