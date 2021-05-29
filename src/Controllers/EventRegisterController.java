/*
@layer: Controller
@collaborators: EventManager, VenueManager
 */

package Controllers;

import UseCases.DateManager;
import UseCases.EventManager;
import UseCases.VenueManager;

import java.util.ArrayList;
import java.util.HashMap;

public class EventRegisterController {

    private final EventManager MasterEventManager;
    private final VenueManager vm;
    private final DateManager dm;

    /**
     * Constructor for EventRegisterController
     * @param EventManager: EventManager instance
     * @param vm: VenueManager instance
     */
    public EventRegisterController(EventManager EventManager, VenueManager vm, DateManager dm) {
        this.MasterEventManager = EventManager;
        this.vm = vm;
        this.dm = dm;
    }

    /**
     * Gets a HashMap of all events that the currently logged in user is not enrolled in.
     * @param userID id of current login user
     * @return event name : event ID, for all events that the current user is not enrolled in.
     */
    public HashMap<String, Integer> getEventNotEnrolled(Integer userID) {
        return MasterEventManager.getEventNotEnrolled(userID);
    }

    /**
     * Gets an array list of ALL events.
     * @return eventname : eventID for all events.
     */
    public HashMap<String, Integer> getEvents() {
        return MasterEventManager.getEventsMap();
    }


    /**
     * Validates event description
     * @author Priyanka
     * @param desc: the IDs for the new speakers being assigned to a panel
     * @return true iff description is a valid length and contains valid characters
     */
    public boolean validateDescription(String desc)
    {
        return desc.length() > 1 && desc.length() < 250;
    }

    /**
     * Gets a HashMap of all events that the currently logged in user is not enrolled in and that have space.
     * @param userID id of current login user
     * @return event name : event ID, for all events that the current user is not enrolled in.
     */
    public HashMap<String, Integer> getEventNotEnrolledWithSpace(Integer userID) {
        HashMap<String, Integer> tempMap = new HashMap<>();
        for (Integer i : MasterEventManager.getEventsWithSpace().values()) {
            if (getEventNotEnrolled(userID).containsValue(i)) {
                tempMap.put(MasterEventManager.getEventName(i), i);
            }
        }
        return tempMap;
    }

    /**
     * Creates a talk event
     * @param title String title of Event
     * @param room int ID of Room
     * @param time int time of Event
     * @param speaker int ID of Speaker at Event
     * @param AttendeeIDs ArrayList containing IDs of Attendees of this Event
     * @param sc the SpeakerController
     * @param vc the VenueController
     * @param capacity the maximum capacity of this one speaker event
     * @param properties the properties of this one speaker event
     * @param day the day of the event
     * @param desc the description of the event
     */
    public Integer createTalkEvent(String title, int room, int time, int speaker, ArrayList<Integer> AttendeeIDs,
                            SpeakerController sc, VenueController vc, ArrayList<String> properties, int capacity,
                                int day, String desc) {
        while(!MasterEventManager.checkTitleIsUnique(title)) {
            title = title + "x";
        }
        Integer eventID = MasterEventManager.createNewTalk(title, room, time, speaker, AttendeeIDs,
                properties, capacity, day, desc);
        sc.enlistSpeakerForTalkEvent(speaker, eventID, day);
        vc.setEventinRoom(eventID, room, time, day);
        return eventID;
    }

    /**
     * Creates a panel event
     * @author Priyanka
     * @param name name of Panel Event
     * @param room ID of Room
     * @param startTime start time of panel
     * @param endTime end time of panel
     * @param speakers list of IDs of Speakers at Panel Event
     * @param AttendeeIDs IDs of all Attendees of this panel
     * @param sc the SpeakerController
     * @param vc the VenueController
     * @param capacity the maximum capacity for this panel event
     * @param desc the description of the event
     * @param day the date of the event
     * @param prop the properties of the panel event
     */
    public Integer createPanelEvent(String name, int room, int startTime, int endTime, ArrayList<Integer> speakers,
                                 ArrayList<Integer> AttendeeIDs, SpeakerController sc, VenueController vc,
                                 int capacity, int day, ArrayList<String> prop, String desc) {
        while(!MasterEventManager.checkTitleIsUnique(name)) {
            name = name + "x";
        }
        Integer eventID = MasterEventManager.createNewPanelEvent(name, room, startTime, endTime, speakers,
                AttendeeIDs, capacity, day, prop, desc);
        sc.enlistSpeakersForPanelEvent(speakers, eventID, MasterEventManager, day);
        vc.setPanelorPartyinRoom(eventID, room, startTime, endTime, day);
        return eventID;
    }

    /**
     * Creates a party event
     * @author Priyanka
     * @param name name of party Event
     * @param room ID of Room
     * @param startTime start time of party
     * @param endTime end time of party
     * @param AttendeeIDs IDs of all Attendees of this party
     * @param vc the VenueController
     * @param capacity the maximum capacity for the party event
     * @param desc the description of the event
     * @param date the day of the event
     * @param prop the properties of the event
     */
    public Integer createPartyEvent(String name, int room, int startTime, int endTime, ArrayList<Integer> AttendeeIDs,
                                 VenueController vc, int capacity, int date, ArrayList<String> prop, String desc) {
        while(!MasterEventManager.checkTitleIsUnique(name)) {
            name = name + "x";
        }
        Integer eventID = MasterEventManager.createNewPartyEvent(name, room, startTime, AttendeeIDs,
                endTime, capacity, date, prop, desc);
        vc.setPanelorPartyinRoom(eventID, room, startTime, endTime, date);
        return eventID;
    }

    /**
     * Decides what event to create based on inputs
     * @author Priyanka
     * @param name name of Event
     * @param roomID ID of Room
     * @param startTime start time of event
     * @param endTime end time of event
     * @param attendeeIDs IDs of all Attendees of this event
     * @param vc the VenueController
     * @param sc the SpeakerController
     * @param capacity the maximum capacity for the event
     * @param desc the description of the event
     * @param prop the properties of the event
     * @param day the day at which the event occurs
     * @param speakerIDs the IDs of the speakers that will be speaking at the event
     */
    public void chooseEventToMake(String name, int roomID, int startTime, int endTime, ArrayList<Integer> speakerIDs,
                      ArrayList<Integer> attendeeIDs, SpeakerController sc, VenueController vc, ArrayList<String> prop,
                                  int capacity, String desc, int day) {
        Integer eventID;

        if(speakerIDs.size()==1 && endTime-startTime==1) {
            Integer speakerID = speakerIDs.get(0);
            eventID = createTalkEvent(name, roomID, startTime, speakerID, attendeeIDs, sc, vc, prop, capacity, day, desc);
        }
        else if(speakerIDs.size() > 0) {
            eventID = createPanelEvent(name, roomID, startTime, endTime, speakerIDs, attendeeIDs, sc, vc, capacity, day, prop, desc);
        }
        else {
            eventID = createPartyEvent(name, roomID, startTime, endTime, attendeeIDs, vc, capacity, day, prop, desc);
        }
        dm.addEvent(day, eventID);
    }
}
