/*
@layer: Presenter - Menu
 */

package Presenters.Menus.DynamicMenus.ManagementMenus;

import Controllers.OrganizerController;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.ExitCmd;
import Controllers.MenuAdapters.EventManager;
import Presenters.Menus.DynamicMenus.DynamicMenu;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Allows for the selection of a time to host an event given the schedules of the speaker and room to host the event.
 */
public class SelectStartTimes extends DynamicMenu {

    private final OrganizerController organizerController;
    private final int roomID;
    private ArrayList<Integer> speakerIDs;
    private int date;

    /**
     * Constructor for SelectTimes
     * @param roomID ID of room
     * @param organizerController instantiation of OrganizerController
     * @param title title of menu
     */
    public SelectStartTimes(OrganizerController organizerController, String title, int roomID) {
        super(title);
        this.organizerController = organizerController;
        this.speakerIDs = new ArrayList<Integer>();
        this.roomID = roomID;
        header = "Select the Start Time of the Event";
    }

    /**
     * Builds SelectEndTime command with necessary info
     * @param title of menu
     * @return SelectEndTime Command
     */
    public Command buildCommand(String title, int startTime){
        return new SelectEndTime(organizerController, title, startTime, roomID);
    }

    /**
     * Updates the available times between a speaker and the rooms in the system
     */
    @Override
    public void update() {
        buildOptionsFromHashMap(organizerController.getAvailableTimesWithAllSpeakers(roomID, speakerIDs));
        addOption(new ExitCmd());
    }

    /**
     * Gets the execution location
     * @return String of execution location
     */
    @Override
    public String executionLocation() {
        return null;
    }

    /**
     * Executes a menu within this class.
     * @param builder the menu to be executed.
     * @return optional null
     */
    public Optional<Command> execute(EventManager builder){
        speakerIDs = builder.getSpeakers();
        return builder.runUntilBackOrExit(this);
    }
}
