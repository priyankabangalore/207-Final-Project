/*
@layer: Controller
@collaborators: EventManager, VenueManager, SpeakerManager
 */

package Controllers;

import UseCases.*;

import java.util.*;


public class OrganizerController extends UserController {

    /**
     * Constructor for OrganizerController
     * @param AM: AttendeeManager instance
     * @param SM: SpeakerManager instance
     * @param OM: OrganizerManager instance
     * @param UM: UserManager instance
     * @param EM: EventManager instance
     * @param VM: VenueManager instance
     * @param RM: RequestManager instance
     * @param DM: DateManager instance
     */
    public OrganizerController(AttendeeManager AM, SpeakerManager SM, OrganizerManager OM, UserManager UM,
                               EventManager EM, VenueManager VM, RequestManager RM, DateManager DM) {
        super(AM, SM, OM, UM, EM, VM, RM, DM);
    }

    /**
     * Creates a schedule of all the rooms using getRoomSchedule in VenueManager, which looks
     * at individual rooms at a time
     * @author Stephanie
     * @return hashmap(K,V), K: String with Room name, times, and events. V: RoomID
     */
    public LinkedHashMap<String, Integer> getRoomSchedules() {
        return VM.getRoomsSchedulesMap();
    }

    /**
     * Return the room schedule for available rooms given the capacity of the event being created
     * by the organiser
     * @param capacity the capacity of the event being created by the organiser
     * @return room schedule for all available rooms given the required capacity
     */
    public LinkedHashMap<String, Integer> getRoomSchedulesWithCapacity(int capacity) {
        return VM.getRoomSchedulesWithCapacity(capacity);
    }

    /**
     * Gets ALL speakers in the system's schedules
     * @author Priyanka
     * @return hashmap(K,V), where K: String with Speaker username, event name, event time. V: speaker ID
     */
    public LinkedHashMap<String, Integer> getSpeakerSchedules(){
        return SM.getSpeakerSchedules(DM);
    }

    /**
     * Adds to date
     * @param date date to modify
     * @param startTime start time on date
     * @param endTime end time on date
     * @return Date String
     */
    private String longDateToString(Integer date, Integer startTime, Integer endTime) {
        String d = date.toString();
        String s = startTime.toString();
        String e = endTime.toString();
        if (d.length() == 3) {
            d = "0" + d;
        }
        if (s.length() == 1) {
            s = "0" + s;
        }
        if (e.length() == 1) {
            e = "0" + e;
        }
        return d + s + e;
    }

    /**
     * Gets the common free time slots between a room and a speaker. (1 speaker)
     * @author Priyanka
     * @param roomID the ID of the room
     * @param speakerID the ID of the speaker
     * @return Hashmap(K,V), where K: time (String), V: time (int) of the common available start times
     */
    public LinkedHashMap<String, Integer> getAvailableTimes(int roomID, int speakerID) {
        ArrayList<Integer> dates = new ArrayList<>(getDatesDict().values());
        LinkedHashMap<String, Integer> availableTimes = new LinkedHashMap<>();
        for (Integer date: dates) {
            HashMap<Integer, Integer> roomDaySchedule = VM.getRoomSchedule(roomID).get(date);
            String roomName = VM.getRoomName(roomID);
            if (roomName.equals("Room does not exist.")) {
                continue;
            }
            for (Integer time : roomDaySchedule.keySet()) {
                if (VM.validateSchedule(time, roomID, date) && SM.checkSpeakerIsFree(speakerID, time, date)) {
                    String timeString = longDateToString(date, time, 0);
                    Integer date_time = Integer.parseInt(timeString);
                    availableTimes.put(timeString, date_time);
                }
            }
        }
        return availableTimes;
    }

