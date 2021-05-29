/*
@layer: Presenter - Menu
 */

package Presenters.Menus.DynamicMenus.ConversationMenus;

import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.DynamicMenuOperatorHub;
import Presenters.Menu;

import java.util.Optional;

/**
 * A custom menu that is used to build conversations for each user.
 */
public class BuildConversationMenu extends Menu {

    /**
     * Constructor for BuildConversationMenu
     * @param title title of menu
     */
    public BuildConversationMenu(String title) {
        super(title);
    }

    /**
     * Gets execution location of menu
     * @return String of execution location
     */
    @Override
    public String executionLocation(){
        return "Dynamic Menu Operator Hub";
    }

    /**
     * Executes a menu within this class.
     * @param builder the menu to be executed.
     * @return optional null
     */
    public Optional<Command> execute(DynamicMenuOperatorHub builder){
        return builder.buildConversation(this);
    }
}
