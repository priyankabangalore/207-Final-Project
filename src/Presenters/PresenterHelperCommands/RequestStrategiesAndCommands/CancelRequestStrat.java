package Presenters.PresenterHelperCommands.RequestStrategiesAndCommands;

import Controllers.Main;
import Controllers.MenuAdapters.DynamicMenuOperatorHub;
import Controllers.MenuAdapters.RequestMenuOperator;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.CommandStrat;

import java.util.HashMap;
import java.util.Optional;

public class CancelRequestStrat extends CommandStrat {

    /**
     * Cancels a request
     */
    public CancelRequestStrat(Main main) {
        super(main);
        this.setTitle("View my Current Requests / Cancel a Request");
    }

    public CancelRequestStrat(String title, int entityID) {
        super(title, entityID);
    }

    /**
     * Gets command
     * @param title
     * @param ID
     * @return CancelRequestStrat command
     */
    @Override
    public Command getCommand(String title, int ID) {
        return new CancelRequestStrat(title, ID);
    }

    /**
     * Gets hashmap of all user request info
     * @param entityID
     * @return hashmap
     */
    @Override
    public HashMap<String, Integer> getMap(int entityID) {
        return main.getRequestController().getUserRequestsInformation(main.getLoginController());
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
     * @param target the command to be executed.
     * @return null optional
     */
    @Override
    public Optional<Command> execute(RequestMenuOperator target) {
        target.cancelRequest(entityID);
        return Optional.empty();
    }

    /**
     * Executes a command within this class.
     * @param dynamicMenuOperatorHub the command to be executed.
     * @return null optional
     */
    @Override
    public Optional<Command> execute(DynamicMenuOperatorHub dynamicMenuOperatorHub){
        dynamicMenuOperatorHub.runCancelRequest();
        return Optional.empty();
    }
}
