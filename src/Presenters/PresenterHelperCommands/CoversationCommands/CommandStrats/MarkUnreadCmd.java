package Presenters.PresenterHelperCommands.CoversationCommands.CommandStrats;

import Controllers.Main;
import Controllers.MenuAdapters.ConversationViewer;
import Controllers.MenuAdapters.DynamicMenuOperatorHub;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.MessageCommandStrat;

import java.util.HashMap;
import java.util.Optional;

public class MarkUnreadCmd extends MessageCommandStrat {

    public MarkUnreadCmd() {
        super(null);
        setTitle("Mark Messages as Unread");
    }

    public MarkUnreadCmd(Main main) {
        super(main);
    }

    public MarkUnreadCmd(String title, int ID) {
        super(title, ID);
    }

    @Override
    public Command getCommand(String title, int ID) {
        return new MarkUnreadCmd(title, ID);
    }

    @Override
    public HashMap<String, Integer> getMap(int entityID) {
        return null;
    }

    @Override
    public HashMap<String, Integer> getMap(int conversationID, int CONVO_LENGTH) {
        return main.getConversationController().getRecentMessagesUnarchived(conversationID, CONVO_LENGTH);
    }

    @Override
    public Optional<Command> execute(ConversationViewer target) {
        target.markAsUnread(entityID);
        return Optional.empty();
    }

    @Override
    public Optional<Command> execute(DynamicMenuOperatorHub target){
        target.runMarkUnread();
        return  Optional.empty();
    }

}
