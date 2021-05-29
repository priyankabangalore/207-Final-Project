/*
@layer: Entity
 */

package Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Objects;

public class Room implements Serializable {
    private final String name;
    private final int id;
    private final int openTime;
    private final int closeTime;
    private final int capacity;
    private final ArrayList<String> properties;
    private final LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> schedule2; // Day : time : eventID


    /**
     * A room instance variable where events can take place
     * @param roomName the name of the room
     * @param roomID the id of the room
     * @param Open when the room opens for events
     * @param Close when the room closes for events
     * @param properties the properties of the room
     * @param capacity the maximum capacity of the room
     */
    public Room(String roomName, int roomID, int Open, int Close, int capacity,
                ArrayList<String> properties, Collection<Integer> existingDays){
        this.name = roomName;
        this.id = roomID;
        this.openTime = Open;
        this.closeTime = Close;
        this.schedule2 = new LinkedHashMap<>();
        this.properties = properties;

        if (this.properties.contains("Tables")) {
            this.capacity = capacity/20;
        }
        else if (this.properties.contains("Chairs")) {
            this.capacity = capacity/2;
        }
        else {
            this.capacity = capacity;
        }

        for (Integer day : existingDays) {
            addDate(day);
        }
    }

    /**
     * Set the event in the room
     * @param time the time its being set at
     * @param EventId the event being set
     * @param date the date its being set on
     */
    public void setEvent(int time, int EventId, int date){
        LinkedHashMap<Integer, Integer> day = schedule2.get(date);
        day.put(time, EventId);
        }

    /**
     * Sets the time period that an event takes place in room
     * @author Priyanka
     * @param startTime the time the event will take place
     * @param eventID the id of the event being scheduled
     * @param endTime time event ends
     * @param day the day of the event
     */
    public void setEventWithinTimePeriod(int startTime, int endTime, int eventID, int day) //2, 3, 4, 5 hour span
    {

        for (int time = startTime; time <= endTime; time++)
        {
            setEvent(time, eventID, day);
        }
    }

    /**
     * Removes an event
     * author: Steph
     * @param event the event to be removed
     */
    public void removeEvent(Integer event) {
        for (LinkedHashMap<Integer, Integer> date : schedule2.values()){
            for (int time : date.keySet()){
                if (Objects.equals(date.get(time), event)) {
                    date.put(time, null);
                }
            }
        }
    }


    /**
     * Returns room name
     * @author Stephanie
     * @return the name of the room
     */
    public String getName(){return name;}


    /**
     * Returns the properties of the room
     * @return List of all the room properties
     */
    public ArrayList<String> getProperties() {
        return properties;
    }

    /**
     * Adds a new property to the room
     * @author: Steph
     * @param name property to be added
     */
    public void addProperty(String name){
        if (!properties.contains(name)) {
            properties.add(name);
        }
    }

    /**
     * Removed a property of the room
     * @author: Stephanie
     * @param name property to be removed
     */
    public void removeProperty(String name){
        properties.remove(name);
    }

    /**
     * Returns ID of room
     * @author Stephanie
     * @return integer
     */
    public int getId(){return id;}

    /**
     * Get the open time of the room
     * @author Stephanie
     * @return the open time
     */
    public int getOpenTime(){return openTime;}

    /**
     * Get the close time of the room
     * @author Stephanie
     * @return the close time
     */
    public int getCloseTime(){return closeTime;}

    /**
     * Get the maximum capacity for this room
     * @return the capacity for this room
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Returns the map of the room with all the times and the dates
     * author: Steph
     * @return see details above
     */
    public  LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> getSchedule2(){return schedule2;}

    /**
     * Add a new date to the schedule
     * @param date a date
     */
    public void addDate(Integer date) {
        LinkedHashMap<Integer, Integer> daySchedule = new LinkedHashMap<>();
        for (int i = openTime; i<=closeTime; i++){
            daySchedule.put(i, null);
        }
        schedule2.put(date, daySchedule);
    }

    /**
     * Validate whether this room is available.
     * @param time the time
     * @param date the date
     * @return true if available
     */
    public boolean validateTime(Integer time, Integer date) {
        if (time < openTime || time > closeTime) {
            return false;
        }
        if (schedule2.get(date) == null) {
            return false;
        }
        return schedule2.get(date).get(time) == null;
    }

    /**
     * Check whether this room has desired properties
     * @param props properties
     * @return true if it has
     */
    public boolean hasProps(ArrayList<String> props) {
        for (String prop : props) {
            if (!properties.contains(prop)) {
                return false;
            }
        }
        return true;
    }
}
