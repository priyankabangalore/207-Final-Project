package Presenters.PresenterHelperCommands.AdminUserCommands;

import Controllers.MenuAdapters.BlockAndUnblockUserOperator;
import Controllers.MenuAdapters.DynamicMenuOperatorHub;
import Presenters.PresenterHelperCommands.Command;

import java.util.Optional;

public class BlockUserCmd extends Command{
    /**
     * Sends String to Command() to print
     */
    public BlockUserCmd(){super("Block a User");}
    /**
     * Constructor for BlockUserCmd
     * @param userID ID of User
     * @param title title of command
     */
    public BlockUserCmd(String title, int userID){super(title, userID);}
    /**
     * Executes a command within this class.
     * @param blockAndUnblockUserOperator the command to be executed.
     * @return null optional
     */
    public Optional<Command> execute(BlockAndUnblockUserOperator blockAndUnblockUserOperator){
        blockAndUnblockUserOperator.BlockUser(getTitle(), entityID);
        return Optional.empty();
    }
    /**
     * Executes a command within this class.
     * @param target the command to be executed.
     * @return null optional
     */
    public Optional<Command> execute(DynamicMenuOperatorHub target){
        target.runBlockUser();
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


