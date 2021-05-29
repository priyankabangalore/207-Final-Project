package Presenters.Menus.DynamicMenus.ConversationMenus;

import Controllers.Main;
import Presenters.Menus.DynamicMenus.StrategyDynamicMenu;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.MessageCommandStrat;

public class MessageHandler extends StrategyDynamicMenu {

    private final int conversationID;
    private final int CONVO_LENGTH;
    private final MessageCommandStrat mcs;

    public MessageHandler(Main main, MessageCommandStrat messageCommandStrat, int conversationID,
                          int CONVO_LENGTH) {
        super(main, messageCommandStrat, conversationID);
        this.CONVO_LENGTH = CONVO_LENGTH;
        this.conversationID = conversationID;
        mcs = messageCommandStrat;
    }

    /**
     * Builds a command
     * @param title
     * @param ID ID of convo
     * @return command
     */
    @Override
    public Command buildCommand(String title, int ID) {
        return cs.getCommand(title, ID);
    }

    /**
     * Update all the conversations
     */
    @Override
    public void update() {
        buildOptionsFromHashMap(mcs.getMap(conversationID, CONVO_LENGTH));
    }

    /**
     * Gets execution location of menu
     * @return String of execution location
     */
    @Override
    public String executionLocation() {
        return "Conversation Viewer";
    }
}
