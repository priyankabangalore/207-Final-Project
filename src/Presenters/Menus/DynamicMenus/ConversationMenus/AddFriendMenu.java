/*
@layer: Presenter - Menu
 */

package Presenters.Menus.DynamicMenus.ConversationMenus;

import Controllers.AttendeeController;
import Controllers.Main;
import Presenters.PresenterHelperCommands.CoversationCommands.AddUserToConvoCmd;
import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.ConversationBuilder;
import Presenters.Menus.DynamicMenus.DynamicMenu;

import java.util.HashMap;
import java.util.Optional;

/**
 * A menu that can display all this users friends, and add each to a conversation.
 */
public class AddFriendMenu extends DynamicMenu {

    private final AttendeeController attendeeController;
    private final Main main;

    /**
     * Instantiates a new AddFriendsMenu that relies upon <main> for its options.
     * @param main the source of this menus' options.
     */
    public AddFriendMenu(Main main) {
        super("Add Friend");
        this.attendeeController = main.getAttendeeController();
        this.main = main;
    }

    /**
     * Updates the list to contain a full list of a users friends.
     */
    @Override
    public void update() {
        HashMap<String, Integer> friendsMap = attendeeController.getFriendsDict(
                main.getLoginController().getCurrentUserID());
        buildOptionsFromHashMap(friendsMap);
    }

    /**
     * Builds AddUserToConvoCmd
     * @param title menu title
     * @param ID user's ID
     * @return AddUserToConvoCmd Command
     */
    @Override
    public Command buildCommand(String title, int ID){
        return new AddUserToConvoCmd(title, ID);}

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
     * @return null optional
     */
    @Override
    public Optional<Command> execute(ConversationBuilder builder){
        builder.addUsers(this);
        return Optional.empty();
    }
}
