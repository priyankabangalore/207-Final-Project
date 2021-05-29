/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands.CoversationCommands;

import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.ConversationBuilder;

import java.util.Optional;

/**
 * A command that adds one user to a conversation that is being constructed.
 */
public class AddUserToConvoCmd extends Command {
    /**
     * Add a single user to an event
     * @param username user being added
     * @param userID ID of the user
     */
    public AddUserToConvoCmd(String username, int userID) {
        super(username, userID);
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
        builder.addUserToConvo(entityID);
        return Optional.empty();
    }
}
