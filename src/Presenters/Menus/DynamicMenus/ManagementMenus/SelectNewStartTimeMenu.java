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
 * Allows for the selection of an end time for an event
 */
public class SelectNewStartTimeMenu extends DynamicMenu {
    private final Main main;
    private final int eventID;

    /**
     * Constructor for SelectNewEndTimeMenu
     * @param main type Main
     * @param title title of menu
     * @param ID of event
     */
    public SelectNewStartTimeMenu(String title, int ID, Main main){
        super(title);
        this.main = main;
        this.eventID = ID;
        this.header = "Select a Start Time For The Event";
    }

    /**
     * Builds command with necessary info
     * @param title of menu
     * @return GenericCmd()
     */
    @Override
    public Command buildCommand(String title, int startTime) {
        return new SelectNewEndTimeMenu(title, startTime, eventID, main);
    }

    /**
     * Updates the start times
     */
    @Override
    public void update() {
        buildOptionsFromHashMap(main.getOrganizerController().getAvailableTimesGivenEventID(eventID));
        addOption(new ExitCmd());
    }

    /**
     * Gets the execution location
     * @return String of execution location
     */
    @Override
    public String executionLocation() {
        return "Event Manager";
    }

    @Override
    public Optional<Command> execute(EventManager eventManager){
        eventManager.runUntilBackOrExit(this);
        return Optional.empty();
    }
}
