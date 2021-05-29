/*
@layer: Presenter - Menu
 */


package Presenters.Menus.DynamicMenus.AttendeeMenus;

import Controllers.AttendeeController;
import Controllers.Main;
import Presenters.PresenterHelperCommands.AttendeeCommands.AddNewFriendCmd;
import Presenters.PresenterHelperCommands.AttendeeCommands.ManuallyAddFriendCmd;
import Presenters.PresenterHelperCommands.Command;
import Presenters.Menus.DynamicMenus.DynamicMenu;

/**
 * A menu that allows a user to add friends to their friend list.
 */
public class AddFriendsMenu extends DynamicMenu {

    private final AttendeeController attendeeController;
    private final Main main;

    /**
     * Constructor for AddFriendsMenu
     * @param main type Main
     */
    public AddFriendsMenu(Main main) {
        super("Add New Friends");
        attendeeController = main.getAttendeeController();
        this.main = main;
    }

    /**
     * Builds command AddNewFriendCmd with necessary info
     * @param title title of menu
     * @param ID of friend
     * @return AddNewFriendCmd command
     */
    @Override
    public Command buildCommand(String title, int ID) {
        return new AddNewFriendCmd(title, ID);
    }

    /**
     * A list of add friend commands built from a list of non-friend users.
     */
    @Override
    public void update() {
        addOption(new ManuallyAddFriendCmd());
        buildOptionsFromHashMap(attendeeController.getNonFriendAttendees(main.getLoginController().getCurrentUserID()));
    }

    /**
     * Gets execution location of menu
     * @return String of execution location
     */
    @Override
    public String executionLocation() {
        return null;
    }

}
