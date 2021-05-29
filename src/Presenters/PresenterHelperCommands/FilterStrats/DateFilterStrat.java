package Presenters.PresenterHelperCommands.FilterStrats;

import Controllers.Main;
import Controllers.MenuAdapters.FilterEventsOperator;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.CommandStrat;

import java.util.HashMap;
import java.util.Optional;

public class DateFilterStrat extends CommandStrat {


    public DateFilterStrat(
    Main main) {
        super(main);
        this.setTitle("Filter by Date");
    }

    public DateFilterStrat(String title, int entityID) {
        super(title, entityID);
    }

    /**
     * Gets command
     * @param title
     * @param ID
     * @return DateFilterStrat command
     */
    @Override
    public Command getCommand(String title, int ID) {
        return new DateFilterStrat(title, ID);
    }

    /**
     * Gets map of all dates
     * @param entityID
     * @return map of all dates
     */
    @Override
    public HashMap<String, Integer> getMap(int entityID) {
        return main.getOrganizerController().getDatesDict();
    }

    /**
     * Gets location of the command
     * @return location of command as a String
     */
    @Override
    public String executionLocation() {
        return "Filter Events Operator";
    }

    /**
     * Executes a command within this class.
     * @param target the command to be executed.
     * @return null optional
     */
    @Override
    public Optional<Command> execute(FilterEventsOperator target) {
        // Execute this as if it were in the filter events main menu
        if(main != null){
            target.runDateFilterMenu();
        }
        // Execute this as if it is an add/remove command
        else{
            target.addRemoveDateRequest(entityID);
        }
        return Optional.empty();
    }
}
