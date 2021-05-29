/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands.CoversationCommands;

import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.ConversationBuilder;

import java.util.HashMap;
import java.util.Optional;

/**
 * A command that enables the user to add multiple users to a conversation that they're building.
 */
public class AddMultipleUsersCmd extends Command {

    private final HashMap<String, Integer> users;

    /**
     * Add multiple users to a conversation
     * @param title title of command
     * @param users users being added
     */
    public AddMultipleUsersCmd(String title, HashMap<String, Integer> users) {
        super(title);
        this.users = users;
    }

    /**
     * Gets location of the command
     * @return location of command as a String
     */
    @Override
    public String executionLocation() {
        return "Build Conversation";
    }

    /**
     * Calls the add user method using the stored user information.
     * @param builder the builder containing the method to be executed
     * @return null optional
     */
    public Optional<Command> execute(ConversationBuilder builder){

        for(int ID : users.values()) {
            builder.addUserToConvo(ID);
        }
        return Optional.empty();
    }

}
