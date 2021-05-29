package Presenters.Menus.DynamicMenus.ManagementMenus;

import Controllers.Main;
import Presenters.Menus.DynamicMenus.DynamicMenu;
import Presenters.PresenterHelperCommands.AdminUserCommands.BlockUserCmd;
import Presenters.PresenterHelperCommands.Command;

public class BlockUserMenu extends DynamicMenu {
    private final Main main;

    public BlockUserMenu(Main main){
        super("Block a User");
        this.main = main;
    }

    /**
     * Build a BanUser command
     * @param title the title of the menu
     * @param ID the ID of the User
     * @return the BanUserCmd
     */
    @Override
    public Command buildCommand(String title, int ID) {
        return new BlockUserCmd(title, ID);
    }

    /**
     * update the user list
     */
    @Override
    public void update() {
        buildOptionsFromHashMap(main.getAdminUserController().getUnblockedUser());
    }

    /**
     * Gets execution location
     * @return String of execution location
     */
    @Override
    public String executionLocation(){
        return "BlockAndUnblockUserOperator";
    }
}
