/*
@layer: Use Case
@collaborators: Room Entity
 */

package UseCases;
import Entities.*;

import java.util.*;

public class VenueManager {
    private final ArrayList<Room> Rooms;
    private final ArrayList<Integer> Ids;
    private final int maxCapacity = 800;
    private final int open = 9; // open time of rooms
    private final int close = 17; // close time of rooms

    /**
     * Constructor for VenueManager
     * @param rooms: list of all Rooms in system
     * @param ids: list of all Ids of Rooms in system
     */
    public VenueManager(ArrayList<Room> rooms, ArrayList<Integer> ids) {
        this.Rooms = rooms;
        this.Ids = ids;
    }

    /**
     * Get the room name using the ID of the room
     * @author Stephanie
     * @param roomID ID of the room you're reaching
     * @return the name of the room
     */
    public String getRoomName(int roomID) {
        Room room = getRoomObject(roomID);
        if (room == null) {
            return "Room does not exist.";
        }
        return room.getName();
    }

    /**
     * Get the IDs of all the rooms
     * @author Stephanie
     * @return list of integers of room IDs
     */
    public ArrayList<Integer> getIds() {
        return Ids;
    }

    /**
     * Get all the Rooms stores in this manager
     * @author Stephanie
     * @return a unique ID to be used when creating new rooms
     */
    public int newID(){
        return Ids.size() +1;
    }

    /**
     * Get a list of all the room events
     * @author Stephanie
     * @return a list of all the room objects
     */
    public ArrayList<Room> getRooms() {
        return Rooms;
    }

    /**
     * Schedule a one-speaker event in a room at a certain time. Validated that the room and time is free for an event to be scheduled.
     * @author Stephanie
     * @param RoomId the room that is being scheduled in
     * @param eventId the event that is being scheduled
     * @param startTime the time that the event is being scheduled to start at
     * @param date the date of the event to be added
     */
    //
    public void setEvent(int RoomId, int eventId, int startTime, int date){ //1 hour
        Room r = getRoomObject(RoomId);
        if (r != null){
            r.setEvent(startTime, eventId, date);
        }
    }

    /**
     * Schedule a party/panel in a room within a time period. Validates that the room and time slot is free for a party/panel to be scheduled.
     * @author Priyanka
     * @param roomID the room that is being scheduled in
     * @param eventID the party/panel that is being scheduled
     * @param startTime the time that the party/panel is being scheduled to start at
     * @param endTime time the party/panel is scheduled to end at
     * @param day the day that the party/panel event is scheduled for
     */
    public void setPanelorPartyEvent(int roomID, int eventID, int startTime, int endTime, int day)
    {
        Room r = getRoomObject(roomID);
        if (r != null) {
            r.setEventWithinTimePeriod(startTime, endTime, eventID, day);
        }

    }

    /**
     * Get the room object using the rooms ID
     * @author Stephanie
     * @param roomId the id of the room that is being looked for
     * @return the room entity object
     */
    public Room getRoomObject(int roomId) {
        for (Room room : Rooms) {
            if (room.getId() == roomId) {
                return room;
            }
        }
        return null;
    }

    /**
     * Get the maximum capacity for the room with the given ID
     * @param roomID ID for the room who's capacity is required
     * @return the maximum capacity for the room with the given ID
     */
    public int getRoomCapacity(int roomID) {
        Room room = getRoomObject(roomID);
        if (room == null) {
            return 0;
        }
        return room.getCapacity();
    }

    /**
     * Create a new room
     * @author Stephanie
     * @param roomname name of the room
     * @param prop the properties of this room
     */
    public void createRoom(String roomname, ArrayList<String> prop, Collection<Integer> existingDays){
        int roomid = newID();
        Room newRoom = new Room(roomname, roomid, open, close, maxCapacity, prop, existingDays);
        Rooms.add(newRoom);
        Ids.add(roomid);
    }

