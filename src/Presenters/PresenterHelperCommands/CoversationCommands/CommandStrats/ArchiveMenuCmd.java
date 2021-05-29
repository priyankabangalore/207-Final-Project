package Presenters.PresenterHelperCommands.CoversationCommands.CommandStrats;

import Controllers.Main;
import Controllers.MenuAdapters.ConversationViewer;
import Controllers.MenuAdapters.DynamicMenuOperatorHub;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.MessageCommandStrat;

import java.util.HashMap;
import java.util.Optional;

public class ArchiveMenuCmd extends MessageCommandStrat {

    public ArchiveMenuCmd() {
        super(null);
        setTitle("Archive Messages");
    }

    public ArchiveMenuCmd(Main main) {
        super(main);
    }

    public ArchiveMenuCmd(String title, int entityID) {
        super(title, entityID);
    }

    @Override
    public Command getCommand(String title, int ID) {
        return new ArchiveMenuCmd(title, ID);
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
        target.archiveMessage(entityID);
        return Optional.empty();
    }

    @Override
    public Optional<Command> execute(DynamicMenuOperatorHub target){
        target.runArchive();
        return Optional.empty();
    }
}
