/*
@layer: Presenter - Menu
 */

package Presenters.Menus.MenuTreeBuilders;

import Controllers.Main;
import Presenters.Menu;

/**
 * Responsible for building a tree of menus for the organiser user.
 */
public class OrganiserMenuBuilder extends UserMenuBuilder {

    /**
     * Constructor for OrganiserMenuBuilder
     * @param main type Main
     */
    public OrganiserMenuBuilder(Main main) {
        super(2, main);
    }

    /**
     * Builds a menu tree tailored for an organizer.
     * @return a menu tree
     */
    @Override
    public Menu buildMenu(){
        Menu mainMenu = super.buildMenu();


        mainMenu.addOption(manageRequests(mainMenu));
        mainMenu.addOption(managementMenu(mainMenu));

        return mainMenu;
    }
}

