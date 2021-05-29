/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands.SpeakerCommands;

import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.SimplePresenterMethods;

import java.util.Optional;

/**
 * A command issued by the user that enables them to view the ratings they have made (attendee)
 * @author Priyanka
 */
public class ViewRatingsCmd extends Command{

    /**
     * Shows rules
     */
    public ViewRatingsCmd() {
        super("View My Reviews");
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
     * Executes a command within this class.
     * @param simplePresenterMethods the command to be executed.
     * @return null optional
     */
    @Override
    public Optional<Command> execute(SimplePresenterMethods simplePresenterMethods) {
        simplePresenterMethods.ViewRatingsSpeaker();
        return Optional.empty();
    }
}
