/*
@layer: Presenter - Menu
 */

package Presenters.Menus.DynamicMenus.ManagementMenus;

import Controllers.EventRegisterController;
import Controllers.Main;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.ManagementCommands.DeleteEventCmd;
import Controllers.MenuAdapters.EventManager;
import Presenters.Menus.DynamicMenus.DynamicMenu;

import java.util.Optional;

/**
 * A menu that shows a list of event that can be deleted.
 */
public class DeleteEventMenu extends DynamicMenu {

    private final EventRegisterController eventRegisterController;

    /**
     * Constructor for DeleteEventMenu
     * @param main type Main
     */
    public DeleteEventMenu(Main main) {
        super("Cancel Events");
        this.eventRegisterController = main.getEventRegisterController();
    }

    /**
     * Builds DeleteEventCmd with necessary info
     * @param title of menu
     * @param ID Id of event
     * @return DeleteEventCmd Command
     */
    @Override
    public Command buildCommand(String title, int ID) {
        return new DeleteEventCmd(title, ID);
    }

    /**
     * Updates the events in the system
     */
    @Override
    public void update() {
        buildOptionsFromHashMap(eventRegisterController.getEvents());
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
    public Optional<Command> execute(EventManager eventManager){
        eventManager.dynamicRun(this, "Back");
        return Optional.empty();
    }
}
