/*
@layer: Presenter - Menu
 */

package Presenters.Menus.MenuTreeBuilders;


import Controllers.Main;
import Presenters.Menu;
import Presenters.Menus.DynamicMenus.AttendeeMenus.EventManagement;
import Presenters.Menus.DynamicMenus.AttendeeMenus.FilterEventsMenu;
import Presenters.Menus.DynamicMenus.AttendeeMenus.RemoveFriendMenu;
import Presenters.PresenterHelperCommands.AttendeeCommands.*;
import Presenters.PresenterHelperCommands.BackCmd;

/**
 * The builder responsible for building a set of menus for the user.
 */
public class AttendeeMenuBuilder extends UserMenuBuilder {
    public AttendeeMenuBuilder(Main main) {
        super(0, main);
    }

    /**
     * Builds the main menu for the attendee.
     */
    @Override
    public Menu buildMenu(){
        Menu mainMenu = super.buildMenu();

        mainMenu.addOption(new ShowRulesCmd());
        mainMenu.addOption(viewLeaveRatingsMenu(mainMenu));
        mainMenu.addOption(friendsMenu(mainMenu));
        mainMenu.addOption(eventsMenu(mainMenu));
        mainMenu.addOption(manageRequests(mainMenu));
        return mainMenu;
    }

    /**
     * Builds a menu for friends management.
     * @return a friend management menu.
     */
    public Menu friendsMenu(Menu parent){
        Menu menu = new Menu("Friends Options", parent);
        menu.addOption(new BackCmd());
        menu.addOption(new RemoveFriendMenu(main));
        menu.addOption(new AddNewFriendCmd());
        menu.addOption(new ShowFriendsEventsCmd());
        menu.addOption(new ShowFriendsCmd());


        return menu;
    }

    /**
     * A menu for managing events
     * @param parent the parent menu
     * @return a menu for managing events
     */
    public Menu eventsMenu(Menu parent){
        Menu menu = new Menu("Event Options", parent);

        menu.addOption(new FilterEventsMenu("Search for Events", main));
        menu.addOption(new BackCmd());
        menu.addOption(new EventManagement(main));
        menu.addOption(new ShowMyEventsCmd());

        return menu;
    }

    public Menu viewLeaveRatingsMenu(Menu parent){
        Menu menu = new Menu("Leave and View Speaker Ratings", parent);
        menu.addOption(new BackCmd());
        menu.addOption(new ViewRatingsCmd());
        menu.addOption(new ViewAllReviewsCmd());
        menu.addOption(new LeaveRatingCommand());
        return menu;
    }
}
