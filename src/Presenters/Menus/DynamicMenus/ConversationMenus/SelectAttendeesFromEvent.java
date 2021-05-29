/*
@layer: Presenter - Menu
 */

package Presenters.Menus.DynamicMenus.ConversationMenus;

import Controllers.Main;
import Controllers.SpeakerController;
import Presenters.PresenterHelperCommands.CoversationCommands.AddUserToConvoCmd;
import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.ConversationBuilder;
import Presenters.Menus.DynamicMenus.DynamicMenu;

import java.util.HashMap;
import java.util.Optional;

/**
 * A menu that allows users to add users to their conversation from a list of all attendees.
 */
public class SelectAttendeesFromEvent extends DynamicMenu {

    /** The event specified by this menu. */
    private final int eventID;

    /** The SpeakerController responsible for updating this menu */
    private final SpeakerController speakerController;

    /**
     * Constructor for SelectAttendeesFromEvent
     * @param main type Main
     * @param title title of menu
     * @param eventID ID of the event
     */
    public SelectAttendeesFromEvent(Main main, int eventID, String title) {
        super(title);
        this.speakerController = main.getSpeakerController();
        this.eventID = eventID;
    }

    /**
     * Builds AddUserTOConvoCmd with necessary info
     * @param ID ID of user
     * @param title title of menu
     * @return AddUserToConvoCmd Command
     */
    @Override
    public Command buildCommand(String title, int ID) {
        return new AddUserToConvoCmd(title, ID);
    }

    /**
     * Updates attendees attending an event
     */
    @Override
    public void update() {
        HashMap<String, Integer> attendees = speakerController.getAttendeeInEvent(eventID);
        buildOptionsFromHashMap(attendees);
        addOption(new Presenters.PresenterHelperCommands.CoversationCommands.AddMultipleUsersCmd("Add all", attendees));
    }

    /**
     * Gets execution location
     * @return String of execution location
     */
    @Override
    public String executionLocation() {
        return "ConversationBuilder";
    }

    /**
     * Executes a menu within this class.
     * @param builder the menu to be executed.
     * @return optional null
     */
    public Optional<Command> execute(ConversationBuilder builder){
        builder.addUsers(this);
        return Optional.empty();
    }
}
