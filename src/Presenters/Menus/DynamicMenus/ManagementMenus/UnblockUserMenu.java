package Presenters.Menus.DynamicMenus.ManagementMenus;

import Controllers.Main;
import Presenters.Menus.DynamicMenus.DynamicMenu;
import Presenters.PresenterHelperCommands.AdminUserCommands.UnblockUserCmd;
import Presenters.PresenterHelperCommands.Command;

public class UnblockUserMenu extends DynamicMenu {
    private final Main main;

    public UnblockUserMenu(Main main){
        super("Unblock a User");
        this.main = main;
    }
    /**
     * Build a UnblockUser command
     * @param title the title of the menu
     * @param ID the ID of the User
     * @return the UnblockUserCmd
     */
    @Override
    public Command buildCommand(String title, int ID) {
        return new UnblockUserCmd(title, ID);
    }

    /**
     * update the user list
     */
    @Override
    public void update() {
        buildOptionsFromHashMap(main.getAdminUserController().getBlockedUser());
    }

    /**
     * Gets execution location
     * @return String of execution location
     */
    @Override
    public String executionLocation(){
        return "Dynamic Menu Operator Hub";
    }
}