    /**
     *
     * Given a room that has free time slots, find time slots in which all speakers in
     * allSpeakerID have free time.
     * @param roomID The ID of the room
     * @param allSpeakerID A list of speaker IDs.
     * @return An arraylist representing the mutual starting times of all speakers in a room.
     */
    public LinkedHashMap<String, Integer> getAvailableTimesWithAllSpeakers(int roomID, ArrayList<Integer> allSpeakerID) {
        // this hashmap stores the count of certain times
        LinkedHashMap<Integer, Integer> timeCounts = new LinkedHashMap<>();
        // the total number of speakers, if the count for some time in
        // the hashmap is equal the number of speakers, then that time is
        // mutual between all speakers.
        int numberOfSpeakers = allSpeakerID.size();
        for (int speakerID: allSpeakerID) {
            // get the starting times for this speaker
            LinkedHashMap<String, Integer> speakerAvailableTimes = getAvailableTimes(roomID, speakerID);
            // count each value of "time" in the array
            for (Map.Entry<String, Integer> time : speakerAvailableTimes.entrySet()) {
                if (timeCounts.containsKey(time.getValue())) {
                    timeCounts.put(time.getValue(), timeCounts.get(time.getValue()) + 1);
                } else {
                    timeCounts.put(time.getValue(), 1);
                }
            }
        }
        LinkedHashMap<String, Integer> mutualAvailableTimes = new LinkedHashMap<>();

        if (numberOfSpeakers == 0) { // for Party Event
            LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> schedule = VM.getRoomSchedule(roomID);
            for (Integer date : DM.getDatesDic().values()) {
                for (Integer time : schedule.get(date).keySet()) {
                    if (VM.validateSchedule(time, roomID, date)) {
                        String timeStringRep = longDateToString(date, time, 0);
                        if (timeStringRep.length() == 7) {
                            timeStringRep = "0" + timeStringRep;
                        }
                        String dateS = DM.dateToString(Integer.parseInt(timeStringRep.substring(0,4)));
                        String startTime = toTime(Integer.parseInt(timeStringRep.substring(4,6)));
                        mutualAvailableTimes.put(dateS + " " + startTime, Integer.parseInt(timeStringRep));
                    }
                }
            }
            return mutualAvailableTimes;
        }

        // timeCounts now contains the counts of all times
        for (Map.Entry<Integer, Integer> counts: timeCounts.entrySet()) {
            if (counts.getValue() == numberOfSpeakers) {
                // then this time is mutual between all the speakers
                String timeStringRep = counts.getKey().toString();
                if (timeStringRep.length() == 7) {
                    timeStringRep = "0" + timeStringRep;
                }
                String date = DM.dateToString(Integer.parseInt(timeStringRep.substring(0,4)));
                String startTime = toTime(Integer.parseInt(timeStringRep.substring(4,6)));
                mutualAvailableTimes.put(date + " " + startTime, counts.getKey());
            }
        }
        return mutualAvailableTimes;
    }

