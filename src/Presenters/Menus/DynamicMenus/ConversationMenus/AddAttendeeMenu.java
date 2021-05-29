/*
@layer: Presenter - Menu
 */

package Presenters.Menus.DynamicMenus.ConversationMenus;

import Controllers.Main;
import Controllers.OrganizerController;
import Presenters.PresenterHelperCommands.CoversationCommands.AddUserToConvoCmd;
import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.ConversationBuilder;
import Presenters.Menus.DynamicMenus.DynamicMenu;

import java.util.HashMap;
import java.util.Optional;

/**
 * Shows the user a list of users that they can add to an event.
 */
public class AddAttendeeMenu extends DynamicMenu {

    private final OrganizerController organiserController;

    /**
     * Creates a new attendee menu
     * @param main the main class.
     */
    public AddAttendeeMenu(Main main) {
        super("Add Attendees");
        this.organiserController = main.getOrganizerController();
    }

    /**
     * Builds AddUserToConvoCmd with necessary info
     * @param title title of menu
     * @param ID ID of convo
     * @return AddUserToConvoCmd
     */
    @Override
    public Command buildCommand(String title, int ID) {
        return new AddUserToConvoCmd(title, ID);
    }

    /**
     * Updates this menu to the current list of attendees
     * TODO add functionality to handle large lists of attendees
     * TODO check attendee availability
     */
    @Override
    public void update() {
        HashMap<String, Integer> attendeesMap = organiserController.getAttendees();
        buildOptionsFromHashMap(attendeesMap);
        addOption(new Presenters.PresenterHelperCommands.CoversationCommands.AddMultipleUsersCmd("Add All", attendeesMap));
    }

    /**
     * This menu should be executed within the conversation builder.
     * @return this classes execution location
     */
    @Override
    public String executionLocation() {
        return "Conversation Builder";
    }

    /**
     * Executes a menu within this class.
     * @param builder the menu to be executed.
     * @return optional null
     */
    @Override
    public Optional<Command> execute(ConversationBuilder builder){
        builder.addUsers(this);
        return Optional.empty();
    }
}
