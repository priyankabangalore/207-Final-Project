package Presenters.PresenterHelperCommands.RequestStrategiesAndCommands;

import Controllers.Main;
import Controllers.MenuAdapters.DynamicMenuOperatorHub;
import Controllers.MenuAdapters.RequestMenuOperator;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.CommandStrat;

import java.util.HashMap;
import java.util.Optional;

public class CreateRequestStrat extends CommandStrat {

    /**
     * Creates a request
     */
    public CreateRequestStrat(Main main) {
        super(main);
        this.setTitle("Make a Request");
    }

    public CreateRequestStrat(String title, int entityID) {
        super(title, entityID);
    }

    /**
     * Gets command
     * @param title
     * @param ID
     * @return CreateRequestStrat command
     */
    @Override
    public Command getCommand(String title, int ID) {
        return new CreateRequestStrat(title, ID);
    }

    /**
     * Gets hashmap of all possible user requests
     * @param entityID
     * @return hashmap
     */
    @Override
    public HashMap<String, Integer> getMap(int entityID) {
        return main.getRequestController().getUserPossibleRequests(main.getLoginController());
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
        target.createRequest(getTitle(), main.getLoginController());
        return Optional.empty();
    }

    /**
     * Executes a command within this class.
     * @param dynamicMenuOperatorHub the command to be executed.
     * @return null optional
     */
    @Override
    public Optional<Command> execute(DynamicMenuOperatorHub dynamicMenuOperatorHub){
        dynamicMenuOperatorHub.runCreateRequest();
        return Optional.empty();
    }
}