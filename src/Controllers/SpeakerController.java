/*
@layer: Controller
@collaborators: SpeakerManager, AttendeeManager, EventManager
 */

package Controllers;

import UseCases.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class SpeakerController extends UserController {

    /**
     * Constructor for SpeakerController
     * @param AM: AttendeeManager instance
     * @param SM: SpeakerManager instance
     * @param OM: OrganizerManager instance
     * @param UM: UserManager instance
     * @param EM: EventManager instance
     * @param VM: VenueManager instance
     * @param DM: DateManager instance
     * @param RM: RequestManager instance
     */
    public SpeakerController(AttendeeManager AM, SpeakerManager SM, OrganizerManager OM, UserManager UM,
                             EventManager EM, VenueManager VM, RequestManager RM, DateManager DM) {
        super(AM, SM, OM, UM, EM, VM, RM, DM);
    }

    /**
     * Calls getAttendees() in the SpeakerManager.
     * @author Priyanka
     * @param speakerID: ID of the speaker
     * @return all attendees that are signed up for events hosted by the current speaker as a hashmap(K,V),
     * where K: attendee username, V: attendee ID
     */
    public HashMap<String, Integer> getAttendees(int speakerID) {
        return SM.getAttendees(speakerID, AM);
    }

    /**
     * gets the schedule (event name: event id) of the currently logged in speaker.
     * @author Priyanka
     * @param speakerID: ID of the speaker
     * @return all future events hosted by the current speaker
     */
    public HashMap<String, Integer> getSchedule(int speakerID)
    {
        HashMap<String, Integer> schedule = new HashMap<>();
        ArrayList<Integer> speakerEventIDs = SM.getEvents(speakerID);
        for (Integer eventID : speakerEventIDs) {
            schedule.put(EM.getEventName(eventID), eventID);
        }
        return schedule;
    }

    /**
     * Creates a schedule of ALL the speakers with their event in different days using getSpeakerSchedule() in SpeakerManager
     * @author Tian
     * @return hashmap(K,V), K: String with Speaker username,day, event name, event time. V: speaker ID
     */
    public HashMap<String, Integer> getSpeakerSchedules() {
        LinkedHashMap<String, Integer> speakerSchedule = new LinkedHashMap<>();
        for (int ID : SM.getIDs()) {
            LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> temp = SM.getSpeakerSchedule(ID);
            String speakerName = UM.getUserName(ID);
            StringBuilder content = new StringBuilder();
            content.append(speakerName);
            content.append("\n");
            for(int date: temp.keySet()){
                content.append(DM.dateToString(date));
                content.append("\n");
                for(int time: temp.get(date).keySet()){
                    content.append(VM.toTime(time));
                    content.append(EM.getEventName(temp.get(date).get(time)));
                    content.append("\n");
                }
            }
            String stringContent = content.toString();
            speakerSchedule.put(stringContent, ID);
        }
        return speakerSchedule;
    }


    /**
     * For one-speaker event only
     * Add an event to the given speaker's list of events
     * @param speakerID the speaker that is being added to the event
     * @param eventID the event who's speaker is being changed
     * @param date the date at which the speaker is being registered in the talk
     */
    public void enlistSpeakerForTalkEvent(Integer speakerID, Integer eventID, int date) {
        int time = EM.getStartTime(eventID);
        if (SM.checkSpeakerIsFree(speakerID, time, date)) {
            SM.addTalkEventToSpeaker(speakerID, eventID, EM, date);
            EM.changeSpeakerForTalkEvent(speakerID, eventID);
        }
    }

    /**
     * For panel event use only
     * Add an event to the list of speakers' list of events
     * @author Priyanka
     * @param speakerID the speaker that is being added to the event
     * @param eventID the event who's speaker is being changed
     * @param date the date at which the speaker is being scheduled for the event
     * @param e EventManager
     */
    public void enlistSpeakersForPanelEvent(ArrayList<Integer> speakerID, Integer eventID, EventManager e, int date) {
        int time = EM.getStartTime(eventID);
        if (SM.checkSpeakersAreFree(speakerID, time, date)) {
            SM.addPanelEventToSpeakers(speakerID, eventID, e, date);
            EM.changeSpeakersForPanelEvent(speakerID, eventID);
        }
    }

    /**
     * Views the average rating (out of 5) the user has
     * @author Priyanka
     * @param speakerID ID of speaker
     */
    public String viewRatings(int speakerID)
    {
       return SM.viewRatings(speakerID);
    }

    /**
     * Add a new rating to this speaker's ratings
     * @author Priyanka
     * @param speakerID ID of the speaker
     * @param rating rating to be added
     */
    public void addRatingtoSpeaker(int rating, int speakerID) {
        SM.addRatingtoSpeaker(rating, speakerID);
    }

    /**
     * return a hashmap of username and userID
     * @param EventID the eventID
     * @return hashMap<></>
     * K name
     * V sID
     */
    public HashMap<String, Integer> getAttendeeInEvent(int EventID){
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        ArrayList<Integer> userlist = EM.getAttendeeInEvent(EventID);
        for (int attendeeID: userlist){
            map.put(UM.getUserName(attendeeID), attendeeID);
        }
        return map;
    }
}
