/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands.CoversationCommands;

import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.ConversationViewer;

import java.util.Optional;

/**
 * A command issued by the user that opens a pre-existing conversation without enabling two way conversation.
 */
public class OpenConversationCmd extends Command {

    /**
     * Lets the user open a conversation to see convo
     * @param title title of command
     * @param conversationID ID of the conversation
     */
    public OpenConversationCmd(String title, int conversationID) {
        super(title, conversationID);
    }

    /**
     * Gets location of the command
     * @return location of command as a String
     */
    @Override
    public String executionLocation() {
        return "Conversation Viewer";
    }

    /**
     * Executes the openConvo function to see conversations
     * @param viewer the conversation you're viewing
     * @return The Command to execute
     */
    public Optional<Command> execute(ConversationViewer viewer) {
        viewer.openConvo(entityID);
        return Optional.empty();
    }
}
