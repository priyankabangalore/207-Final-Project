/*
@layer: Controller
 */

package Controllers.MenuAdapters;

import Controllers.Main;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.CoversationCommands.BuildConverationCmd;
import Presenters.Menu;
import Presenters.Menus.DynamicMenus.DynamicMenu;

import java.util.ArrayList;
import java.util.Optional;

/**
 * A class that allows the user to build a new conversation using a list of ways to add users.
 */
public class ConversationBuilder extends DynamicMenuOperator {
    protected final Menu mainMenu;
    private final ArrayList<Integer> usersToAdd = new ArrayList<>();
    private String conversationName;
    private boolean viable = false;

    /**
     * Constructor for ConversationBuilder
     * @param main: type Main
     * @param menu: type Menu
     */
    public ConversationBuilder(Menu menu, Main main) {
        super(main);
        this.mainMenu = menu;
        this.usersToAdd.add(main.getLoginController().getCurrentUserID());
    }

    /**
     * The first command called upon conversation building. Runs the available methods of adding participants until
     * the user indicates that their conversation is complete.
     *
     * @return A command to build the newly built conversation.
     */
    public Optional<Command> buildConversation() {
        // Runs each of the options presented to the user in their "New Conversation" option
        run(mainMenu, "Finalise / Exit");
        if (viable) {
            // Set the name of the conversation
            this.setName();
            //This will be returned when the conversation is complete.
            return Optional.of(new BuildConverationCmd(conversationName, usersToAdd));
        }
        return Optional.empty();
    }

    /**
     * Adds a users to the list of users to be added to this conversation. Called by AddUserCmd.
     *
     * @param userID userID to be added
     */
    public void addUserToConvo(int userID) {
        if (usersToAdd.contains(userID)) {
            showPrompt("User already in conversation.");
        } else {
            this.usersToAdd.add(userID);
            showPrompt("User Added");
            viable = true;
        }
        menuPresenter.showPrompt("Press Enter to continue.");
        scanner.nextLine();
    }

    /**
     * Prompts the user to set a name for this conversation. Called by FinaliseCmd
     */
    public void setName() {
        showPrompt("Enter a name for this conversation");
        this.conversationName = scanner.nextLine();
    }

    /**
     * Updates and runs a user-adding menu until the back command is called.
     *
     * @param menu the menu to be run.
     */
    public void addUsers(DynamicMenu menu) {
        menu.update();
        dynamicRun(menu, "Back");
    }

    /**
     * Executes a command within this class.
     * @param command the command to be executed.
     * @return a command to be executed in another part of the program
     */
    @Override
    public Optional<Command> executeCmd(Command command){return command.execute(this);}
}
