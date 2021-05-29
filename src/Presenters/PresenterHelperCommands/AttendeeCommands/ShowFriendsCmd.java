/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands.AttendeeCommands;

import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.SimplePresenterMethods;

import java.util.Optional;

/**
 * A command that calls for the UI to display the currently logged in users' friends.
 */
public class ShowFriendsCmd extends Command {

    /**
     * Shows the user their friends.
     */
    public ShowFriendsCmd() {
        super("View My Friends");
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
    public  Optional<Command> execute(SimplePresenterMethods simplePresenterMethods){
        simplePresenterMethods.viewFriends();
        return Optional.empty();
    }
}
