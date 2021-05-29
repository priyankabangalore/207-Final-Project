/*
@layer: Controller
 */

package Controllers.MenuAdapters;

import Controllers.Main;
import Presenters.PresenterHelperCommands.Command;
import Presenters.Menu;

import java.util.Optional;

/**
 * An operator responsible for operating a set of menus that allow the user to enrol in events.
 */
public class EventEnroller extends DynamicMenuOperator {
    private final int loggedInUserID;
    private final Menu mainMenu;

    /**
     * Constructor for EventEnroller
     * @param mainMenu: type Menu
     * @param main: type Main
     */
    public EventEnroller(Main main, Menu mainMenu) {
        super(main);
        loggedInUserID = main.getLoginController().getCurrentUserID();
        this.mainMenu = mainMenu;
    }

    /**
     * Allows the user to either enrol or dis-enrol in events.
     */
    public void showEventOptions(){
        run(mainMenu, "Back");
    }

    /**
     * Enrols the user in an event.
     * @param eventID the id of the event
     */
    public void enrolUser(int eventID){
        main.getAttendeeController().enrolInEvent(eventID, loggedInUserID);
    }

    /**
     * Dis-enrols a user from an event.
     * @param eventID the id of the event
     */
    public void disEnrolUser(int eventID){
        main.getAttendeeController().disEnrolInEvent(eventID, loggedInUserID);
    }

    /**
     * Executes a command within this class.
     * @param command the command to be executed.
     * @return a command to be executed in another part of the program
     */
    @Override
    public Optional<Command> executeCmd(Command command){return command.execute(this);}
}
