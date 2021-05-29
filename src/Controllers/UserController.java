/*
@layer: Controller
@collaborators: AttendeeManager, SpeakerManager, OrganizerManager, UserManager, VenueManager, EventManager
 */

package Controllers;

import UseCases.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;


public abstract class UserController {

    final AttendeeManager AM;
    final SpeakerManager SM;
    final OrganizerManager OM;
    final UserManager UM;
    final EventManager EM;
    final VenueManager VM;
    final RequestManager RM;
    final DateManager DM;

    /**
     * Constructor for UserController
     * @param AM : AttendeeManager instance
     * @param SM : SpeakerManager instance
     * @param OM : OrganizerManager instance
     * @param UM : UserManager instance
     * @param EM : EventManager instance
     * @param VM : VenueManager instance
     * @param RM : RequestManager instance
     * @param DM : DateManager instance
     */
    public UserController(AttendeeManager AM, SpeakerManager SM, OrganizerManager OM, UserManager UM,
                          EventManager EM, VenueManager VM, RequestManager RM, DateManager DM) {
        this.AM = AM;
        this.EM = EM;
        this.SM = SM;
        this.OM = OM;
        this.UM = UM;
        this.VM = VM;
        this.RM = RM;
        this.DM = DM;
    }

    /**
     * Get the events by a time(s) of user's choice
     * @author: Priyanka
     * @param times start time(s) we are looking at
     * @param attendeeID ID of attendee
     * @return Hashmap(K,V), where K: event name + description, V: eventID
     */
    public HashMap<String,Integer> getEventsByTime(ArrayList<Integer> times, Integer attendeeID){
        HashMap<String,Integer> eventMap = new HashMap<>();
        if(times.size()>0) {
            for(Integer time : times) {
                for (int e : EM.getEventIds()) {
                    if (EM.getEventStartTime(e).equals(time)) {
                        if(AM.checkAttendeeIsAvailableForEvent(e, attendeeID, UM, EM))
                        {
                            String eventStuff = EM.getEventName(e) + EM.getEventDesc(e);
                            eventMap.put(eventStuff,e);
                        }
                    }
                }
            }
        }
        return eventMap;
    }

    /**
     * Get the events by speakers(s) of user's choice
     * @author: Priyanka
     * @param attendeeID ID of attendee
     * @param speakerIDs IDs of the speaker(s) requested
     * @return Hashmap(K,V), where K: event name + description, V: eventID
     */
    public HashMap<String,Integer> getEventsBySpeaker(ArrayList<Integer> speakerIDs, Integer attendeeID) {
        HashMap<String,Integer> eventMap = new HashMap<>();
        if(speakerIDs.size()>0) {
            for (int s : speakerIDs) {
                ArrayList<Integer> events = SM.getEvents(s);
                for(int e : events) {
                    if(AM.checkAttendeeIsAvailableForEvent(e, attendeeID, UM, EM)) {
                        String eventStuff = EM.getEventName(e) + EM.getEventDesc(e);
                        eventMap.put(eventStuff,e);
                    }
                }
            }
        }
        return eventMap;
    }

    /**
     * Get the events by date(s) of user's choice
     * @author: Priyanka
     * @param attendeeID ID of attendee
     * @param dateList date(s) requested
     * @return Hashmap(K,V), where K: event name + description, V: eventID
     */
    public HashMap<String, Integer> getEventsByDate(ArrayList<Integer> dateList, Integer attendeeID) {
        HashMap<String,Integer> eventMap = new HashMap<>();
        if(dateList.size()>0) {
            for (int d : dateList) {
                ArrayList<Integer> EventsbyDate = EM.getEventsbyDate(d);
                for(int e : EventsbyDate) {
                    if(AM.checkAttendeeIsAvailableForEvent(e, attendeeID, UM, EM)) {
                        String eventStuff = EM.getEventName(e) + EM.getEventDesc(e);
                        eventMap.put(eventStuff,e);
                    }
                }
            }
        }
        return eventMap;
    }

    /**
     * Filters events and calls necessary functions given input
     * @author: Priyanka
     * @param speakerIDs speaker(s) requested to filter by
     * @param times time(s) requested to filter by
     * @param dates date(s) requested to filter by
     * @param attendeeID ID of attendee
     * @return A string with all the events and related info of the event with the specified filters
     */
    public HashMap<String,Integer> eventFilters(ArrayList<Integer> times, ArrayList<Integer> speakerIDs,
                                                ArrayList<Integer> dates, Integer attendeeID) {
        HashMap<String, Integer> d = getEventsByDate(dates, attendeeID);
        HashMap<String, Integer> t = getEventsByTime(times, attendeeID);
        HashMap<String, Integer> s = getEventsBySpeaker(speakerIDs, attendeeID);
        HashMap<String, Integer> filter = new HashMap<>();
        for(String stuff : d.keySet()) {
            for(Integer ID : d.values()) {
                if(!filter.containsValue(ID)) {
                    filter.put(stuff,ID);
                }
            }
        }
        for(String stuff : t.keySet()) {
            for(Integer ID : t.values()) {
                if(!filter.containsValue(ID)) {
                    filter.put(stuff,ID);
                }
            }
        }
        for(String stuff : s.keySet()) {
            for(Integer ID : s.values()) {
                if(!filter.containsValue(ID)) {
                    filter.put(stuff,ID);
                }
            }
        }
        return filter;
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
     * Gets the username of a user from ID
     * @param userID the user's ID
     * @return the user's username
     */
    public String getUsername(int userID){
        return UM.getUserName(userID);
    }

    /**
     * Returns a list of all the dates that are currently set up for the conference
     * @author: Steph
     * @return See details above
     *          K - String version of date (March 13)
     *          V - Integer version of date (0313)
     */
    public LinkedHashMap<String, Integer> getDatesDict(){
        return DM.getDatesDic();
    }


    /**
     * Gets a list of all the times of the event (9-5pm)
     * @author: Steph
     * @return See details above
     */ //TODO method looks sus
    public LinkedHashMap<String, Integer> getTimesDict(){
        LinkedHashMap<String, Integer> timesDict = new LinkedHashMap<>();
        for (int i = 9; i <= 17; i++){
            String stringTime = toTime(i);
            timesDict.put(stringTime, i);
        }
        return timesDict;
    }
}




