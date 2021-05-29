/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands.ManagementCommands;

import Controllers.MenuAdapters.NewRoomOperator;
import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.EventManager;

import java.util.Optional;

/**
 * A command that allows a user to add a speaker to an event.
 */
public class AddRemovePropertiesCommand extends Command {

    /**
     * Add a new property to the event
     * @param title title of command
     */
    public AddRemovePropertiesCommand(String title) {
        super(title);
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
        builder.addRemoveProperty(getTitle());
        return Optional.empty();
    }


    /**
     * Execute the NewRoomOperator to add attendee
     * @param builder the command to be executed.
     * @return the Command to be Executed
     */
    @Override
        public Optional<Command> execute(NewRoomOperator builder){
        builder.addRemoveProperty(getTitle());
        return Optional.empty();
    }
}
