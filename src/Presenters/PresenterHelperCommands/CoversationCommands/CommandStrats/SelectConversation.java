package Presenters.PresenterHelperCommands.CoversationCommands.CommandStrats;

import Controllers.Main;
import Controllers.MenuAdapters.ConversationViewer;
import Presenters.PresenterHelperCommands.Command;
import Presenters.PresenterHelperCommands.CommandStrat;
import Presenters.PresenterHelperCommands.ExitCmd;

import java.util.HashMap;
import java.util.Optional;

public class SelectConversation extends CommandStrat {

    public SelectConversation(Main main) {
        super(main);
    }

    public SelectConversation(String title, int entityID) {
        super(title, entityID);
    }

    @Override
    public Command getCommand(String title, int ID) {
        return new SelectConversation(title, ID);
    }

    @Override
    public HashMap<String, Integer> getMap(int entityID) {
        return main.getConversationController().getConvos(main.getLoginController().getCurrentUserID());
    }

    @Override
    public String executionLocation() {
        return "Conversation Viewer";
    }

    @Override
    public Optional<Command> execute(ConversationViewer target) {
        target.setSelectedConvoID(entityID);
        return Optional.of(new ExitCmd());
    }
}
