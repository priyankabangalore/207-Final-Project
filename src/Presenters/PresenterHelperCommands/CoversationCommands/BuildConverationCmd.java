/*
@layer: Presenter - Command
 */

package Presenters.PresenterHelperCommands.CoversationCommands;

import Controllers.Main;
import Presenters.PresenterHelperCommands.Command;

import java.util.ArrayList;
import java.util.Optional;

/**
 * A command issued by the UI that instantiates a new conversation.
 */
public class BuildConverationCmd extends Command {

    private final ArrayList<Integer> users;
    private final String convoTitle;

    /**
     * Build the conversation to be printed for the user
     * @param convoTitle Name of the convo
     * @param users Users joined in the convo
     */
    public BuildConverationCmd(String convoTitle, ArrayList<Integer> users) {
        super("Build Conversation");
        this.users = users;
        this.convoTitle = convoTitle;
    }

    /**
     * Requests that main instruct the UserController to create a new conversation.
     * @param main the main class
     * @return null optional
     */
    public Optional<Command> execute(Main main){
        main.getConversationController().buildConversation(convoTitle, users);
        return Optional.empty();
    }

    /**
     * Gets location of the command
     * @return location of command as a String
     */
    @Override
    public String executionLocation() {
        return "Main";
    }
}
