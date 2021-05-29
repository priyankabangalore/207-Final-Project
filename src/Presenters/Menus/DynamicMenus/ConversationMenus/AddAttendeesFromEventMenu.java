/*
@layer: Presenter - Menu
 */

package Presenters.Menus.DynamicMenus.ConversationMenus;

import Controllers.Main;
import Controllers.SpeakerController;
import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.ConversationBuilder;
import Presenters.Menus.DynamicMenus.DynamicMenu;

import java.util.Optional;

/**
 * Adds attendees to a conversation from a given event.
 */
public class AddAttendeesFromEventMenu extends DynamicMenu {

    private final SpeakerController speakerController;
    private final Main main;

    /**
     * Constructor for AddAttendeesFromEventMenu
     * @param main type Main
     */
    public AddAttendeesFromEventMenu(Main main) {
        super("Add Attendees From Specific Event");
        this.main = main;
        this.speakerController = main.getSpeakerController();
    }

    /**
     * Builds SelectAttendeesFromEvent command with needed info
     * @param title title of menu
     * @param ID id of attendee
     * @return SelectAttendeesFromEvent command
     */
    @Override
    public Command buildCommand(String title, int ID) {
        return new SelectAttendeesFromEvent(main, ID, "Add From" + title);
    }

    /**
     * Updates schedule of speaker
     */
    @Override
    public void update() {
        buildOptionsFromHashMap(speakerController.getSchedule(main.getLoginController().getCurrentUserID()));
    }

    /**
     * Gets execution location of menu
     * @return String of execution location
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
