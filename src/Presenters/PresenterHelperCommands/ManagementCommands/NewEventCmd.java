/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands.ManagementCommands;

import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.EventManager;

import java.util.Optional;

/**
 * A command that allows the user to create a new event.
 */
public class NewEventCmd extends Command {

    /**
     * Create a new event
     */
    public NewEventCmd() {
        super("Create a New Event");
    }

    /**
     * Gets location of the command
     * @return location of command as a String
     */
    @Override
    public String executionLocation() {
        return "Event Manager";
    }

    /**
     * Executes a command within this class.
     * @param eventManager the command to be executed.
     * @return null optional
     */
    @Override
    public Optional<Command> execute(EventManager eventManager){
        return eventManager.getBuildEventCommand();

}}
