/*
@layer: Presenter - Menu
 */

package Presenters.Menus.DynamicMenus.AttendeeMenus;

import Controllers.AttendeeController;
import Controllers.EventRegisterController;
import Controllers.Main;
import Presenters.PresenterHelperCommands.AttendeeCommands.DisEnrolInEventCmd;
import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.EventEnroller;
import Presenters.Menus.DynamicMenus.DynamicMenu;

import java.util.Optional;

/**
 * A menu displaying a users' currently enrolled events, and enabling them to dis-enrol from them.
 */
public class DisEnrolInEvent extends DynamicMenu {

    private final EventRegisterController eventRegisterController;
    private final AttendeeController attendeeController;
    private final Main main;

    /**
     * Constructor for DisEnrolInEvent
     * @param main type Main
     */
    public DisEnrolInEvent(Main main) {
        super("Dis-Enrol From an Event");
        this.eventRegisterController = main.getEventRegisterController();
        this.attendeeController = main.getAttendeeController();
        this.main = main;
    }

    /**
     * Builds command DisEnrolInEventCmd with necessary info
     * @param title title of menu
     * @param ID of evemt
     * @return DisEnrolInEventCmd command
     */
    @Override
    public Command buildCommand(String title, int ID) {
        return new DisEnrolInEventCmd(title, ID);
    }

    /**
     * Sets this menu's options to a list of dis-enroll commands for each event this user is attending.
     */
    @Override
    public void update() {
        this.buildOptionsFromHashMap(attendeeController.getEventsSignedUp(
                main.getLoginController().getCurrentUserID()));
    }

    /**
     * Gets execution location
     * @return execution location as a String
     */
    @Override
    public String executionLocation() {
        return "Dynamic Menu Operator Hub";
    }

    /**
     * Executes a menu within this class.
     * @param eventEnroller the menu to be executed.
     * @return optional null
     */
    @Override
    public Optional<Command> execute(EventEnroller eventEnroller) {
        eventEnroller.dynamicRun(this, "Back");
        return Optional.empty();
    }
}
