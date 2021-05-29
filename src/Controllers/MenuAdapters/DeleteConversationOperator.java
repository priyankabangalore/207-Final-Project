package Controllers.MenuAdapters;

import Controllers.AdminUserController;
import Controllers.ConversationController;
import Controllers.Main;
import Presenters.Menus.DynamicMenus.ConversationMenus.DeleteConversationMenu;
import Presenters.PresenterHelperCommands.Command;

import java.util.Optional;

/**
 * a class allow view User's conversation and delete conversation
 */
public class DeleteConversationOperator extends DynamicMenuOperator {
    private final ConversationController cc;
    private final AdminUserController auc;

    /**\
     * construct this Operator
     * @param main the main you are in
     */
    public DeleteConversationOperator(Main main){
        super(main);
        this.cc = main.getConversationController();
        this.auc = main.getAdminUserController() ;
    }

    /**
     * display the menu with all the conversation can be deleted
     */
    public void runDeleteMenu(){
        DeleteConversationMenu deleteConversationMenu = new DeleteConversationMenu(main);
        runUntilBackOrExit(deleteConversationMenu);
    }

    /**
     * delete a conversation
     * @param title
     * @param conversationID
     */
    public void deleteConversation(String title, int conversationID){
        auc.deleteConversation(conversationID);
    }

    /**
     * execute the cmd in this class
     * @param command the cmd that need to be executed
     * @return a command to be executed in another part of the program
     */
    public Optional<Command> executeCmd(Command command){return command.execute(this);}
}
