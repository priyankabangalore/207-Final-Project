/*
@layer: Presenter - Menu
 */

package Presenters.Menus.DynamicMenus.ManagementMenus;

import Controllers.MenuAdapters.EventManager;
import Controllers.OrganizerController;
import Presenters.Menus.DynamicMenus.DynamicMenu;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.ExitCmd;
import Presenters.PresenterHelperCommands.ManagementCommands.AddRemoveSpeakerCmd;

import java.util.Optional;

/**
 * A menu that shows a list of speakers that host events
 */
public class SelectSpeaker extends DynamicMenu {

    private final int roomID;
    private final OrganizerController organizerController;

    /**
     * Constructor for SelectSpeaker
     * @param title menu title
     * @param organizerController instantiation of OrganizerController
     * @param roomID ID of room
     */
    public SelectSpeaker(OrganizerController organizerController, String title, int roomID){
        super(title);
        this.organizerController = organizerController;
        this.roomID = roomID;
        this.header = "Select Speakers:";
    }

    /**
     * Builds SelectTimes command with necessary info
     * @param ID
     * @param title title of menu
     * @return SelectTimes command
     */
    @Override
    public Command buildCommand(String title, int ID) {
        return new AddRemoveSpeakerCmd(title, ID);
    }

    /**
     * Updates a speaker's schedule in the system
     */
    @Override
    public void update() {
        buildOptionsFromHashMap(organizerController.getSpeakerSchedules());
        addOption(new SelectStartTimes(organizerController, "Continue", roomID));
        addOption(new ExitCmd());

    }

    /**
     * Gets execution location
     * @return String of execution location
     */
    @Override
    public String executionLocation() {
        return "Event Builder";
    }

    /**
     * Executes a menu within this class.
     * @param builder the menu to be executed.
     * @return optional null
     */
    public Optional<Command> execute(EventManager builder){
        builder.setRoom(roomID);
        return builder.runUntilBackOrExit(this);
    }
}
