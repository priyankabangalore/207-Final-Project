package Gateways;

import Entities.Request;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class RequestFile implements FileInteractable{

    private final String filepath = "file/requests.ser";
    private final String idpath = "file/requestsid.ser";


    /**
     * Save all the Request to a file
     * Should **only** be use when program shutting down
     * @param objs list of Request to be saved
     */
    public void save(ArrayList<Request> objs) {
        save(objs, filepath);
    }

    /**
     * load all the previous Request stored in a file
     * @return a list of Request
     */
    public ArrayList<Request> load() {
        return (ArrayList<Request>) loadList(filepath);
    }

    /**
     * Save all the RequestIDs to a file
     * Should **only** be use when program shutting down
     * @param objs list of RequestIDs to be saved
     */
    public void saveID(ArrayList<Integer> objs) {
        save(objs, idpath);
    }

    /**
     * load all the previous RequestIDs stored in a file
     * @return a list of RequestIDs
     */
    public ArrayList<Integer> loadID() {
        return (ArrayList<Integer>) loadList(idpath);
    }
}
