/*
@layer: Presenter - Command
 */

package Presenters.PresenterHelperCommands.AttendeeCommands;

import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.AddNewFriendOperator;
import Controllers.MenuAdapters.DynamicMenuOperatorHub;

import java.util.Optional;

/**
 * A command issued by the user to add a new friend.
 */
public class AddNewFriendCmd extends Command {

    /**
     * Sends String to Command() to print
     */
    public AddNewFriendCmd() {
        super("Add a New Friend");
    }

    /**
     * Constructor for AddNewFriendCmd
     * @param attendeeID ID of attendee
     * @param title title of command
     */
    public AddNewFriendCmd(String title, int attendeeID) {
        super(title, attendeeID);
    }

    /**
     * Gets location of the command
     * @return location of command as a String
     */
    @Override
    public String executionLocation() {
        return "Dynamic Menu Operator Hub";
    }

    /**
     * Executes a command within this class.
     * @param dynamicMenuOperatorHub the command to be executed.
     * @return null optional
     */
    @Override
    public Optional<Command> execute(DynamicMenuOperatorHub dynamicMenuOperatorHub){
        return dynamicMenuOperatorHub.addNewFriends();
    }

    /**
     * Executes a command within this class.
     * @param addNewFriendOperator the command to be executed.
     * @return null optional
     */
    @Override
    public Optional<Command> execute(AddNewFriendOperator addNewFriendOperator){
        addNewFriendOperator.addNewFriend(entityID);
        return Optional.empty();
    }
}
