/*
@layer: Presenter - Menu
 */

package Presenters.Menus.DynamicMenus.ConversationMenus;

import Controllers.Main;
import Controllers.OrganizerController;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.CoversationCommands.AddMultipleUsersCmd;
import Presenters.PresenterHelperCommands.CoversationCommands.AddUserToConvoCmd;
import Controllers.MenuAdapters.ConversationBuilder;
import Presenters.Menus.DynamicMenus.DynamicMenu;

import java.util.HashMap;
import java.util.Optional;

/**
 * Adds speakers to the conversation currently being built.
 */
public class AddSpeakersMenu extends DynamicMenu {

    /** The SpeakerController responsible for updating this menu */
    private final OrganizerController organiserController;

    /**
     * Constructor for AddSpeakersMenu
     * @param main type Main
     */
    public AddSpeakersMenu(Main main) {
        super("Add Speakers");
        organiserController = main.getOrganizerController();
    }

    /**
     * Builds AddUserToConvoCmd with necessary info
     * @param ID ID of user
     * @param title title of menu
     * @return AddUserToConvoCmd Command
     */
    @Override
    public Command buildCommand(String title, int ID) {
        return new AddUserToConvoCmd(title, ID);
    }

    /**
     * Updates speakers in system
     */
    @Override
    public void update() {
        HashMap<String, Integer> speakers = organiserController.getSpeakers();
        buildOptionsFromHashMap(speakers);
        addOption(new AddMultipleUsersCmd("Add All", speakers));
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
     * @return a menu to be executed in another part of the program
     */
    @Override
    public Optional<Command> execute(ConversationBuilder builder){
        builder.addUsers(this);
        return Optional.empty();
    }
}
