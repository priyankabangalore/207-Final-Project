package Presenters.PresenterHelperCommands.AdminUserCommands;

import Controllers.MenuAdapters.BlockAndUnblockUserOperator;
import Controllers.MenuAdapters.DynamicMenuOperatorHub;
import Presenters.PresenterHelperCommands.Command;

import java.util.Optional;

public class UnblockUserCmd extends Command {
    /**
     * Sends String to Command() to print
     */
    public UnblockUserCmd(){super("Unblock a User");}
    /**
     * Constructor for UnblockUserCmd
     * @param userID ID of User
     * @param title title of command
     */
    public UnblockUserCmd(String title, int userID){super(title, userID);}
    /**
     * Executes a command within this class.
     * @param blockAndUnblockUserOperator the command to be executed.
     * @return null optional
     */
    public Optional<Command> execute(BlockAndUnblockUserOperator blockAndUnblockUserOperator){
        blockAndUnblockUserOperator.unblockUser(getTitle(), entityID);
        return Optional.empty();
    }
    /**
     * Executes a command within this class.
     * @param target the command to be executed.
     * @return null optional
     */
    public Optional<Command> execute(DynamicMenuOperatorHub target){
        target.runUnblockUser();
        return Optional.empty();
    }
    /**
     * Gets location of the command
     * @return location of command as a String
     */
    public String executionLocation() {
        return "Dynamic Menu Operator Hub";
    }
}
