package Presenters.PresenterHelperCommands.AttendeeCommands;

import Controllers.MenuAdapters.DynamicMenuOperatorHub;
import Presenters.PresenterHelperCommands.Command;

import java.util.Optional;

public class ViewAllReviewsCmd extends Command {

    /**
     * View all reviews
     */
    public ViewAllReviewsCmd() {
        super("View all Speaker Reviews");
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
    public Optional<Command> execute(DynamicMenuOperatorHub target) {
        target.showAllReviews();
        return Optional.empty();
    }
}