    /**
     * Gets the available end time for an event given the room and all speakers at the event (multiple speakers)
     * @author Priyanka
     * @param roomID ID of room of event
     * @param speakerIDs IDs of the speakers
     * @param startTimeLong 8 digit start time of event with date
     * @return Hashmap(K,V), where K: time (String), V: time (int) of the common available end times
     */
    public LinkedHashMap<String, Integer> getAvailableEndTimesWithAllSpeakers(int roomID, ArrayList<Integer> speakerIDs,
                                                                        Integer startTimeLong){
        String longDate = startTimeLong.toString();
        if (longDate.length() == 7) {
            longDate = "0" + longDate;
        }
        Integer date = Integer.parseInt(longDate.substring(0,4));
        Integer startTime = Integer.parseInt(longDate.substring(4,6));

        HashMap<Integer, Integer> roomSchedule = VM.getRoomSchedule(roomID).get(date);

        LinkedHashMap<String, Integer> times = new LinkedHashMap<>();

        if (speakerIDs.isEmpty()) { //a party
            int endTime = startTime + 4;
            if (VM.validateMultiHourSchedule(startTime, endTime,roomID, date)) {
                times.put(toTime(endTime), Integer.parseInt(longDateToString(date, startTime, endTime)));
                endTime += 1;
                if (VM.validateMultiHourSchedule(startTime, endTime,roomID, date)) {
                    times.put(toTime(endTime), Integer.parseInt(longDateToString(date, startTime, endTime)));
                }
            }
            return times;
        }

        //multiple speakers = panel = event can span 2 hours min or 3 hours max
        Integer endTime1 = startTime +2; //10am-12pm - we check that 11am and 12pm are commonly avail
        Integer endTime2 = startTime +3; //9am-12pm - we check that 10am, 11am and 12pm are commonly avail
            if (!getAvailableTimesWithAllSpeakers(roomID, speakerIDs).isEmpty()) {
                for (Integer s : speakerIDs) {
                    for (int ignored : roomSchedule.keySet()) {
                        //checks end time
                        if (roomSchedule.get(endTime1) == null && SM.checkSpeakerIsFree(s, endTime1, date)) {
                            //checks time in-between start and end time
                            int time = startTime + 1;
                            if (roomSchedule.get(time) == null && SM.checkSpeakerIsFree(s, time, date)) {
                                String timeString = toTime(time);
                                Integer date_time = Integer.parseInt(longDateToString(date, startTime, time));
                                times.put(timeString, date_time); //checks time in between start and end time
                            }
                            String timeString = toTime(endTime1);
                            Integer date_time = Integer.parseInt(longDateToString(date, startTime, endTime1));
                            times.put(timeString, date_time);
                        }
                        //checks end time
                        else if (roomSchedule.get(endTime2) == null && SM.checkSpeakerIsFree(s, endTime2, date)) {
                            //checks time in-between start and end time
                            int time1 = startTime + 1;
                            int time2 = startTime + 2;
                            if (roomSchedule.get(time1) == null && SM.checkSpeakerIsFree(s, time1, date)
                                    && roomSchedule.get(time2) == null && SM.checkSpeakerIsFree(s, time2, date)) {
                                String timeString = toTime(time1);
                                Integer date_time = Integer.parseInt(longDateToString(date, startTime, time1));
                                times.put(timeString, date_time);
                                date_time = Integer.parseInt(longDateToString(date, startTime, time2));
                                String timeString2 = toTime(time2);
                                times.put(timeString2, date_time);
                            }
                            String timeString = toTime(endTime2);
                            Integer date_time = Integer.parseInt(longDateToString(date, startTime, endTime2));
                            times.put(timeString, date_time);
                        }
                    }
                }
            }
        return times;
    }

    /**
     * Gets the commonly available times between a room and speaker given an eventID
     * @author Priyanka
     * @param eventID ID of event
     * @return Hashmap(K,V), where K: time as a string, V: time as an int
     */
    public HashMap<String, Integer> getAvailableTimesGivenEventID(int eventID) {
        Integer room = EM.getEventRoom(eventID);
        ArrayList<Integer> speakers = EM.getSpeaker(eventID);
        return getAvailableTimesWithAllSpeakers(room, speakers);
    }

    /**
     * Remove Event from Venues, Remove Attendees of this Event, Remove this Event from Attendees' list.
     * @param eventID int
     */
    public void cancelEvent(int eventID){
        AM.deleteEvent(eventID);
        SM.deleteEvent(eventID); // no need for a if statement
        VM.deleteEvent(eventID);
        DM.deleteEvent(eventID);
        EM.removeEvent(eventID);

    }

    /**
     * Gets all attendees in the system
     * @return Hashmap(K,V), where K: attendee name, V: attendee ID
     */
    public HashMap<String, Integer> getAttendees(){
        return AM.getAttendees();
    }

    /**
     * Gets all speakers in the system
     * @return Hashmap(K,V), where K: speaker name, V: speaker ID
     */
    public HashMap<String, Integer> getSpeakers(){ return SM.getSpeakers();}

