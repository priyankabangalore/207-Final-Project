package Presenters.Menus.MenuTreeBuilders;

import Controllers.Main;
import Presenters.Menu;

public class AdminUserMenuBuilder extends UserMenuBuilder{

    public AdminUserMenuBuilder (Main main) {
        super(3, main);
    }

    /**
     * A menu for admin user
     * @return a menu for the admin
     */
    @Override
    public Menu buildMenu(){
        Menu mainMenu = super.buildMenu();


        mainMenu.addOption(managementMenu(mainMenu));

        return mainMenu;
    }
}
