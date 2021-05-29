/*
@layer: Gateway
 */

package Gateways;

import Entities.Conversation;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class ConversationFile implements FileInteractable {

    private final String filepath = "file/conversations.ser";

    /**
     * Save all the conversations to a file
     * Should **only** be use when program shutting down
     * @param objs list of Conversations to be saved
     */
    public void save(ArrayList<Conversation> objs) {
        save(objs, filepath);
    }

    /**
     * load all the previous conversations stored in a file
     * @return a list of Conversations
     */
    public ArrayList<Conversation> load() {
        return (ArrayList<Conversation>) loadList(filepath);
    }
}
