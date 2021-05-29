/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands.ManagementCommands;

import Controllers.MenuAdapters.DynamicMenuOperatorHub;
import Presenters.PresenterHelperCommands.Command;

import java.util.Optional;

/**
 * A command that allows the user to create a new room.
 */
public class GetNewRoomCmd extends Command {
    /**
     * Create a new room
     */
    public GetNewRoomCmd() {
        super("Create a New Room");
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
        return dynamicMenuOperatorHub.newRoom();
    }
}

