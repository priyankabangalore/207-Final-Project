/*
@layer: Presenter - Menu
 */

package Presenters.Menus.DynamicMenus.AttendeeMenus;

import Controllers.AttendeeController;
import Controllers.Main;
import Controllers.MenuAdapters.DynamicMenuOperatorHub;
import Presenters.PresenterHelperCommands.AttendeeCommands.RemoveFriendCmd;
import Presenters.PresenterHelperCommands.Command;
import Presenters.Menus.DynamicMenus.DynamicMenu;

import java.util.Optional;

/**
 * A menu that allows a user to add friends to their friend list.
 */
public class RemoveFriendMenu extends DynamicMenu {

    private final AttendeeController attendeeController;
    private final Main main;

    /**
     * Constructor for AddFriendsMenu
     * @param main type Main
     */
    public RemoveFriendMenu(Main main) {
        super("Remove A Friend");
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
        return new RemoveFriendCmd(title, ID);
    }

    /**
     * A list of add friend commands built from a list of non-friend users.
     */
    @Override
    public void update() {
        buildOptionsFromHashMap(attendeeController.getFriendsDict(main.getLoginController().getCurrentUserID()));
    }

    /**
     * Gets execution location of menu
     * @return String of execution location
     */
    @Override
    public String executionLocation() {
        return "Dynamic Menu Operator Hub";
    }

    /**
     * Executes a menu within this class.
     * @param dynamicMenuOperatorHub the menu to be executed.
     * @return optional null
     */
    @Override
    public Optional<Command> execute(DynamicMenuOperatorHub dynamicMenuOperatorHub){
        dynamicMenuOperatorHub.removeFriends(this);
        return Optional.empty();
    }

}
