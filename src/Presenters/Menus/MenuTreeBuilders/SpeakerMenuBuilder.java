/*
@layer: Presenter - Menu
 */

package Presenters.Menus.MenuTreeBuilders;

import Controllers.Main;
import Presenters.PresenterHelperCommands.SpeakerCommands.ShowRulesCmd;
import Presenters.PresenterHelperCommands.SpeakerCommands.ViewEvents;
import Presenters.Menu;
import Presenters.PresenterHelperCommands.SpeakerCommands.ViewRatingsCmd;

public class SpeakerMenuBuilder extends UserMenuBuilder {

    /**
     * Constructor for SpeakerMenuBuilder
     * @param main type Main
     */
    SpeakerMenuBuilder(Main main) {
        super(1, main);
    }

    /**
     * Returns a set of menus tailored for a speaker.
     */
    @Override
    public Menu buildMenu() {
        Menu mainMenu = super.buildMenu();

        mainMenu.addOption(new ShowRulesCmd());

        mainMenu.addOption(manageRequests(mainMenu));
        mainMenu.addOption(new ViewRatingsCmd());
        mainMenu.addOption(new ViewEvents());

        return mainMenu;
    }
}
