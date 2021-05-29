package Presenters.PresenterHelperCommands.CoversationCommands.CommandStrats;

import Controllers.Main;
import Controllers.MenuAdapters.ConversationViewer;
import Controllers.MenuAdapters.DynamicMenuOperatorHub;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.MessageCommandStrat;

import java.util.HashMap;
import java.util.Optional;

public class UnarchiveCmd extends MessageCommandStrat {

    public UnarchiveCmd() {
        super(null);
        setTitle("Unarchive Messages");
    }

    public UnarchiveCmd(Main main) {
        super(main);
    }

    public UnarchiveCmd(String title, int entityID) {
        super(title, entityID);
    }

    @Override
    public Command getCommand(String title, int ID) {
        return new UnarchiveCmd(title, ID);
    }

    @Override
    public HashMap<String, Integer> getMap(int entityID) {
        return null;
    }

    @Override
    public HashMap<String, Integer> getMap(int conversationID, int CONVO_LENGTH) {
        return main.getConversationController().getRecentMessagesArchived(conversationID,CONVO_LENGTH);
    }

    @Override
    public Optional<Command> execute(ConversationViewer target) {
        target.unarchiveMessage(entityID);
        return Optional.empty();
    }

    @Override
    public Optional<Command> execute(DynamicMenuOperatorHub target){
        target.runUnarchive();
        return  Optional.empty();
    }
}

