package Presenters.PresenterHelperCommands;

import Controllers.Main;

import java.util.HashMap;

public abstract class CommandStrat extends Command {

    protected Main main = null;

    /**
     * Strat command
     */
    public CommandStrat(Main main){
        super("This is a Strat");
        this.main = main;
    }

    public CommandStrat(String title, int entityID) {
        super(title, entityID);
    }

    /**
     * Gets command
     * @param title
     * @param ID
     */
    public abstract Command getCommand(String title, int ID);

    /**
     * Gets a map used by the update method in a dynamic menu
     * @param entityID
     * @return hashmap
     */
    public abstract HashMap<String, Integer> getMap(int entityID);

    /**
     * Gets execution location of command
     * @return String of execution location
     */
    @Override
    public abstract String executionLocation();
}
