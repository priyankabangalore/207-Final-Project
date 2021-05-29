package Controllers.MenuAdapters;

import Controllers.Main;
import Presenters.Menus.DynamicMenus.DynamicMenu;
import Presenters.Menus.DynamicMenus.StrategyDynamicMenu;
import Presenters.PresenterHelperCommands.AttendeeCommands.LeaveRatingCommand;
import Presenters.PresenterHelperCommands.Command;

import java.util.HashMap;
import java.util.Optional;

public class ReviewMenuOperator extends DynamicMenuOperator {

    /**
     * Constructor for ReviewMenuOperator
     *
     * @param main : type Main
     */
    public ReviewMenuOperator(Main main) {
        super(main);
    }

    /**
     * Runs menu
     */
    public void runLeaveRatingMenu(){
        DynamicMenu ratingMenu = new StrategyDynamicMenu(main, new LeaveRatingCommand(main), 1);
        runUntilBackOrExit(ratingMenu);
    }

    /**
     * Allows user to leave a review
     * @param speakerID : ID of speaker to review
     */
    public void leaveReview(int speakerID){
        StringBuilder input = new StringBuilder();
        while (true) {
            input.setLength(0);
            showPrompt("Enter a rating From 1-5, 5 being Excellent (◠‿◠) and 1 being Poor (╥﹏╥)");
            input.append(scanner.nextLine());
            if(input.toString().matches("^[1-5]$")){
                break;
            }
            showPrompt("Invalid input.");
        }
        main.getAttendeeController().addRating(speakerID, Integer.parseInt(input.toString()),
                main.getLoginController().getCurrentUserID());
        showPrompt("Rating received.");
    }

    /**
     * Gets all reviews
     */
    public void getReviews(){
        StringBuilder reviews = new StringBuilder();
        HashMap<String, Integer> speakers = main.getOrganizerController().getSpeakers();
        for (String speaker : speakers.keySet()){
            reviews.append(speaker).append(":");
            reviews.append(main.getSpeakerController().viewRatings(speakers.get(speaker)));
            reviews.append("\n");
        }
        if (reviews.length() < 3) {
            reviews.append("There are no speakers in the system with reviews.");
        }
        showPrompt(reviews.toString());
        reviews.append("Press Enter to continue.");
        scanner.nextLine();
    }

    /**
     * Checks if value is numeric
     * @param str : String to check
     * @return true iff parse-able
     */
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    /**
     * Executes a command within this class.
     * @param command the command to be executed.
     * @return a command to be executed in another part of the program
     */
    @Override
    public Optional<Command> executeCmd(Command command){return command.execute(this);}
}