    /**
     * Validate that the room name has not already been used
     * @author Stephanie
     * @param name new name of the room
     * @return true if the name is free for use (unique name)
     */
    public boolean ValidateUniqueName(String name){
        for (Room room : Rooms) {
            if (room.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validate if a given time is available to schedule a new event
     * @author Stephanie
     * @param time time that is being checked
     * @param roomId the room you would like to schedule an event at
     * @param date the date for which the room's availability is being validated
     * @return true if the time is available for scheduling a new event at
     */
    public boolean validateSchedule(int time, int roomId, int date){
       Room room = getRoomObject(roomId);
       if (room == null) {
           return false;
       }
       return room.validateTime(time, date);
    }

    /**
     * Validate if a given start and finish times are available to schedule a panel or party
     * @author Priyanka
     * @param startTime start time of event
     * @param endTime end time of event
     * @param roomID ID of the room
     * @param date the date for which the validity of the event is checked
     * @return true iff the times are available for scheduling a new event at
     */
    public boolean validateMultiHourSchedule(int startTime, int endTime, int roomID, int date) //2-3 hours
    {
        for (int time = startTime;time <= endTime;time++) {
            if (!validateSchedule(time, roomID, date)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes an event from the rooms using the event ID
     * @param eventID id of the event you are trying to remove
     */
    public void deleteEvent(int eventID){
        for (Room room : Rooms) {
            room.removeEvent(eventID);
        }
    }

    /**
     * Get the schedule of the room based off the room ID
     * @author Stephanie
     * @param roomID The ID of the room of which you are finding the schedule for
     * @return Hashmap(K, V), K: Integer Time, V: Integer EventID
     */
    public LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> getRoomSchedule(int roomID) {
        Room currRoom = getRoomObject(roomID);
        if (currRoom != null) {
            return currRoom.getSchedule2();
        }
        return new LinkedHashMap<>();
    }

    /**
     * Creates a schedule of all the rooms using getRoomSchedule in VenueManager, which looks at individual rooms at a time
     * @author Stephanie
     * @return hashmap(K,V), K: String with Room name, times, and events. V: RoomID
     */
    public LinkedHashMap<String, Integer> getRoomsSchedulesMap() {
        LinkedHashMap<String, Integer> schedule = new LinkedHashMap<>();
        for (Room rm : Rooms) {
            Integer id = rm.getId();
            schedule.put(oneRoomScheduleString(id), id);

        }
        return schedule;
    }

    /**
     * Get the schedule for a single room in string form
     * @param roomID ID for the room
     * @return the schedule of the room in string form, including date, time, and availability
     */
    private String oneRoomScheduleString(Integer roomID) {
        LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> oneRoom = getRoomSchedule(roomID);
        String rmName = getRoomName(roomID);
        StringBuilder info =  new StringBuilder(rmName + "'s Schedule:" + "\n");

        for(Integer date : oneRoom.keySet()) {
            info.append("    ").append(date);
            for (Integer time : oneRoom.get(date).keySet()) {
                info.append("\n    ").append(toTime(time));
                if (oneRoom.get(oneRoom.get(date).get(time)) == null) {
                    info.append(": Available \n");
                } else {
                    info.append(": Booked \n");
                }
            }
        }
        return info.toString();
    }

    /**
     * Return the room schedule for available rooms given the capacity of the event being created
     * by the organiser
     * @param capacity the capacity of the event being created by the organiser
     * @return room schedule for all available rooms given the required capacity
     */
    public LinkedHashMap<String, Integer> getRoomSchedulesWithCapacity(int capacity) {
        LinkedHashMap<String, Integer> schedule = new LinkedHashMap<>();
        for (Room rm : Rooms) {
            Integer id = rm.getId();
            if (getRoomCapacity(id) >= capacity) {
                schedule.put(oneRoomScheduleString(id), id);
            }
        }
        return schedule;
    }

    /**
     * Validating that the room the user wants to schedule in exists
     * @param id room id of the room the user wants to use
     * @return True if the room is valid/ exists.
     */
    public boolean validateRoomExists(int id){
        for (int i=0; i<getRooms().size(); i++){
            if (getRooms().get(i).getId() == id){
                return true;
            }
        }
        return false;
    }

    /**
     * Return the properties of the room with the given ID
     * @param roomID ID of the room
     * @return properties of the room
     */
    public ArrayList<String> getProperties(Integer roomID) {
        Room room = getRoomObject(roomID);
        if (room == null) {
            return new ArrayList<>();
        }
        return room.getProperties();
    }


    /**
     * Check if room capacity is valid
     * @author Priyanka
     * @param roomID ID of the room
     * @param capacity room capacity
     * @return true iff room capacity is greater than or equal to event capacity
     */
    public boolean validateRoomCapacity(int roomID, int capacity) {
        return getRoomObject(roomID).getCapacity() >= capacity;
    }


    /**
     * Add Property to a room
     * @author Steph
     * @param roomID the room being edited
     * @param prop the prop being added
     */
    public void addProperty(int roomID, String prop){
        Room rm = getRoomObject(roomID);
        rm.addProperty(prop);
    }

    /**
     * A hashmap with a string of all the rooms that have valid properties and room caps (for the user to set their event in a room)
     * @author: steph
     * @param props the properties the organizer has set in the event
     * @param minCap the min cap of the event
     * @return A HashMap</String,/Integer> String = the rooms, Integer = nothing important
     */
    public LinkedHashMap<String, Integer> getValidPropertyRooms(ArrayList<String> props, int minCap) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        for (Room rm : Rooms) {
            if (rm.hasProps(props) && minCap <= rm.getCapacity()) {
                map.put(rm.getName(), rm.getId());
            }
        }
        return map;
    }

    /**
     * Set properties in room
     * @author Priyanka
     * @param roomID the ID of the room the properties are being added to
     * @param props the properties to be added
     */
    public void setProperties(int roomID, String props) {
        addProperty(roomID, props);
    }

    /**
     * Properly formats the time (am/pm)
     * @param time time as an int
     * @return time in proper format (am/pm)
     */
    public String toTime(int time){
        if (time < 12){
            return time + "am";
        }
        else if(time == 12){
            return 12 + "pm";
        }
        else {
            return time - 12 + "pm";
        }
    }

    /**
     * Gets the commonly available times between a room and speaker given an eventID
     * @author Priyanka
     * @param eventID ID of event
     * @param EM EventManager
     * @param SM SpeakerManager
     * @return Hashmap(K,V), where K: time as a string, V: time as an int
     */
    public HashMap<String, Integer> getAvailableTimesGivenEventID(int eventID, EventManager EM, SpeakerManager SM) {
        Event event = EM.getEvent(eventID);

        if (event == null) {
            return new HashMap<>();
        }
        Integer date = event.getDate();
        int span = EM.getEventSpan(eventID);

        HashMap<String, Integer> times = new HashMap<>();
        HashMap<Integer, Integer> roomSchedule = getRoomSchedule(EM.getEventRoom(eventID)).get(date);

        if(span==1) //talk
        {
            TalkEvent e = (TalkEvent) EM.getEvent(eventID);
            int speakerID = e.getSpeaker();
            for (Integer time: roomSchedule.keySet()) {
                if (roomSchedule.get(time) == null && SM.checkSpeakerIsFree(speakerID, time, date)) {
                    String timeString = toTime(time);
                    times.put(timeString, time);
                }
            }
        }
        else if(span==2 || span==3) //panel
        {
            PanelEvent e = (PanelEvent) EM.getEvent(eventID);
            ArrayList<Integer> speakerIDs = e.getSpeakers();
            for(Integer s : speakerIDs)
            {
                for (Integer time: roomSchedule.keySet()) {
                    if (roomSchedule.get(time) == null && SM.checkSpeakerIsFree(s, time, date)) {
                        String timeString = toTime(time);
                        times.put(timeString, time);
                    }
                }
            }
        }
        else if(span==4 || span==5) //party
        {
            for (Integer time: roomSchedule.keySet()) {
                if (roomSchedule.get(time) == null) {
                    String timeString = toTime(time);
                    times.put(timeString, time);
                }
            }
        }
        return times;
    }

    /**
     * Updates the empty room schedule
     * @param day day of event
     */
    public void updateEmptySchedule(Integer day){
        for (Room rm : Rooms){
            rm.addDate(day);
        }
    }

    /**
     * Reschedules an event
     * @param day day to reschedule event to
     * @param startTime start time of event
     * @param endTime end time of event
     * @param roomID ID of room
     * @param eventID ID of event
     */
    public void rescheduleEvent(int roomID, int eventID, int startTime, int endTime, int day) {
        deleteEvent(eventID);
        setPanelorPartyEvent(roomID, eventID, startTime, endTime, day);
    }
}
