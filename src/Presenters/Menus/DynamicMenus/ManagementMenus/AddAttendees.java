/*
@layer: Presenter - Menu
 */

package Presenters.Menus.DynamicMenus.ManagementMenus;

import Controllers.OrganizerController;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.FinaliseCmd;
import Presenters.PresenterHelperCommands.ManagementCommands.AddAttendeeCmd;
import Controllers.MenuAdapters.EventManager;
import Presenters.Menus.DynamicMenus.DynamicMenu;

import java.util.Optional;

/**
 * A menu that shows a user a list of attendees they can add to an event.
 */
public class AddAttendees extends DynamicMenu {

    private final int endTime;
    private final OrganizerController organizerController;

    /**
     * Constructor for AddAttendees
     * @param title title of menu
     * @param organizerController instantiation of OrganizerController
     */
    public AddAttendees(String title, int endTime, OrganizerController organizerController) {
        super(title);
        this.endTime = endTime;
        this.organizerController = organizerController;
    }

    /**
     * Builds AddAttendeeCmd with necessary info
     * @param ID ID of attendee
     * @param title attendee's name
     */
    @Override
    public Command buildCommand(String title, int ID) {
        return new AddAttendeeCmd(title, ID);
    }

    /**
     * Updates the attendees in the system
     */
    @Override
    public void update() {
        buildOptionsFromHashMap(organizerController.getAttendees());
        addOption(new FinaliseCmd("Create Event"));
    }

    /**
     * Gets execution location
     * @return String of execution location
     */
    @Override
    public String executionLocation() {
        return null;
    }

    /**
     * Executes a menu within this class.
     * @param builder the menu to be executed.
     * @return null optional
     */
    public Optional<Command> execute(EventManager builder){
        builder.setEndTime(endTime);
        Optional<Command> op = Optional.of(new FinaliseCmd());
        return op;
    }
}
