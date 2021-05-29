/*
@layer: Presenter - Command
 */

package Presenters.PresenterHelperCommands.SpeakerCommands;

import Controllers.MenuAdapters.SimplePresenterMethods;
import Presenters.PresenterHelperCommands.Command;

import java.util.Optional;

/**
 * A command issued by the user that enables them to view the system rules (speaker)
 * @author Priyanka
 */
public class ShowRulesCmd extends Command{

    public ShowRulesCmd() {
        super("View Conference Rules");
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
        simplePresenterMethods.viewRulesSpeaker();
        return Optional.empty();
    }
}
