package Presenters.Menus.DynamicMenus;

import Controllers.Main;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.CommandStrat;

public class StrategyDynamicMenu extends DynamicMenu {

    protected final CommandStrat cs;
    protected final int entityID;
    private final String executionLocation;

    public StrategyDynamicMenu(Main main, CommandStrat commandStrat, int entityID) {
        super("", main);
        cs = commandStrat;
        this.entityID = entityID;
        executionLocation = null;
    }

    public StrategyDynamicMenu(Main main, CommandStrat commandStrat, int entityID, String executionLocation) {
        super("", main);
        cs = commandStrat;
        this.entityID = entityID;
        this.executionLocation = executionLocation;
    }

    /**
     * Builds the comm
     * @param title of command
     * @param ID of entity
     * @return command
     */
    @Override
    public Command buildCommand(String title, int ID) {
        return cs.getCommand(title, ID);
    }

    /**
     * Updates this menu to a certain set of options determined the command strategy
     */
    @Override
    public void update() {
        buildOptionsFromHashMap(cs.getMap(entityID));
    }

    /**
     * Where this command is to be executed
     * @return the execution location
     */
    @Override
    public String executionLocation(){
        return executionLocation;
    }
}
