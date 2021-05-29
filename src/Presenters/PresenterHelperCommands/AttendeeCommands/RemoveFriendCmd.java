/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands.AttendeeCommands;

import Controllers.MenuAdapters.RemoveFriendOperator;
import Presenters.PresenterHelperCommands.Command;

import java.util.Optional;

/**
 * A command which allows a user to remove an existing friend in their network
 * @author Priyanka
 */
public class RemoveFriendCmd extends Command {

    /**
     * Constructor for RemoveFriendCmd
     * @param friendID ID of friend
     * @param title title of friend
     */
    public RemoveFriendCmd(String title, int friendID) {
        super(title, friendID);
    }

    /**
     * Executes a command within this class.
     * @param removeFriendOperator the command to be executed.
     * @return null optional
     */
    public Optional<Command> execute(RemoveFriendOperator removeFriendOperator){
        removeFriendOperator.removeFriend(getTitle(), entityID);
        return Optional.empty();
    }

    /**
     * Gets location of the command
     * @return location of command as a String
     */
    @Override
    public String executionLocation() {
        return null;
    }
}
