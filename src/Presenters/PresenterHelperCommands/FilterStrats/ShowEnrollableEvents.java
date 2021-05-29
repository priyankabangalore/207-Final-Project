package Presenters.PresenterHelperCommands.FilterStrats;

import Controllers.Main;
import Controllers.MenuAdapters.FilterEventsOperator;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.CommandStrat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class ShowEnrollableEvents extends CommandStrat {

    private ArrayList<Integer> speakerRequests = new ArrayList<>();
    private ArrayList<Integer> timeRequests = new ArrayList<>();
    private ArrayList<Integer> dateRequests = new ArrayList<>();

    public ShowEnrollableEvents(Main main) {
        super(main);

    }

    public ShowEnrollableEvents(Main main, ArrayList<Integer> speakerRequests, ArrayList<Integer> timeRequests,
                                ArrayList<Integer> dateRequests) {
        super(main);
        this.setTitle("Show Events That Match Your Criteria");
        this.speakerRequests = speakerRequests;
        this.timeRequests = timeRequests;
        this.dateRequests = dateRequests;

    }

    public ShowEnrollableEvents(String title, int entityID) {
        super(title, entityID);
    }

    /**
     * Gets command
     * @param title
     * @param ID
     * @return command
     */
    @Override
    public Command getCommand(String title, int ID) {
        return new ShowEnrollableEvents(title, ID);
    }

    /**
     * Gets map of all dates
     * @param entityID
     * @return map of all dates
     */
    @Override
    public HashMap<String, Integer> getMap(int entityID) {
        return main.getOrganizerController().eventFilters(timeRequests, speakerRequests, dateRequests, main.
                getLoginController().getCurrentUserID());
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
            target.runSelectEventMenu();
        }
        // Execute this as if it is an add/remove command
        else{
            target.enrolUser(entityID);
        }
        return Optional.empty();
    }
}