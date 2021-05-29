/*
@layer: Presenter - Command
 */


package Presenters.PresenterHelperCommands.CoversationCommands;

import Presenters.PresenterHelperCommands.Command;
import Controllers.MenuAdapters.ConversationViewer;

import java.util.Optional;

/**
 * Opens a conversation, allows the user to send messages.
 */
public class OpenDialogueConversationCmd extends Command {

    /**
     * Opens the dialogue conversation
     * @param title Title of command
     * @param conversationID ID of the conversation
     */
    public OpenDialogueConversationCmd(String title, int conversationID) {
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
     * Calls conversation view to open a convo with the ability to message
     * @param viewer
     * @return null optional
     */
    public Optional<Command> execute(ConversationViewer viewer) {
        viewer.openConvoCanMessage(entityID);
        return Optional.empty();
    }
}
