/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands.AttendeeCommands;

import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.AddNewFriendOperator;

import java.util.Optional;

/**
 * A command issued by the user that enables them to open a prompt to add a new friend via ID.
 */
public class ManuallyAddFriendCmd extends Command {
    /**
     * Add a friend
     */
    public ManuallyAddFriendCmd() {
        super("Manually Add a Friend Via ID");
    }

    /**
     * Gets location of the command
     * @return location of command as a String
     */
    @Override
    public String executionLocation() {
        return "AddNewFriendsOperator";
    }

    /**
     * Executes a command within this class.
     * @param addNewFriendOperator the command to be executed.
     * @return null optional
     */
    @Override
    public Optional<Command> execute(AddNewFriendOperator addNewFriendOperator) {
        addNewFriendOperator.addNewFriendFromID();
        return Optional.empty();
    }
}
