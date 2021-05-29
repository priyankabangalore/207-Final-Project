/*
@layer: Presenter - Menu
 */

package Presenters.Menus.DynamicMenus.ManagementMenus;

import Controllers.Main;
import Controllers.OrganizerController;
import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.EventManager;
import Presenters.Menus.DynamicMenus.DynamicMenu;

import java.util.ArrayList;
import java.util.Optional;

/**
 * A menu that shows a list of rooms that can host an event.
 */
public class SelectRoom extends DynamicMenu {

    private final OrganizerController organizerController;
    private ArrayList<String> properties = new ArrayList<>();
    private int capacity;

    /**
     * Constructor for SelectRoom
     * @param main type Main
     */
    public SelectRoom(Main main, String title) {
        super(title);
        this.header = "The below rooms match your specifications, select one to host your Event";
        organizerController = main.getOrganizerController();
    }

    /**
     * Builds SelectSpeaker command with necessary info
     * @param title title of menu
     * @param ID ID of room
     * @return SelectSpeaker command
     */
    @Override
    public Command buildCommand(String title, int ID) {
        return new SelectSpeaker(organizerController, title, ID);
    }

    /**
     * Updates room schedule in the system
     */
    @Override
    public void update() {
        buildOptionsFromHashMap(organizerController.getRoomsWithPropertiesAndCapacityDict(properties, capacity));
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
    @Override
    public Optional<Command> execute(EventManager builder){
        this.capacity = builder.userSetCapacity();
        this.properties = builder.getProperties();
        return builder.runUntilBackOrExit(this);
    }
}
