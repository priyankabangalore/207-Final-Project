/*
@layer: Controller
@collaborators: LoginController, AttendeeController
 */

package Controllers.MenuAdapters;

import Controllers.AttendeeController;
import Controllers.LoginController;
import Controllers.Main;
import Presenters.PresenterHelperCommands.Command;
import Presenters.Menus.DynamicMenus.AttendeeMenus.AddFriendsMenu;

import java.util.Optional;

/**
 * An operator that displays a menu that allows the user to add a new friend.
 */
public class AddNewFriendOperator extends DynamicMenuOperator {

    private final AttendeeController attendeeController;
    private final LoginController loginController;

    /**
     * Constructor for AddNewFriendOperator
     * @param main: type Main
     */
    public AddNewFriendOperator(Main main) {
        super(main);
        attendeeController = main.getAttendeeController();
        loginController = main.getLoginController();
    }

    /**
     * Runs the menu displaying the means by which to add friends.
     */
    public void addNewFriends(){
        dynamicRun(new AddFriendsMenu(main), "Back");
    }

    /**
     * Calls the main method to add a new friend.
     * @param friendID friend to be added
     */
    public void addNewFriend(int friendID){
        main.getAttendeeController().addNewFriend(friendID, loginController.getCurrentUserID());
    }

    /**
     * Prompts the user to enter a valid new friend ID then iff a valid attendee adds that ID as a friend.
     */
    public void addNewFriendFromID(){
        boolean notBack = true;
        String type = "your";
        String input;
        int userID;
        while (notBack) {
            showPrompt("Enter \"Back\" to go back.");
            showPrompt("Enter "+ type + " friends ID:");
            input = scanner.nextLine();
            if (input.equals("Back")){
                notBack = false;
                continue;
            }
            try {
                userID = Integer.parseInt(input);
                if (!attendeeController.isAttendee(userID)) {
                    showPrompt("This user either does not exist, or cannot be added as a friend.");
                    continue;
                }
                boolean res =  attendeeController.addNewFriend(userID, loginController.getCurrentUserID());
                if (!res) {
                    showPrompt("This user is already in you friend list");
                    type = "another";
                }
            }
            catch (NumberFormatException exep) {
                showPrompt("Please enter a number.");
            }
        }
    }

    /**
     * Executes a command within this class.
     * @param command the command to be executed.
     * @return optional null
     */
    @Override
    public Optional<Command> executeCmd(Command command){return command.execute(this);}
}

