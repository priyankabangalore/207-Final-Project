/*
@layer: Presenter - Menu
 */

package Presenters.Menus.DynamicMenus.ConversationMenus;

import Controllers.ConversationController;
import Controllers.LoginController;
import Controllers.Main;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.CoversationCommands.OpenConversationCmd;
import Presenters.PresenterHelperCommands.CoversationCommands.OpenDialogueConversationCmd;
import Controllers.MenuAdapters.DynamicMenuOperatorHub;
import Presenters.Menus.DynamicMenus.DynamicMenu;

import java.util.Optional;

/**
 * A menu used by the user to select from a list of available conversations they are a part of.
 */
public class SelectConverstionMenu extends DynamicMenu {

    private final ConversationController conversationController;
    private final LoginController loginController;

    /**
     * Constructor for SelectConversationMenu
     * @param main type Main
     */
    public SelectConverstionMenu(Main main) {
        super("View Conversations");
        this.conversationController = main.getConversationController();
        this.loginController = main.getLoginController();
    }

    /**
     * Creates a open conversation command for a given conversation, restricts a users' ability to message in that
     * conversation if they do not have permission.
     * @param title the title of the conversation
     * @param ID the id of the conversation
     * @return a command to open the conversation.
     */
    @Override
    public Command buildCommand(String title, int ID) {
        if(conversationController.currentlyLoggedInCanMessage(ID, loginController.getCurrentUserID())){
            return new OpenDialogueConversationCmd(title, ID);
        }
        else{
            return new OpenConversationCmd(title, ID);
        }
    }

    /**
     * Updates this menus options with the current conversations available to this user.
     */
    @Override
    public void update() {
        this.buildOptionsFromHashMap(conversationController.getConvos(loginController.getCurrentUserID()));
    }

    /**
     * Gets the execution location of the menu
     * @return String of execution location
     */
    @Override
    public String executionLocation() {
        return "Dynamic Menu Operator Hub";
    }

    /**
     * Executes a menu within this class.
     * @param dynamicMenuOperatorHub the menu to be executed.
     * @return optional null
     */
    public Optional<Command> execute(DynamicMenuOperatorHub dynamicMenuOperatorHub){
        return dynamicMenuOperatorHub.viewConversations(this);
    }
}
