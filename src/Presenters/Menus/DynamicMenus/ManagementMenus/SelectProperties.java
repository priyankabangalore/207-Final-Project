/*
@layer: Presenter - Menu
*/

package Presenters.Menus.DynamicMenus.ManagementMenus;

import Controllers.Main;
import Controllers.MenuAdapters.EventManager;
import Presenters.Menus.DynamicMenus.DynamicMenu;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.ManagementCommands.AddRemovePropertiesCommand;

import java.util.Optional;

/**
 * Allows for the selection of properties
 */
public class SelectProperties extends DynamicMenu {

    /**
     * Constructor for SelectProperties
     * @param main type Main
     * @param title title of menu
     */
    public SelectProperties(String title, Main main) {
        super(title, main, "Select Your Desired Property");
    }

    /**
     * Builds SelectRoom command with necessary info
     * @param title title of menu
     * @param ID ID of event
     * @return AddAttendees Command
     */
    @Override
    public Command buildCommand(String title, int ID) {
        return new AddRemovePropertiesCommand(title);
    }

    /**
     * Updates the properties
     */
    @Override
    public void update() {
        this.buildOptionsFromHashMap(main.getVenueController().getAllPropertiesDict());
        addOption(new SelectRoom(main, "Continue"));
    }

    /**
     * Gets the execution location
     * @return String of execution location
     */
    @Override
    public String executionLocation() {
        return "Event Manager";
    }

    /**
     * Executes a menu within this class.
     * @param target the menu to be executed.
     * @return optional null
     */
    @Override
    public Optional<Command> execute(EventManager target) {
        target.runUntilBackOrExit(this);
        return Optional.empty();
    }
}
