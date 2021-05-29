package Controllers.MenuAdapters;

import Controllers.Main;
import Presenters.Menu;
import Presenters.Menus.DynamicMenus.StrategyDynamicMenu;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.FilterStrats.DateFilterStrat;
import Presenters.PresenterHelperCommands.FilterStrats.ShowEnrollableEvents;
import Presenters.PresenterHelperCommands.FilterStrats.SpeakerFilterStrat;
import Presenters.PresenterHelperCommands.FilterStrats.TimeFilterStrat;

import java.util.ArrayList;
import java.util.Optional;

public class FilterEventsOperator extends DynamicMenuOperator {

    private final ArrayList<Integer> speakerRequests = new ArrayList<>();
    private final ArrayList<Integer> timeRequests = new ArrayList<>();
    private final ArrayList<Integer> dateRequests = new ArrayList<>();
    private final int userID;

    /**
     * Constructor for DynamicMenuOperator
     *
     * @param main : type Main
     */
    public FilterEventsOperator(Main main) {
        super(main);
        userID = main.getLoginController().getCurrentUserID();
    }

    public void showFilterMenu(Menu filterMenu){
        run(filterMenu, "Back");
    }

    /**
     * Runs a menu that allows users to add a date filter
     */
    public void runDateFilterMenu() {
        StrategyDynamicMenu strategyDynamicMenu = new StrategyDynamicMenu(main, new DateFilterStrat(main), userID);
        runUntilBackOrExit(strategyDynamicMenu);
    }

    /**
     * Runs a menu that allows users to add a speaker filter
     */
    public void runSpeakerFilterMenu() {
        StrategyDynamicMenu strategyDynamicMenu = new StrategyDynamicMenu(main, new SpeakerFilterStrat(main), userID);
        runUntilBackOrExit(strategyDynamicMenu);
    }

    /**
     * Runs a menu that allows users to add a time filter
     */
    public void runTimeFilterMenu() {
        StrategyDynamicMenu strategyDynamicMenu = new StrategyDynamicMenu(main, new TimeFilterStrat(main), userID);
        runUntilBackOrExit(strategyDynamicMenu);
    }

    /**
     * Shows the user the events that match their criteria
     */
    public void runSelectEventMenu() {
        StrategyDynamicMenu strategyDynamicMenu = new StrategyDynamicMenu(main, new ShowEnrollableEvents(main,
                speakerRequests, timeRequests, dateRequests), userID);
        runUntilBackOrExit(strategyDynamicMenu);
    }

    /**
     * Adds removes a speaker to the list of speaker requested to be in the event iff the speaker is not in the list,
     * else removes the speaker.
     * @param speakerID the speaker to be added
     */
    public void addRemoveSpeakerRequest(int speakerID) {
        super.addRemove(speakerID, speakerRequests);
    }

    /**
     * Adds removes a time to the list of time requested to be in the event iff the time is not in the list,
     * else removes the time.
     * @param time the time to be added
     */
    public void addRemoveTimeRequest(int time) {
        super.addRemove(time, speakerRequests);
    }

    /**
     * Adds removes a date to the list of date requested to be in the event iff the date is not in the list,
     * else removes the date.
     * @param date the date to be added
     */
    public void addRemoveDateRequest(int date) {
        super.addRemove(date, dateRequests);
    }

    /**
     * Enrols the currently logged in user in a given event
     * @param eventID the event to be enrolled in
     */
    public void enrolUser(int eventID){
        main.getAttendeeController().enrolInEvent(eventID, main.getLoginController().getCurrentUserID());
    }

    /**
     * Executes a command within this class.
     * @param command the command to be executed.
     * @return a command to be executed in another part of the program
     */
    @Override
    public Optional<Command> executeCmd(Command command){return command.execute(this);}


}
