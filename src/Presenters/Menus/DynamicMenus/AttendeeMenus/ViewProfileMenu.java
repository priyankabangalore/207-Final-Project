/*
@layer: Presenter - Menu
 */

package Presenters.Menus.DynamicMenus.AttendeeMenus;

import Controllers.Main;
import Presenters.Menu;
import Presenters.PresenterHelperCommands.AttendeeCommands.*;
import Presenters.PresenterHelperCommands.BackCmd;
import Presenters.PresenterHelperCommands.Command;
import Presenters.Menus.DynamicMenus.DynamicMenu;

/**
 * A menu that allows a user to view their profile
 * @author Priyanka
 */
public class ViewProfileMenu extends DynamicMenu {

    /**
     * Constructor for ViewProfileMenu
     */
    public ViewProfileMenu(Main main, Menu parent) {
        super("Manage My Account Credentials", parent);
        addOption(new BackCmd());
        addOption(new ChangePasswordCmd());
        addOption(new ChangeEmailCmd());
        addOption(new ChangeUsernameCmd());
    }

    /**
     * Builds command using necessary info
     * @param title
     * @param ID of user
     */
    @Override
    public Command buildCommand(String title, int ID) {
        return null;
    }

    /**
     * Updates nothing
     */
    @Override
    public void update() {

    }

    /**
     * Gets execution location of menu
     * @return String of execution location
     */
    @Override
    public String executionLocation() {
        return "MenuPresenter";
    }

}