    /**
     * Change the capacity of the event with the given ID, provided that the event exists, and the room for the event
     * can accommodate the new capacity
     * @param eventID the ID for the event who's capacity is being changed
     * @param capacity the new maximum capacity of the event
     * @return true iff the event exists, and the capacity for the room of the event is greater than or equal to the
     * new capacity for the event
     */
    public boolean changeEventCapacity(int eventID, int capacity) {
        return EM.changeEventCapacity(eventID, capacity, VM);
    }

    /**
     * The properties of the rooms
     * @return A LinkedHashMap</\string> With all the properties of the rooms
     */
    public LinkedHashMap<String, Integer> getRoomProperties(){
        LinkedHashMap<String, Integer> Properties = new LinkedHashMap<>();
        for (int i = 0; i < VM.getIds().size(); i++) {
            ArrayList<String> oneRoom = VM.getProperties(VM.getIds().get(i));
            String rmName = VM.getRoomName(VM.getIds().get(i));
            StringBuilder info = new StringBuilder(rmName + "'s Properties" + "\n");
            for (String prop : oneRoom) {
                info.append("    - ").append(prop).append("\n");
            }
            Properties.put(info.toString(), VM.getIds().get(i));
        }
        return Properties;
    }

    /**
     * The properties of the events
     * @author: Stephanie
     * @return A LinkedHashMap</String> with all the properties of all the events
     */
    public LinkedHashMap<String, Integer> getEventProperties(){
        LinkedHashMap<String, Integer> Properties = new LinkedHashMap<>();
        for (int i = 0; i < EM.getEventIds().size(); i++) {
            ArrayList<String> oneRoom = EM.getProperties(EM.getEventIds().get(i));
            String rmName = EM.getEventName(EM.getEventIds().get(i));
            StringBuilder info = new StringBuilder(rmName + "'s Properties" + "\n");
            for (String prop : oneRoom) {
                info.append("    - ").append(prop).append("\n");
            }
            Properties.put(info.toString(), EM.getEventIds().get(i));
        }
        return Properties;
    }

    /**
     * Reschedule an event
     * @author Priyanka
     * @param eventID the event being rescheduled
     * @param startTime the new start time
     * @param endTime the new end time
     * @param roomID the new room to be rescheduled to
     * @param day the day its being scheduled to
     */
    public void rescheduleEvent(int eventID, int startTime,int endTime, int day, int roomID){
        DM.rescheduleEvent(eventID, day);
        VM.rescheduleEvent(roomID, eventID, startTime, endTime, day);
        ArrayList<Integer> spks = EM.getSpeaker(eventID);
        SM.rescheduleEvent(spks, eventID, startTime, endTime, day);
        EM.rescheduleEvent(eventID, startTime, endTime, day);
    }

    /**
     * Gets a list of all rooms that have the specified properties, and have a capacity >= the event capacity specified
     * @author: steph
     * @param neededProperties the properties the rooms need to have
     * @param neededMinCapacity the minimum capacity of the room
     * @return the rooms matching the user's specifications
     */
    public LinkedHashMap<String, Integer> getRoomsWithPropertiesAndCapacityDict(ArrayList<String> neededProperties,
                                                                          int neededMinCapacity){
        LinkedHashMap<String, Integer> rooms;
        rooms = VM.getValidPropertyRooms(neededProperties, neededMinCapacity);
        return rooms;
    }

    /**
     * Add a property to a room
     * @author: steph
     * @param Roomid room where prop is being added
     * @param name the name of the prop to be added
     */
    public void addProperty (int Roomid, String name){
        VM.addProperty(Roomid, name);
    }

    public HashMap<String, Integer> getAvailableEndTimes(Integer eventID, Integer startTime) {
        Integer room = EM.getEventRoom(eventID);
        ArrayList<Integer> speakers = EM.getSpeaker(eventID);
        return getAvailableEndTimesWithAllSpeakers(room, speakers, startTime);
    }
}


