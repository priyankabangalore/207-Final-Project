/*
@layer: Controller
@collaborators: VenueManager, EventManager
 */

package Controllers;

import UseCases.DateManager;
import UseCases.VenueManager;
import UseCases.EventManager;

import java.util.*;


public class VenueController {
    private final VenueManager MasterVenueManager;
    private final EventManager MasterEventManager;
    private final DateManager DM;

    public ArrayList<String> AVAILABLE_PROPERTIES =
            new ArrayList<>(Arrays.asList("Tomatoes to Throw at Bad Speakers",
            "Tables", "Chairs", "Water Dispenser", "Projector",
            "Microphone", "Snacks"));

    /**
     * Constructor for VenueController
     * @param em: EventManager instance
     * @param vm: VenueManager instance
     */
    public VenueController(VenueManager vm, EventManager em, DateManager dm) {
        this.MasterVenueManager = vm;
        this.MasterEventManager = em;
        this.DM = dm;
    }

    /**
     * Creates a new room
     * @author Stephanie
     * @param name name of the room you'd like to create, automatically sets the open/close time
     * @param properties the properties for the new room
     */
    public void newRoom(String name, ArrayList<String> properties){
        if (MasterVenueManager.ValidateUniqueName(name)) {
            MasterVenueManager.createRoom(name, properties, DM.getDatesDic().values());
        }
    }

    /**
     * Set an event in the room, ensures that the time and room are available
     * @author Stephanie
     * @param EventID the id of the event you are scheduling
     * @param RoomID the id of the room that you want to schedule in
     * @param time the time you would like to schedule at
     * @param date the date at which the event is being set
     */
    public void setEventinRoom(int EventID, int RoomID, int time, int date){

        MasterVenueManager.setEvent(RoomID, EventID, time, date);

    }

    /**
     * Set a panel in the room, ensures that the times and room are available
     * @author Priyanka
     * @param eventID the id of the panel you are scheduling
     * @param roomID the id of the room that you want to schedule in
     * @param startTime the start time to schedule at
     * @param endTime the end time of event
     * @param date the date at which the panel is being set
     */
    public void setPanelorPartyinRoom(int eventID, int roomID, int startTime, int endTime, int date){
        MasterVenueManager.setPanelorPartyEvent(roomID, eventID, startTime, endTime, date);
    }

    /**
     * Validate that the room with the given ID exists
     * @param id room ID
     * @return true iff the room is a registered room
     */
    public boolean validateRoomExists(int id) {
        return MasterVenueManager.validateRoomExists(id);
    }

    /**
     * Gets all available properties
     * @return hashmap, where K: property, V: int
     */
    public HashMap<String, Integer> getAllPropertiesDict(){
        HashMap<String, Integer> allPropDict = new HashMap<>();
        for (String property: AVAILABLE_PROPERTIES){
            allPropDict.put(property, AVAILABLE_PROPERTIES.indexOf(property));
        }
        return allPropDict;
    }

    /**
     * Set properties in a room
     * @param roomID room ID
     * @param properties properties to be set
     * @return true iff the properties have been set
     */
    public boolean setRoomProperties(int roomID, ArrayList<String> properties) {
        for (String s : properties) {
            if (!MasterVenueManager.getProperties(roomID).contains(s)) {
                MasterVenueManager.setProperties(roomID, s);
                return true;
            }
        }
     return false;
    }
}