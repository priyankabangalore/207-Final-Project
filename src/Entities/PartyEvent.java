/*
@layer: Entity
@collaborators: Event Entity
 */

package Entities;

import java.util.ArrayList;

public class PartyEvent extends Event { //no speakers

    /**
     * An event with no speakers (party)
     *
     * @author Priyanka
     * @param name:      name of the party
     * @param room:      the room of the party
     * @param startTime: the time when the party starts
     * @param endTime:   time the party ends
     * @param ID:        the ID of the party
     * @param date:      date of party
     * @param desc: event description
     * @param prop: properties of this event
     * @param capacity: the capacity for this event
     */
    public PartyEvent(String name, int room, int startTime, int ID, int endTime, int capacity, int date,
                      ArrayList<String> prop, String desc) {
        super(name, room, startTime, endTime, ID, prop, capacity, date, desc);
    }
}

