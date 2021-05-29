/*
@layer: Presenter - Menu
*/

package Presenters.Menus.DynamicMenus.ManagementMenus;

import Controllers.Main;
import Controllers.MenuAdapters.EventManager;
import Presenters.Menus.DynamicMenus.DynamicMenu;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.ManagementCommands.RescheduleEventCmd;

import java.util.Optional;

/**
 * Allows for the selection of an end time for an event
 */
public class SelectNewEndTimeMenu extends DynamicMenu {
    private final Integer eventID;
    private final Integer startTime;

    /**
     * Constructor for SelectNewEndTimeMenu
     * @param main type Main
     * @param title title of menu
     */
    public SelectNewEndTimeMenu(String title, Integer startTime, Integer eventId, Main main) {
        super(title, main);
        this.startTime = startTime;
        this.eventID = eventId;
    }

    /**
     * Builds command with necessary info
     * @param title of menu
     */
    @Override
    public Command buildCommand(String title, int longStartTime) {
        String longDate = Integer.toString(longStartTime);
        if (longDate.length() == 7) {
            longDate = "0" + longDate;
        }
        Integer date = Integer.parseInt(longDate.substring(0,4));
        Integer startTime = Integer.parseInt(longDate.substring(4,6));
        Integer endTime = Integer.parseInt(longDate.substring(6,8));
        return new RescheduleEventCmd(title, eventID, startTime, endTime, date);
    }

    /**
     * Updates the end times
     */
    @Override
    public void update() {
        buildOptionsFromHashMap(main.getOrganizerController().getAvailableEndTimes(eventID, startTime));
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
