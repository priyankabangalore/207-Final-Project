/*
@layer: Entity
 */

package Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Date implements Serializable {
    private final int date; //Saved as month-day, ex: 0313 is March 13
    private final ArrayList<Integer> eventIds;

    /**
     * Object representing multiple days of the conference
     * @author: Steph
     * @param day the date held as a 4 letter integer month/day. EX: 0310 is March 13
     */
    public Date(int day) {
        this.date = day;
        this.eventIds = new ArrayList<>();
    }

    /**
     * Returns the date as is
     * @author: Steph
     * @return the date in its 4 digit form
     */
    public Integer getDate() {
        return date;
    }

    /**
     * Gets the events that are being held on this day
     * @author: Steph
     * @return The events being held on this day in Ids
     */
    public ArrayList<Integer> getEventIds() {
        return eventIds;
    }

    /**
     * Add a new event to this day
     * @author: steph
     * @param eventID the event to be added
     */
    public void addEvent(Integer eventID){
        eventIds.add(eventID);
    }

    /**
     * Remove an event from this day
     * @author: Steph
     * @param eventID the event to be removed
     */
    public void removeEvent(Integer eventID){
        eventIds.remove(eventID);
    }


}


