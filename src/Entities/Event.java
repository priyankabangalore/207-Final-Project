/*
@layer: Entity
@collaborators: PartyEvent, PanelEvent, OneSpeakerEvent
*/

package Entities;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Event implements Serializable {
    private final String name;
    private final int room;
    private int startTime;
    private int endTime;
    private final ArrayList<Integer> attendees;
    private final int ID;
    private int capacity;
    private final ArrayList<String> properties;
    private int date;
    private String description;
    private final ArrayList<String> rules;


    /**
     * Represents an Instance Event
     *
     * @author Priyanka
     * @param name     name of the event
     * @param room      the room in which the event takes place
     * @param startTime the time in which the event starts
     * @param endTime   the time in which the event ends
     * @param ID        the ID of the event
     * @param prop      the properties of the event
     * @param capacity  the maximum capacity for the event
     * @param desc the event's description
     * @param date the date for the event
     */
    public Event(String name, int room, int startTime, int endTime, int ID, ArrayList<String> prop,
                 int capacity, int date, String desc) {
        this.name = name;
        this.room = room;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.ID = ID;
        this.attendees = new ArrayList<>();
        this.capacity = capacity;
        this.properties = prop;
        this.description = desc;
        this.rules = new ArrayList<>();
    }

    /**
     * Get name of Event
     * @author Priyanka
     * @return name of Event
     */
    public String getName() {
        return name;
    }

    /**
     * Get the date for the event
     * @return date for the event
     */
    public int getDate(){return date;}

    /**
     * Get ID of room for Event
     * @author Priyanka
     * @return room ID for Event
     */
    public int getRoom() {
        return room;
    }

    /**
     * Get the time at which the Event STARTS
     * @author Priyanka
     * @return start time of Event
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Get the time at which the Event ends
     * @author Priyanka
     * @return end time of Event
     */
    public int getEndTime() {return endTime;}

    /**
     * Gets ID for the Event
     * @author Priyanka
     * @return ID of Event
     */
    public int getID(){
        return ID;
    }

    /**
     * Gets attendees for the Event
     * @author Priyanka
     * @return list of IDs of attendees attending
     */
    public ArrayList<Integer> getAttendees() {
        return attendees;
    }

    /**
     * Get the maximum capacity for this event
     * @return the capacity for this event
     */
    public int getCapacity() { return capacity; }

    /**
     * Adds attendee to Event
     * @author Priyanka
     * @param attendeeID: ID of attendee
     */
    public void addAttendee(Integer attendeeID) {
        attendees.add(attendeeID);
    }


    /**
     * Removes attendee from list of attendees attending the Event
     * @author Priyanka
     * @param attendeeID: ID of attendee
     */
    public void removeAttendee(Integer attendeeID){
        attendees.remove(attendeeID);
    }

    /**
     * Check if Event has space
     * @author Priyanka
     * @return true iff the Event has space
     */
    public boolean isSpace(){
        return attendees.size() < capacity;
    }

    /**
     * Set a maximum capacity for this event
     * @param capacity capacity for this event
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Sets description for Event
     * @author Priyanka
     * @param d: description to be set
     */
    public void setDescription(String d)
    {
        description=d;
    }

    /**
     * Gets description for Event
     * @author Priyanka
     * @return description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets rules for party
     * @author Priyanka
     * @param r: rule to be set
     */
    public void setRules(String r)
    {
        rules.add(r);
    }

    /**
     * Gets rules for party
     * @author Priyanka
     * @return rules
     */
    public ArrayList<String> getRules()
    {
        return rules;
    }

    /**
     * Get the properties that the event needs
     *
     * @return the properties of the event
     *
     */
    public ArrayList<String> getProperties() {
        return properties;
    }


    /**
     * Add a new property to the event
     *
     * @param Prop new property to be added
     */
    public void addProperty(String Prop) {
        properties.add(Prop);
    }

    /**
     * Remove property from the existing properties of the event
     *
     * @param Prop property to be removed
     */
    public void removeProperty(String Prop) {
        properties.remove(Prop);
    }

    /**
     * Change the date of the event
     * @param Date What the date is being checked to
     */
    public void setDate(int Date) {
        this.date = Date;
    }

    /**
     * Set the start time of the event
     * @param st new start time
     */
    public void setStartTime(int st){
        this.startTime = st;
    }

    /**
     * Set the end time of the event
     * @param et new end time
     */
    public void setEndTime(int et){
        this.endTime = et;
    }
}