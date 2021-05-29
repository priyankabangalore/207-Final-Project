/*
@layer: Presenter - Menu
*/

package Presenters.Menus;

import Presenters.PresenterHelperCommands.LogInCmd;
import Presenters.PresenterHelperCommands.RegisterCmd;
import Presenters.PresenterHelperCommands.ShutDownCmd;
import Presenters.Menu;

/**
 * A menu shown to the user upon login.
 */
public class LoginMenu extends Menu {

    /**
     * Constructor for LoginMenu
     */
    public LoginMenu() {
        super("LoginMenu");
        addOption(new ShutDownCmd());
        addOption(new RegisterCmd());
        addOption(new LogInCmd());
    }
}
