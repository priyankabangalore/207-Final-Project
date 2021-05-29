/*
@layer: Controller
 */

package Controllers.MenuAdapters;

import Controllers.AttendeeController;
import Controllers.LoginController;
import Controllers.Main;
import Presenters.Menus.DynamicMenus.DynamicMenu;
import Presenters.PresenterHelperCommands.Command;

import java.util.Optional;

/**
 * An operator that displays a menu that allows the user to remove a friend.
 */
public class RemoveFriendOperator extends DynamicMenuOperator {

    private final AttendeeController attendeeController;
    private final LoginController loginController;
    private final DynamicMenu removeFriendMenu;

    /**
     * Constructor for AddNewFriendOperator
     * @param main: type Main
     */
    public RemoveFriendOperator(Main main, DynamicMenu removeFriendMenu) {
        super(main);
        attendeeController = main.getAttendeeController();
        loginController = main.getLoginController();
        this.removeFriendMenu = removeFriendMenu;
    }

    /**
     * Runs the menu displaying the means by which to remove friends.
     */
    public void removeFriends(){
        dynamicRun(removeFriendMenu, "Back");
    }

    /**
     * Calls the main method to remove a friend.
     * @param title
     * @param friendID friend to be removed
     */
    public void removeFriend(String title, int friendID){
        main.getAttendeeController().removeFriend(friendID, loginController.getCurrentUserID());
    }

    /**
     * Executes a command within this class.
     * @param command the command to be executed.
     * @return a command to be executed in another part of the program
     */
    @Override
    public Optional<Command> executeCmd(Command command){return command.execute(this);}
}

