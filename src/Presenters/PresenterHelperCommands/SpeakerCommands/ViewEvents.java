/*
@layer: Presenter - Command
*/

package Presenters.PresenterHelperCommands.SpeakerCommands;

import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.SimplePresenterMethods;

import java.util.Optional;

/**
 * A command that enables the currently logged in speaker to view the events to which they are enrolled.
 */
public class ViewEvents extends Command {

    /**
     * View all events
     */
    public ViewEvents() {
        super("View My Events");
    }

    /**
     * Gets location of the command
     * @return location of command as a String
     */
    @Override
    public String executionLocation() {
        return "Simple Presenter Methods";
    }

    /**
     * Execute command to see events
     * @param simplePresenterMethods the command to be executed
     * @return the command to be executed
     */
    public Optional<Command> execute(SimplePresenterMethods simplePresenterMethods){
        simplePresenterMethods.displaySpeakerEvents();
        return Optional.empty();
    }

}
