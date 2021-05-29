/*
@layer: Entity
@collaborators: Event Entity
 */

package Entities;

import java.util.ArrayList;

public class TalkEvent extends Event { //1 speaker

    private int speaker;

    /**
     * Represents an Instance Event
     *
     * @author Priyanka
     * @param title     name of the event
     * @param room      the room in which the event takes place
     * @param startTime the time in which the event starts
     * @param endTime   the time in which the event ends
     * @param speaker   the speaker who speaks in said room
     * @param ID        the ID of the event
     * @param prop      the properties of the event
     * @param capacity  the maximum capacity for the event
     * @param date      date of the event
     * @param desc the description of the event
     */
    public TalkEvent(String title, int room, int startTime, int endTime, int speaker, int ID, ArrayList<String> prop,
                     int capacity, int date, String desc) {
        super(title, room, startTime, endTime, ID, prop, capacity, date, desc);
        this.speaker = speaker;
    }

    /**
     * Get the speakers for the panel
     * @author Priyanka
     * @return list of speaker IDs
     */
    public int getSpeaker() {
        return speaker;
    }

    /**
     * Sets speakers for the panel
     * @author Priyanka
     * @param speakerID: list of IDs of all speakers
     */
    public void setSpeaker(int speakerID) {
        this.speaker = speakerID;
    }
}
