package Presenters.PresenterHelperCommands.AdminUserCommands;

import Controllers.MenuAdapters.DeleteConversationOperator;
import Controllers.MenuAdapters.DynamicMenuOperatorHub;
import Presenters.PresenterHelperCommands.Command;

import java.util.Optional;

public class DeleteConversationCmd extends Command {
    /**
     * Sends String to Command() to print
     */
    public DeleteConversationCmd(){super("Delete a Conversation");}
    /**
     * Constructor for AddNewFriendCmd
     * @param conversationID ID of conversation
     * @param title title of command
     */
    public DeleteConversationCmd(String title, int conversationID){super(title, conversationID);
    }
    /**
     * Executes a command within this class.
     * @param dco the command to be executed.
     * @return null optional
     */
    public Optional<Command> execute(DeleteConversationOperator dco){
        dco.deleteConversation(getTitle(), entityID);
        return Optional.empty();
    }
    /**
     * Executes a command within this class.
     * @param target the command to be executed.
     * @return null optional
     */
    public Optional<Command> execute(DynamicMenuOperatorHub target){
        target.runDeleteConversation();
        return Optional.empty();
    }
    /**
     * Gets location of the command
     * @return location of command as a String
     */
    @Override
    public String executionLocation() {
        return "Dynamic Menu Operator Hub";
    }
}
