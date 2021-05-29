/*
@layer: Presenter - Menu
 */

package Presenters.Menus.DynamicMenus.AttendeeMenus;

import Controllers.Main;
import Presenters.PresenterHelperCommands.BackCmd;
import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.DynamicMenuOperatorHub;
import Presenters.Menu;

import java.util.Optional;

/**
 * A simple menu that allows the user to choose whether they would like to enrol or dis-enrol in an event.
 */
public class EventManagement extends Menu {

    /**
     * Constructor for EventManagement
     * @param main type Main
     */
    public EventManagement(Main main) {
        super("Manage Events");
        addOption(new EnrolInEvent(main));
        addOption(new DisEnrolInEvent(main));
        addOption(new BackCmd());
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
     * @param dynamicMenuOperatorHub the menu to be executed.
     * @return optional null
     */
    @Override
    public Optional<Command> execute(DynamicMenuOperatorHub dynamicMenuOperatorHub){
        dynamicMenuOperatorHub.openEventManager(this);
        return Optional.empty();
    }
}
