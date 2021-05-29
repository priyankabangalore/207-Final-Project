/*
@layer: Controller
 */

package Controllers.MenuAdapters;

import Controllers.ConversationController;
import Controllers.LoginController;
import Controllers.Main;
import Presenters.Menus.DynamicMenus.ConversationMenus.MessageHandler;
import Presenters.Menus.DynamicMenus.StrategyDynamicMenu;
import Presenters.PresenterHelperCommands.Command;
import Presenters.Menu;
import Presenters.Menus.DynamicMenus.DynamicMenu;
import Presenters.PresenterHelperCommands.CoversationCommands.CommandStrats.ArchiveMenuCmd;
import Presenters.PresenterHelperCommands.CoversationCommands.CommandStrats.MarkUnreadCmd;
import Presenters.PresenterHelperCommands.CoversationCommands.CommandStrats.SelectConversation;
import Presenters.PresenterHelperCommands.CoversationCommands.CommandStrats.UnarchiveCmd;

import java.util.HashMap;
import java.util.Optional;


/**
 * A class that allows the user to view and send messages to their conversations.
 */
public class ConversationViewer extends DynamicMenuOperator {
    protected Menu mainMenu = null;
    private final int CONVO_LENGTH = 50;
    private final ConversationController cc;
    private int selectedConvoID;

    /**
     * Constructor for ConversationViewer
     * @param main: type Main
     */
    public ConversationViewer(Main main) {
        super(main);
        cc = main.getConversationController();
    }

    /**
     * Constructor for ConversationViewer
     * @param menu: type Menu
     * @param main: type Main
     */
    public ConversationViewer(Menu menu, Main main) {
        super(main);
        this.mainMenu = menu;
        cc = main.getConversationController();
    }

    /**
     * Opens conversations to view
     */
    public void viewConversations() {
        DynamicMenu menu = (DynamicMenu) mainMenu;
        dynamicRun(menu, "Back");
    }

    /**
     * marks convo as unread
     */
    public void runMarkUnread(){
        if(notBack(selectConversation())){
            showMarkUnreadMenu(selectedConvoID);
        }
    }

    /**
     * View archived convos
     */
    public void runArchive(){
        if(notBack(selectConversation())){
            showArchiveMenu(selectedConvoID);
        }
    }

    /**
     * View unarchived convos
     */
    public void runUnarchive(){
        if(notBack(selectConversation())){
            showUnarchiveMenu(selectedConvoID);
        }
    }

    public boolean notBack(Optional<Command> cmd){
        return !cmd.isPresent() || !cmd.get().getTitle().equals("Back");
    }


    //Below methods implement strategy design pattern.

    /**
     * Select Conversations
     */
    public Optional<Command> selectConversation(){
        DynamicMenu selectConvo = new StrategyDynamicMenu(main, new SelectConversation(main), 0,
                "Conversation Viewer");
        return runUntilBackOrExit(selectConvo);
    }

    /**
     * Show unread menu
     */
    public void showMarkUnreadMenu(int convoID){
        MessageHandler strategyDynamicMenuMenu = new MessageHandler(main, new MarkUnreadCmd(main), convoID, CONVO_LENGTH);
        runUntilBackOrExit(strategyDynamicMenuMenu);
    }

    /**
     * show archive menu
     */
    public void showArchiveMenu(int convoID){
        MessageHandler strategyDynamicMenuMenu = new MessageHandler(main, new ArchiveMenuCmd(main), convoID, CONVO_LENGTH);
        runUntilBackOrExit(strategyDynamicMenuMenu);
    }

    /**
     * show unarchived menu
     */
    public void showUnarchiveMenu(int convoID){
        MessageHandler strategyDynamicMenuMenu = new MessageHandler(main, new UnarchiveCmd(main), convoID, CONVO_LENGTH);
        runUntilBackOrExit(strategyDynamicMenuMenu);
    }

    /**
     * Displays a conversation to the user, and marks all messages as read.
     * @param conversationID the conversation to view
     */
    public void viewConvo(int conversationID){
        ConversationController cc = main.getConversationController();
        showPrompt("Enter \"Back\" to go back to view conversations.\nClick enter to send a message.");
        showPrompt(cc.getStringRep(conversationID));
        StringBuilder conversation = new StringBuilder();
        HashMap<String, Integer> messagesHashUnsorted = cc.getRecentMessagesUnarchived(conversationID, CONVO_LENGTH);
        HashMap<Integer, String> messagesHash = new HashMap<>();
        for(HashMap.Entry<String, Integer> entry : messagesHashUnsorted.entrySet()){
            messagesHash.put(entry.getValue(), entry.getKey());
        }
        for (Integer messageID : messagesHash.keySet()){
            Integer a = cc.getSenderID(messageID);
            conversation.append(main.getAttendeeController().getUsername(a));
            conversation.append(":");
            conversation.append(messagesHash.get(messageID));
            conversation.append("\n");
            cc.markAsRead(messageID, main.getLoginController());
        }
        showPrompt(conversation.toString());
    }

    /**
     * Opens a given conversation, allows the user to send messages.
     * @param conversationID the conversation to be opened
     */
    public void openConvoCanMessage(int conversationID) {
        LoginController lc = main.getLoginController();
        viewConvo(conversationID);
        String input;
        input = scanner.nextLine();
        while (!input.equals("Back")) {
            if(input.equals("")){
                continue;
            }
            cc.sendMessage(conversationID, input, lc.getCurrentUserID());
            input = scanner.nextLine();
        }
    }


    /**
     * Opens a given conversation, does not allow  the user to send messages.
     * @param conversationID the conversation to be opened
     */
    public void openConvo(int conversationID) {
        ConversationController controller = main.getConversationController();
        showPrompt("Enter \"Back\" to go back to view conversations.\nClick enter to send a message.");
        viewConvo(conversationID);
        String input = "awdadssdfad";
        while (!input.equals("Back")) {
            if(input.equals("")){
                continue;
            }
            input = scanner.nextLine();
            if (!input.equals("Back")) {
                showPrompt("You do not have permission to message in this conversation.");
                menuPresenter.showPrompt("Press Enter to continue.");
                scanner.nextLine();
            }
        }
    }


    /**
     * Archive the message with the given ID
     * @param messageID ID of the message
     */
    public void archiveMessage(int messageID) {
        cc.archiveMessage(messageID);
    }

    /**
     * Unarchive the message with the given ID
     * @param messageID ID of the message
     */
    public void unarchiveMessage(int messageID) {
        cc.unarchiveMessage(messageID);
    }


    /**
     * Mark the message with the given ID as unread if the current user is not the
     * sender of the message. If the current user is the sender of the message, do
     * nothing.
     * @param messageID ID of the message
     */
    public void markAsUnread(int messageID) {
        cc.markAsUnread(messageID, main.getLoginController());
    }

    /**
     * Executes a command within this class.
     * @param command the command to be executed.
     * @return a command to be executed in another part of the program
     */
    @Override
    public Optional<Command> executeCmd(Command command){return command.execute(this);}

    /**
     * Selects convo ID for a new convo
     */
    public void setSelectedConvoID(int newConvoID){
        selectedConvoID = newConvoID;
    }
}
