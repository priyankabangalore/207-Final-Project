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
public class SelectEndTime extends DynamicMenu {

    private final OrganizerController organizerController;
    private final int roomID;
    private ArrayList<Integer> speakerIDs;
    private final int startTime;

    /**
     * Constructor for SelectTimes
     * @param roomID ID of room
     * @param organizerController instantiation of OrganizerController
     * @param title title of menu
     */
    public SelectEndTime(OrganizerController organizerController, String title, int startTime, int roomID) {
        super(title);
        this.organizerController = organizerController;
        this.speakerIDs = new ArrayList<>();
        this.roomID = roomID;
        this.startTime = startTime;
        header = "Select an End Time For The Event";
    }

    /**
     * Builds AddAttendees command with necessary info
     * @param title title of menu
     * @param date date of event
     * @return AddAttendees Command
     */
    public Command buildCommand(String title, int date){
        return new AddAttendees(title, date, organizerController);
    }

    /**
     * Updates the available end times between the speakers and the rooms in the system
     */
    @Override
    public void update() {
        buildOptionsFromHashMap(organizerController.getAvailableEndTimesWithAllSpeakers(roomID, speakerIDs, startTime));
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
        builder.setStartTime(startTime);
        return builder.runUntilBackOrExit(this);
    }
}
