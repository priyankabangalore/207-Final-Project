/*
@layer: Presenter - Menu
 */

package Presenters.Menus.MenuTreeBuilders;

import Controllers.Main;
import Presenters.Menus.DynamicMenus.AttendeeMenus.ViewProfileMenu;
import Presenters.Menus.DynamicMenus.ConversationMenus.AddAttendeeMenu;
import Presenters.PresenterHelperCommands.AdminUserCommands.BlockUserCmd;
import Presenters.PresenterHelperCommands.AdminUserCommands.DeleteConversationCmd;
import Presenters.PresenterHelperCommands.AdminUserCommands.UnblockUserCmd;
import Presenters.PresenterHelperCommands.BackCmd;
import Presenters.PresenterHelperCommands.CoversationCommands.CommandStrats.ArchiveMenuCmd;
import Presenters.PresenterHelperCommands.CoversationCommands.CommandStrats.MarkUnreadCmd;
import Presenters.PresenterHelperCommands.CoversationCommands.CommandStrats.UnarchiveCmd;
import Presenters.PresenterHelperCommands.FinaliseCmd;
import Presenters.Menu;
import Presenters.PresenterHelperCommands.LogOutCmd;
import Presenters.PresenterHelperCommands.ManagementCommands.*;
import Presenters.PresenterHelperCommands.RequestStrategiesAndCommands.CancelRequestStrat;
import Presenters.PresenterHelperCommands.RequestStrategiesAndCommands.CreateRequestStrat;
import Presenters.PresenterHelperCommands.RequestStrategiesAndCommands.MarkAdressedStrat;
import Presenters.PresenterHelperCommands.RequestStrategiesAndCommands.MarkPendingStrat;
import Presenters.PresenterHelperCommands.ShutDownCmd;

/**
 * Builds menu object that are common between all users.
 */
public class UserMenuBuilder {

    final int permissionLvl;
    final Main main;

    /**
     * Constructor for UserMenuBuilder
     * @param permissionLvl permission level of the user (organizer, attendee, speaker)
     * @param main type Main
     */
    UserMenuBuilder(int permissionLvl, Main main){
        this.permissionLvl = permissionLvl;
        this.main = main;
    }

    /**
     * Does something with empty
     * @return Main Menu
     */
    Menu emptyMainMenu(){
        return new Menu("Main Menu");
    }

    /**
     * Gets permission level of user
     * @return permission level
     */
    int getPermissionLvl(){
        return permissionLvl;
    }

    /**
     * Builds messaging menu
     */
    protected Menu messagingMenu(Menu parent) {
        Menu menu = new Menu("Messaging", parent);
        menu.addOption(new BackCmd());
        menu.addOption(new UnarchiveCmd());
        menu.addOption(new ArchiveMenuCmd());
        menu.addOption(new MarkUnreadCmd());
        menu.addOption(newConversation());
        menu.addOption(viewConversations());
        return menu;
    }

    /**
     * Allows user to create a new conversation
     */
    Menu newConversation() {
        Menu newConvoMenu = new Presenters.Menus.DynamicMenus.ConversationMenus.BuildConversationMenu("New Conversation");
        switch (permissionLvl) {
            case (0):
                newConvoMenu.addOption(new Presenters.Menus.DynamicMenus.ConversationMenus.AddFriendMenu(main));
                break;
            case (1):
                newConvoMenu.addOption(new Presenters.Menus.DynamicMenus.ConversationMenus.AddAttendeesFromEventMenu(main));
                break;
            case (2):
            case (4):
                newConvoMenu.addOption(new Presenters.Menus.DynamicMenus.ConversationMenus.AddSpeakersMenu(main));
                newConvoMenu.addOption(new AddAttendeeMenu(main));
                break;
        }
        newConvoMenu.addOption(new FinaliseCmd());

        return newConvoMenu;
    }

    /**
     * Allows user to manage requests
     */
    Menu manageRequests(Menu parent){
        Menu requests = new Menu("Manage Requests", parent);
        switch (permissionLvl){
            case (0):
            case (1):
                requests.addOption(new CreateRequestStrat(main));
                requests.addOption(new CancelRequestStrat(main));
                break;
            case (2):
            case (3):
                requests.addOption(new MarkAdressedStrat(main));
                requests.addOption(new MarkPendingStrat(main));
                break;
        }
        requests.addOption(new BackCmd());
        return requests;
    }

    /**
     * Builds the managementMenu.
     * @param parent the parent menu for this menu
     * @return a management menu
     */
    protected Menu managementMenu(Menu parent) {
        Menu menu = new Menu("Management Menu", parent);
        if (permissionLvl == 3) {
            menu.addOption(new DeleteConversationCmd());
            menu.addOption(new ViewVenueCmd());
            menu.addOption(manageBansMenu(menu));
        }

        menu.addOption(new EventManagementCmd());
        menu.addOption(new GetNewRoomCmd());
        menu.addOption(createUsersMenu(menu));
        menu.addOption(new BackCmd());

        return menu;
    }

    /**
     * Create a menu for user creation
     * @param parent parent menu
     * @return user creation menu
     */
    private Menu createUsersMenu(Menu parent){
        Menu menu = new Menu("Create Users", parent);
        menu.addOption(new BackCmd());
        menu.addOption(new CreateSpeakerCmd());
        menu.addOption(new CreateAttendeeCmd());
        if(permissionLvl == 3){
            menu.addOption(new CreateOrganiserCmd());
        }
        return menu;
    }

    /**
     * Creates menu that manages bans
     * @param parent the parent menu
     * @return a ban menu
     */
    private Menu manageBansMenu(Menu parent){
        Menu menu = new Menu("Manage Bans", parent);
        menu.addOption(new BackCmd());
        menu.addOption(new UnblockUserCmd());
        menu.addOption(new BlockUserCmd());
        return menu;
    }

    /**
     * Allows for user to view their conversations
     */
    protected Menu viewConversations() {
        return new Presenters.Menus.DynamicMenus.ConversationMenus.SelectConverstionMenu(main);
    }

    /**
     * Builds the main menu with common options for all users.
     */
    public Menu buildMenu() {
        Menu mainMenu = new Menu("Main Menu");

        mainMenu.addOption(new ShutDownCmd());
        mainMenu.addOption(new LogOutCmd());
        mainMenu.addOption(new ViewProfileMenu(main, mainMenu));
        mainMenu.addOption(messagingMenu(mainMenu));
        return mainMenu;
    }
}
