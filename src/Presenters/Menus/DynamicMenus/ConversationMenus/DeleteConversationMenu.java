package Presenters.Menus.DynamicMenus.ConversationMenus;

import Controllers.AdminUserController;
import Controllers.ConversationController;
import Controllers.Main;
import Presenters.Menus.DynamicMenus.DynamicMenu;
import Presenters.PresenterHelperCommands.AdminUserCommands.DeleteConversationCmd;
import Presenters.PresenterHelperCommands.Command;

import java.util.HashMap;

public class DeleteConversationMenu extends DynamicMenu {
    private final AdminUserController auc;

    /**
     * Constructor for DeleteConversationMenu
     * @param main type Main
     */
    public DeleteConversationMenu(Main main){
        super("Delete Conversation");
        ConversationController cc = main.getConversationController();
        auc = main.getAdminUserController();
    }

    /**
     * Delete a conversation
     * @param title
     * @param ID
     * @return
     */
    @Override
    public Command buildCommand(String title, int ID) {
        return new DeleteConversationCmd(title, ID);
    }

    /**
     * Update all the conversations
     */
    public void update() {
        HashMap<String, Integer> allConversation = auc.showAllConversation();
        buildOptionsFromHashMap(allConversation);
    }

    /**
     * Gets execution location of menu
     * @return String of execution location
     */
    @Override
    public String executionLocation(){
        return "DeleteConversationOperator";
    }
}
