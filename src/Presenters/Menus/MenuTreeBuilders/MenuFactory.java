/*
@layer: Presenter - Menu
 */

package Presenters.Menus.MenuTreeBuilders;
import Controllers.Main;
import Presenters.Menu;

import java.util.logging.Logger;

/**
 * Delegates the construction of the menu system to one of a set of builders depending up the type of the currently
 * logged in user.
 */
public class MenuFactory {
    private static final java.util.logging.Logger logger = Logger.getLogger(MenuFactory.class.getName());
    private final Main main;

    /**
     * Constructor for MenuBuilder
     * @param main type Main
     */
    public MenuFactory(Main main) {
        this.main = main;
        logger.addHandler(Main.handler);
    }

    /**
     * Builds a set of menus. Menu will have different options depending upon the <permissionLevel>.
     * @param permissionLevel the permission level
     * @return a menu tree.
     */
    public Menu buildMenu(int permissionLevel) {
        switch (permissionLevel){
            case 0:
                Presenters.Menus.MenuTreeBuilders.AttendeeMenuBuilder aBuilder = new AttendeeMenuBuilder(main);
                return aBuilder.buildMenu();
            case 1:
                Presenters.Menus.MenuTreeBuilders.SpeakerMenuBuilder sBuilder = new SpeakerMenuBuilder(main);
                return sBuilder.buildMenu();
            case 2:
                Presenters.Menus.MenuTreeBuilders.OrganiserMenuBuilder oBuilder = new OrganiserMenuBuilder(main);
                return oBuilder.buildMenu();
            case 3:
                Presenters.Menus.MenuTreeBuilders.AdminUserMenuBuilder adBuilder = new AdminUserMenuBuilder(main);
                return adBuilder.buildMenu();
            default:
                throw new NoClassDefFoundError("A type of user specified that does not exist");
        }
    }
}
