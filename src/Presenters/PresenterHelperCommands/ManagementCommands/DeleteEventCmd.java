/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands.ManagementCommands;

import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.EventManager;

import java.util.Optional;

/**
 * A command that allows the user to delete a pre-existing command.
 */
public class DeleteEventCmd extends Command {
    /**
     * Delete an event
     * @param title title of command
     * @param eventID the eventID of the event being deleted
     */
    public DeleteEventCmd(String title, int eventID) {
        super(title, eventID);
    }

    /**
     * Gets location of the command
     * @return location of command as a String
     */
    @Override
    public String executionLocation() {
        return "EventManager";
    }

    /**
     * Executes a command within this class.
     * @param eventManager the command to be executed.
     * @return null optional
     */
    @Override
    public Optional<Command> execute(EventManager eventManager){
        eventManager.deleteEvent(entityID);
        return Optional.empty();
    }
}
