/*
@layer: Gateway
 */

package Gateways;

import Entities.Message;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class MessageFile implements FileInteractable {

    private final String filepath = "file/messages.ser";
    private final String idpath = "file/messageids.ser";

    /**
     * Save all the conversations to a file
     * Should **only** be use when program shutting down
     * @param objs list of Conversations to be saved
     */
    public void save(ArrayList<Message> objs) {
        save(objs, filepath);
    }

    /**
     * load all the previous conversations stored in a file
     * @return a list of Conversations
     */
    public ArrayList<Message> load() {
        return (ArrayList<Message>) loadList(filepath);
    }

    /**
     * Save all the conversationIDs to a file
     * Should **only** be use when program shutting down
     * @param objs list of ConversationIDs to be saved
     */
    public void saveID(ArrayList<Integer> objs) {
        save(objs, idpath);
    }

    /**
     * load all the previous conversationIDs stored in a file
     * @return a list of ConversationIDs
     */
    public ArrayList<Integer> loadID() {
        return (ArrayList<Integer>) loadList(idpath);
    }
}
