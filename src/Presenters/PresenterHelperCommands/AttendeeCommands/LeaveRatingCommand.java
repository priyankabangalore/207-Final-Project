package Presenters.PresenterHelperCommands.AttendeeCommands;

import Controllers.Main;
import Controllers.MenuAdapters.DynamicMenuOperatorHub;
import Controllers.MenuAdapters.ReviewMenuOperator;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.CommandStrat;

import java.util.HashMap;
import java.util.Optional;

public class LeaveRatingCommand extends CommandStrat {

    /**
     * Constructor for leave rating command
     */
    public LeaveRatingCommand() {
        super(null);

        setTitle("Leave a Speaker Review");
    }

    /**
     * Constructor for leave rating command
     * @param main the main to be made
     */
    public LeaveRatingCommand(Main main) {
        super(main);
    }

    /**
     *
     * @param title
     * @param entityID
     */
    public LeaveRatingCommand(String title, int entityID) {
        super(title, entityID);
    }

    @Override
    public Command getCommand(String title, int ID) {
        return new LeaveRatingCommand(title, ID);
    }

    @Override
    public HashMap<String, Integer> getMap(int entityID) {
        return main.getOrganizerController().getSpeakers();
    }

    @Override
    public String executionLocation() {
        return "Dynamic Menu Operator Hub";
    }

    public Optional<Command> execute(DynamicMenuOperatorHub target){
        target.runLeaveRatingMenu();
        return Optional.empty();
    }

    @Override
    public Optional<Command> execute(ReviewMenuOperator target){
        target.leaveReview(entityID);
        return Optional.empty();
    }
}
