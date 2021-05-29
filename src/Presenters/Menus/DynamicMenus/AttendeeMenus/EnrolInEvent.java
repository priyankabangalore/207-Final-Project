/*
@layer: Presenter - Menu
 */

package Presenters.Menus.DynamicMenus.AttendeeMenus;

import Controllers.EventRegisterController;
import Controllers.LoginController;
import Controllers.Main;
import Presenters.PresenterHelperCommands.AttendeeCommands.EnrolInEventCmd;
import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.EventEnroller;
import Presenters.Menus.DynamicMenus.DynamicMenu;

import java.util.Optional;

/**
 * A dynamic menu that allows users to enrol in specific events.
 */
public class EnrolInEvent extends DynamicMenu {

    private final EventRegisterController eventRegisterController;
    private final LoginController loginController;

    /**
     * Constructor for EnrolInEvent
     * @param main type Main
     */
    public EnrolInEvent(Main main) {
        super("Enrol in a New Event");
        this.eventRegisterController = main.getEventRegisterController();
        this.loginController = main.getLoginController();
    }

    /**
     * Builds command EnrolInEventCmd with necessary info
     * @param title of event
     * @param ID of event
     * @return EnrolInEventCmd command
     */
    @Override
    public Command buildCommand(String title, int ID) {
        return new EnrolInEventCmd(title, ID);
    }

    /**
     * Sets this menu's options to a list of enrol commands for each event this user is attending.
     */
    @Override
    public void update() {
        this.buildOptionsFromHashMap(eventRegisterController.getEventNotEnrolledWithSpace(
                loginController.getCurrentUserID()));
    }

    /**
     * Gets execution location
     * @return String of execution location
     */
    @Override
    public String executionLocation() {
        return "Dynamic Menu Operator Hub";
    }

    /**
     * Executes a menu within this class.
     * @param eventEnroller the menu to be executed.
     * @return null optional
     */
    @Override
    public Optional<Command> execute(EventEnroller eventEnroller) {
        eventEnroller.dynamicRun(this, "Back");
        return Optional.empty();
    }
}
