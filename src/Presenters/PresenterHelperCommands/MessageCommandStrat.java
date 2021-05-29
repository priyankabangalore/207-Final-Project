package Presenters.PresenterHelperCommands;

import Controllers.Main;
import Controllers.MenuAdapters.ConversationViewer;
import Presenters.PresenterHelperCommands.Command;

import java.util.HashMap;
import java.util.Optional;

public abstract class MessageCommandStrat extends CommandStrat {


    protected Main main = null;

    /**
     * Message command
     */
    public MessageCommandStrat(Main main){
        super(main);
        this.main = main;
    }

    public MessageCommandStrat(String title, int entityID) {
        super(title, entityID);
    }

    /**
     * Gets command
     * @param title
     * @param ID
     */
    public abstract Command getCommand(String title, int ID);

    /**
     * Gets a map used by the update method in a dynamic menu
     * @param conversationID ID of convo
     * @param CONVO_LENGTH length of convo
     * @return hashmap
     */
    public abstract HashMap<String, Integer> getMap(int conversationID, int CONVO_LENGTH);

    /**
     * Gets location of the command
     * @return location of command as a String
     */
    @Override
    public String executionLocation() {
        return "Dynamic Menu Operator Hub";
    }

    /**
     * Executes a command within this class.
     * @param target the command to be executed.
     */
    @Override
    public abstract Optional<Command> execute(ConversationViewer target);
}
