/*
@layer: Presenter - Menu
*/

package Presenters.Menus.DynamicMenus.ManagementMenus;

import Controllers.Main;
import Controllers.MenuAdapters.EventManager;
import Presenters.Menus.DynamicMenus.DynamicMenu;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.ExitCmd;

import java.util.Optional;

/**
 * A Dynamic menu that allows the user to reschedule an event
 */
public class RescheduleEventMenu extends DynamicMenu {
    private final Main main;

    /**
     * Constructor for RescheduleEventMenu
     * @param main type Main
     */
    public RescheduleEventMenu(Main main){
        super("Reschedule an Event");
        this.main = main;
    }

    /**
     * builds a new AddRemovePropertyCommand.
     * @param title title of the event
     * @param ID this does not matter
     * @return SelectNewStartTimeMenu command
     */
    @Override
    public Command buildCommand(String title, int ID) { return new SelectNewStartTimeMenu(title, ID, main);
    }

    @Override
    public void update() {
        buildOptionsFromHashMap(main.getEventRegisterController().getEvents());
        addOption(new ExitCmd());
    }

    /**
     * Gets execution location of menu
     * @return String of execution location
     */
    @Override
    public String executionLocation() {
        return "Event Manager";
    }

    /**
     * Executes a menu within this class.
     * @param eventManager the menu to be executed.
     * @return optional null
     */
    @Override
    public Optional<Command> execute(EventManager eventManager){
        eventManager.runUntilBackOrExit(this);
        return Optional.empty();
    }
}
