package Gateways;

import Entities.Date;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class DateFile implements FileInteractable {

    private final String filepath = "file/date.ser";

    /**
     * Save all the conversations to a file
     * Should **only** be use when program shutting down
     * @param objs list of Conversations to be saved
     */
    public void save(ArrayList<Date> objs) {
        save(objs, filepath);
    }

    /**
     * load all the previous conversations stored in a file
     * @return a list of Conversations
     */
    public ArrayList<Date> load() {
        return (ArrayList<Date>) loadList(filepath);
    }
}