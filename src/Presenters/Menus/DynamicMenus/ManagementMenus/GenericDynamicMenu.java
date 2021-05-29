package Presenters.Menus.DynamicMenus.ManagementMenus;

import Presenters.Menus.DynamicMenus.DynamicMenu;
import Presenters.PresenterHelperCommands.Command;

public class GenericDynamicMenu extends DynamicMenu {

    public GenericDynamicMenu(String title) {
        super(title);
    }

    /**
     * Build a command
     * @param title the title of the menu
     * @param ID the ID of the User
     * @return null
     */
    @Override
    public Command buildCommand(String title, int ID) {
        return null;
    }

    /**
     * updates something
     */
    @Override
    public void update() {

    }

    /**
     * Gets execution location
     * @return String of execution location
     */
    @Override
    public String executionLocation() {
        return null;
    }
}
