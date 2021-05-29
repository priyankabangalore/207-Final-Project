package Controllers.MenuAdapters;

import Controllers.AdminUserController;
import Controllers.Main;
import Presenters.Menus.DynamicMenus.ManagementMenus.BlockUserMenu;
import Presenters.Menus.DynamicMenus.ManagementMenus.UnblockUserMenu;
import Presenters.PresenterHelperCommands.Command;

import java.util.Optional;
/**
 * An operator that allow block and unblock user from logging in.
 */

public class BlockAndUnblockUserOperator extends DynamicMenuOperator{
    private final AdminUserController auc;

    /**
     * construct the operator
     * @param main: type Main
     */
    public BlockAndUnblockUserOperator(Main main){
        super(main);
        auc = main.getAdminUserController();
    }

    /**
     * block the user with the ID
     * @param title title
     * @param userID the userID you want to block
     */
    public void BlockUser(String title, int userID){auc.blockUser(userID);}

    /**
     * run the menu displaying all the user to choose which to block
     */
    public void runBlockMenu(){
        BlockUserMenu blockUserMenu = new BlockUserMenu(main);
        runUntilBackOrExit(blockUserMenu);
    }

    /**
     * unblock the user with the ID
     * @param title
     * @param userID ID of user
     */
    public void unblockUser(String title, int userID){auc.unblockUser(userID);}

    /**
     * run the menu displaying all the user to choose which to block
     */
    public void runUnblockMenu(){
        UnblockUserMenu unblockUserMenu = new UnblockUserMenu(main);
        runUntilBackOrExit(unblockUserMenu);
    }

    /**
     * execute the cmd in this class
     * @param command the command to be executed
     * @return optional a command to be executed in another part of the program
     */
    public Optional<Command> executeCmd(Command command){return command.execute(this);}

}
