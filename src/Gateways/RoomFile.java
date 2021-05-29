/*
@layer: Gateway
 */

package Gateways;

import Entities.Room;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class RoomFile implements FileInteractable {

    private final String filepath = "file/rooms.ser";
    private final String idpath = "file/roomids.ser";

    /**
     * Save all the Rooms to a file
     * Should **only** be use when program shutting down
     * @param objs list of Rooms to be saved
     */
    public void save(ArrayList<Room> objs) {
        save(objs, filepath);
    }

    /**
     * load all the previous Rooms stored in a file
     * @return a list of Rooms
     */
    public ArrayList<Room> load() {
        return (ArrayList<Room>) loadList(filepath);
    }

    /**
     * Save all the RoomIDs to a file
     * Should **only** be use when program shutting down
     * @param objs list of RoomIDs to be saved
     */
    public void saveID(ArrayList<Integer> objs) {
        save(objs, idpath);
    }

    /**
     * load all the previous RoomIDs stored in a file
     * @return a list of RoomIDs
     */
    public ArrayList<Integer> loadID() {
        return (ArrayList<Integer>) loadList(idpath);
    }
}
