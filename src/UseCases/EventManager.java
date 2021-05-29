/*
@layer: Use Case
@collaborators: Event entity, User entity, PanelEvent Entity, PartyEvent Entity, UserManager
 */

package UseCases;

import Entities.*;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class EventManager {

    private final ArrayList<TalkEvent> talkEvents;
    private final ArrayList<Integer> EventIds;
    private final ArrayList<PanelEvent> panelEvents;
    private final ArrayList<PartyEvent> partyEvents;

    /**
     * Constructor for EventManager
     * @param talkEvents: list of all one-speaker talk events in system
     * @param IDs: Ids of ALL events in system
     * @param panelEvents: list of all panel events in system
     * @param partyEvents: list of all party events in system
     */
    public EventManager(ArrayList<TalkEvent> talkEvents, ArrayList<PanelEvent> panelEvents,
                        ArrayList<PartyEvent> partyEvents, ArrayList<Integer> IDs) {
        this.talkEvents = talkEvents;
        this.EventIds = IDs;
        this.panelEvents = panelEvents;
        this.partyEvents = partyEvents;
    }

    // getters start
    /**
     * Get all events stored in this manager
     * @author Priyanka
     * @return list of events
     */
    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> events = new ArrayList<>();
        events.addAll(talkEvents);
        events.addAll(panelEvents);
        events.addAll(partyEvents);
        return events;
    }

    /**
     * Get all the talks stored in this manager
     * @author Priyanka
     * @return list of talks
     */
    public ArrayList<TalkEvent> getTalkEvents() {
        return talkEvents;
    }

    /**
     * Get all the panels stored in this manager
     * @author Priyanka
     * @return list of panels
     */
    public ArrayList<PanelEvent> getPanelEvents() {
        return panelEvents;
    }

    /**
     * Get all the parties stored in this manager
     * @author Priyanka
     * @return list of parties
     */
    public ArrayList<PartyEvent> getPartyEvents() {
        return partyEvents;
    }

    /**
     * Gets ID of ALL events
     *
     * @return List of all IDs of events
     */
    public ArrayList<Integer> getEventIds() {
        return EventIds;
    }

    /**
     * Get the Event with the given id
     *
     * @param eventID id of an Event
     * @return return the Event that matches the id
     */
    public Event getEvent(Integer eventID) {
        for (Event event : talkEvents) {
            if (event.getID() == eventID) {
                return event;
            }
        }
        for (Event event : partyEvents) {
            if (event.getID() == eventID) {
                return event;
            }
        }
        for (Event event : panelEvents) {
            if (event.getID() == eventID) {
                return event;
            }
        }
        return null;
    }

    /**
     * Gets the name of an event
     *
     * @param eventID id of the event
     * @return String of the event's name
     */
    public String getEventName(int eventID) {
        Event EventObject = getEvent(eventID);
        if (EventObject != null) {
            return EventObject.getName();
        } else {
            return "Event does not exist or has been removed.";
        }
    }

    /**
     * Gets room of an event
     *
     * @param eventID id of the event
     * @return Integer of the room number of the event
     */
    public Integer getEventRoom(Integer eventID) {
        Event e = getEvent(eventID);
        if (e == null) {
            return -1;
        }
        return e.getRoom();
    }

    /**
     * Add the user with the given ID to the given event iff the user is not attending the event already and
     * the event has the capacity to accommodate the user
     *
     * @param UserId ID of the user
     * @param eventId ID of the event
     */
    public void addUserToEvent(int UserId, int eventId) {
        Event ThisEvent = getEvent(eventId);
        if (ThisEvent != null && isUserNotAttending(UserId, eventId) && ThisEvent.isSpace()) {
            ThisEvent.addAttendee(UserId);
        }
    }

    /**
     * Removes a user from an event
     *
     * @param eventId id of the event
     * @param UserId id of a user (speaker, attendee, organizer)
     */
    public void removeUserFromEvent(int UserId, int eventId) {
        if (!isUserNotAttending(UserId, eventId)) {
            Event ThisEvent = getEvent(eventId);
            if (ThisEvent != null) {
                ThisEvent.removeAttendee(UserId);
            }
        }
    }

    /**
     * Creates a new event ID and adds it to the list of all event IDs
     *
     * @return new event ID
     */
    private Integer newID() {
        int newID = EventIds.size()+1;
        EventIds.add(newID);
        return newID;
    }

    /**
     * Creates a new one-speaker talk with all necessary information
     * @param startTime: start time of event
     * @param title : title of event
     * @param room : room number where event will take place
     * @param speaker : speaker's ID
     * @param AttendIds : IDs of all those attending the event
     * @param properties : the properties for this event
     * @param capacity : the maximum capacity for this event
     * @param day : the day of the event
     * @param desc: event description
     * @return ID of talk made
     */
    public Integer createNewTalk(String title, int room, int startTime, int speaker, ArrayList<Integer> AttendIds,
                                  ArrayList<String> properties, int capacity, int day, String desc) {
        Integer id = newID();
        int endTime = startTime + 1;
        TalkEvent newTalkEvent = new TalkEvent(title, room, startTime, endTime, speaker, id, properties, capacity, day, desc);
        talkEvents.add(newTalkEvent);
        newTalkEvent.setDescription(desc);
        for (int attendee : AttendIds){
            newTalkEvent.addAttendee(attendee);
        }
        return id;
    }

    /**
     * Creates a new panel event with all necessary information
     * @author Priyanka
     * @param name: name of panel
     * @param room: room number where panel will take place
     * @param startTime: start time of panel
     * @param endTime: end time of penl
     * @param speakers: speaker's ID
     * @param attendeeIDs: IDs of attendees attending the event
     * @param capacity: the maximum capacity for the event
     * @param day: the date of the event
     * @param prop: panel properties
     * @param desc: event description
     * @return ID of panel made
     */
    public Integer createNewPanelEvent(String name, int room, int startTime, int endTime, ArrayList<Integer>
            speakers, ArrayList<Integer> attendeeIDs, int capacity, int day, ArrayList<String> prop, String desc) {
        Integer id = newID();
        PanelEvent newPanelEvent = new PanelEvent(name, room, startTime, endTime, id, capacity, day, prop, speakers,desc);
        panelEvents.add(newPanelEvent);
        newPanelEvent.setDescription(desc);
        for (int attendee : attendeeIDs){
            newPanelEvent.addAttendee(attendee);
        }
        return id;
    }

    /**
     * Gets event's description
     * @author Priyanka
     * @param eventID ID of event
     * @return description
     */
    public String getEventDesc(int eventID)
    {
        return getEvent(eventID).getDescription();
    }

    /**
     * Gets event's by date
     * @author Priyanka
     * @param date date to get events on
     * @return list of eventIDs
     */
    public ArrayList<Integer> getEventsbyDate(int date)
    {
        ArrayList<Event> events = getAllEvents();
        ArrayList<Integer> eventsByDate = new ArrayList<>();

        for(Event e : events)
        {
            if(e.getDate() == date)
            {
                eventsByDate.add(e.getID());
            }
        }
        return eventsByDate;
    }

    /**
     * Creates a new party event with all necessary information
     * @author Priyanka
     * @param name: title of party
     * @param room: room number where party will take place
     * @param startTime: start time of party
     * @param endTime: end time of party
     * @param attendeeIDs: IDs of attendees attending the party
     * @param capacity: the maximum capacity for the event
     * @param day: the date of the event
     * @param prop: panel properties
     * @param desc: event description
     * @return ID of party made
     */
    public Integer createNewPartyEvent(String name, int room, int startTime, ArrayList<Integer> attendeeIDs,
                                       int endTime, int capacity, int day, ArrayList<String> prop, String desc) {
        Integer id = newID();
        PartyEvent newPartyEvent = new PartyEvent(name, room, startTime, id, endTime, capacity, day, prop, desc);
        partyEvents.add(newPartyEvent);
        newPartyEvent.setDescription(desc);
        for (int attendee : attendeeIDs){
            newPartyEvent.addAttendee(attendee);
        }
        return id;
    }

    /**
     * Ensures title of the event is unique
     * @author Priyanka
     * @param title: Title of the event
     * @return true iff title is unique
     */
    public boolean checkTitleIsUnique(String title) {
        for (Event event : talkEvents) {
            if (event.getName().equals(title)) {
                return false;
            }
        }
        for (Event event : partyEvents) {
            if (event.getName().equals(title)) {
                return false;
            }
        }
        for (Event event : panelEvents) {
            if (event.getName().equals(title)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks that the event exists
     *
     * @param eventID id of the event
     * @return true iff event ID exists in list of ALL event IDs
     */
    public boolean validateEventExists(int eventID){
        return getEvent(eventID) != null;
    }


    /**
     * For single-speaker events (talk)
     * Change the speaker assigned to an event
     *
     * @param speakerID: the ID for the new speaker being assigned to an event
     * @param eventID: the ID for the event to which a new speaker is being assigned
     */
    public void changeSpeakerForTalkEvent(int speakerID, int eventID) {
        TalkEvent event = (TalkEvent) getEvent(eventID);
        if (event != null) {
            event.setSpeaker(speakerID);
        }
    }

    /**
     * For multi-speaker events (panel)
     * Change the speakers assigned to an event
     * @author Priyanka
     * @param speakerIDs: the IDs for the new speakers being assigned to a panel
     * @param eventID: the ID for the panel to which the new speakers are being assigned
     */
    public void changeSpeakersForPanelEvent(ArrayList<Integer> speakerIDs, int eventID) {
        PanelEvent event = (PanelEvent) getEvent(eventID);
        event.setSpeakers(speakerIDs);
    }

    /**
     * Checks if a user is not attending an event
     *
     * @param eventId: id of the event
     * @param UserId: ID if the user (organizer, speaker, attendee)
     * @return true iff user is not attending the event
     */
    private boolean isUserNotAttending(int UserId, int eventId) {
        //return false if the user is already attending
        Event event = getEvent(eventId);
        if (event == null) {
            return false;
        }
        return !event.getAttendees().contains(UserId);
    }

    /**
     * Get all the attendees of an event
     *
     * @param eventID id of event
     * @return a list of attendee's id
     */
    public ArrayList<Integer> getAttendees(Integer eventID) {
        Event e = getEvent(eventID);
        if (e == null) {
            return new ArrayList<>();
        }
        return getEvent(eventID).getAttendees();
    }

    /**
     * Get all speakers of a panel
     * @author Priyanka
     * @param eventID: id of event
     * @return a list of attendee's id
     */
    public ArrayList<Integer> getPanelSpeakers(Integer eventID) {
        PanelEvent event = (PanelEvent) getEvent(eventID);
        if (event == null) {
            return new ArrayList<>();
        }
        return event.getSpeakers();
    }


    /**
     * Gets the start time of any event
     *
     * @param eventID id of the interested event
     * @return Integer of time of the interested event
     */
    public int getStartTime(Integer eventID) {
        Event e = getEvent(eventID);
        if (e == null) {
            return -1;
        }
        return e.getStartTime();
    }


    /**
     * Gets events with space in them
     *
     * @return Hashmap(K,V), where K: event name and V: event ID (all with space)
     */
    public HashMap<String, Integer> getEventsWithSpace(){
        HashMap<String, Integer> events = new HashMap<>();
        for (TalkEvent event : this.talkEvents){
            if (event.isSpace()){
                events.put(event.getName(), event.getID());
            }
        }
        for (PanelEvent event : this.panelEvents){
            if (event.isSpace()){
                events.put(event.getName(), event.getID());
            }
        }
        for (PartyEvent event : this.partyEvents){
            if (event.isSpace()){
                events.put(event.getName(), event.getID());
            }
        }
        return events;
    }

    /**
     * Removes an event from the list of events.
     * @author Priyanka
     * @param eventID: event's ID
     */
    public void removeEvent(Integer eventID){
        Event event = getEvent(eventID);
        if (event != null) {
            talkEvents.remove(event);
            panelEvents.remove(event);
            partyEvents.remove(event);
        }
    }

    /**
     * Gets a HashMap of all events that the currently logged in user is not enrolled in.
     * @param userID id of current login user
     * @return Hashmap(K,V), where K: event name and V:event ID, for all events that the current user is not
     * enrolled in.
     */
    public HashMap<String, Integer> getEventNotEnrolled(Integer userID) {
        HashMap<String, Integer> temp = new HashMap<>();
        for (int i : getEventIds()) {
            if(getEvent(i) != null && !getAttendees(i).contains(userID)) {
                temp.put(getEventName(i), i);
            }
        }
        return temp;
    }

    /**
     * Gets a hashmap of ALL events.
     * @author Priyanka
     * @return Hashmap(K,V), where K: event name and V:eventID for all events.
     */
    public HashMap<String, Integer> getEventsMap() {
        HashMap<String, Integer> map = new HashMap<>();
        for(PartyEvent p : partyEvents) {
            map.put(getEventName(p.getID()), p.getID());
        }
        for(TalkEvent t : talkEvents) {
            map.put(getEventName(t.getID()), t.getID());
        }
        for(PanelEvent k : panelEvents) {
            map.put(getEventName(k.getID()), k.getID());
        }
        return map;
    }

    /**
     * Validate the property exists
     * @author: Steph
     * @param Prop the property being checked
     * @param eventID the eventID where the property is being checked
     * @return True if the property exists within the event
     */
    public boolean validatePropertyExists(String Prop, int eventID){
        for (String prop: getEvent(eventID).getProperties()){
            if (prop.equals(Prop)){
                return true;
            }
        }
        return false;
    }

    /**
     * Remove a property from an event
     * @author: Steph
     * @param Prop the property being removed
     * @param eventID the ID of the event where the property is being removed
     */
    public void removeProperty(String Prop, int eventID){
        if (validatePropertyExists(Prop, eventID)){
            getEvent(eventID).removeProperty(Prop);
        }
    }

    /**
     * Get the properties within this event
     * @author: Steph
     * @param eventID the event in question
     * @return An ArrayList</String> with all the properties
     */
    public ArrayList<String> getProperties (int eventID) {
        Event event = getEvent(eventID);
        if (event == null) {
            return new ArrayList<>();
        }
        return event.getProperties();
    }

    /**
     * Change the capacity of the event with the given ID iff the event exists, and the capacity for the room for the
     * event is greater than or equal to the new capacity of the event.
     * @param eventID ID of the event who's capacity is being changed
     * @param capacity new maximum capacity for the event
     * @param vm VenueManager
     * @return true iff the new capacity has been set
     */
    public boolean changeEventCapacity(Integer eventID, int capacity, VenueManager vm) {
        Event event = getEvent(eventID);
        if (event == null) {
            return false;
        }
        int roomCapacity = vm.getRoomCapacity(event.getRoom());
        if (roomCapacity >= capacity) {
            event.setCapacity(capacity);
            return true;
        }
        return false;
    }

    /**
     * Return the maximum capacity for this event
     * @param eventID ID for the event
     * @return maximum capacity for the event
     */
    public int getEventCapacity(int eventID) {
        Event event = getEvent(eventID);
        if (event == null) {
            return 0;
        }
        return event.getCapacity();
    }

    /**
     * Reschedule event
     * @param EventID the id of the event being rescheduled
     * @param starttime the new start time of the event
     * @param endtime the new end time of the event
     * @param day the day of the event
     */
    public void rescheduleEvent(int EventID, int starttime, int endtime, int day){
        Event ev = getEvent(EventID);
        if (ev != null) {
            ev.setDate(day);
            ev.setStartTime(starttime);
            ev.setEndTime(endtime);
        }
    }

    /**
     * Get the time span of the event with the given ID
     * @param eventID ID of the event
     * @return difference between the start time and the stop time of the event
     */
    public int getEventSpan(int eventID) {
        Event event = getEvent(eventID);
        if (event == null) {
            return 0;
        }
        return event.getEndTime() - event.getStartTime();
    }

    /**
     * Return the start time for the event with the given ID
     * @param eventID ID of the event
     * @return start time for this event
     */
    public Integer getEventStartTime(int eventID) {
        Event event = getEvent(eventID);
        if (event == null) {
            return -1;
        }
        return event.getStartTime();
    }

    /**
     * Return the end time for the event with the given ID
     * @param eventID ID of the event
     * @return end time for this event
     */
    public int getEventEndTime(int eventID) {
        Event event = getEvent(eventID);
        if (event == null) {
            return -1;
        }
        return event.getEndTime();
    }

    /**
     * Get event speaker(s)
     * @param eventID ID of the event
     * @return List of speaker IDs
     */
    public ArrayList<Integer> getSpeaker(int eventID) {
        for (PanelEvent e : panelEvents) {
            if (e.getID() == eventID) {
                return e.getSpeakers();
            }
        }
        for (TalkEvent e : talkEvents) {
            if (e.getID() == eventID) {
                return new ArrayList<>(Collections.singletonList(e.getSpeaker()));
            }
        }
        return new ArrayList<>();
    }

    /**
     * return a userlist of this event
     * @param EventID eventID
     * @return list of user
     */
    public ArrayList<Integer> getAttendeeInEvent(int EventID){
        Event event = getEvent(EventID);
        if (event != null) {
            return event.getAttendees();
        }
        return new ArrayList<>();
    }
}


