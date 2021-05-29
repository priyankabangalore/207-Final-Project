/*
@layer: Entity
@collaborators: Event Entity
 */

package Entities;

import java.util.ArrayList;

public class PanelEvent extends Event {//more than 1 speaker

    private ArrayList<Integer> speakers;

    /**
     * An event with more than 1 speaker (panel event)
     * @author Priyanka
     * @param name: name of the panel
     * @param room: the room of the panel
     * @param startTime: the time when the panel starts
     * @param endTime time when panel ends
     * @param ID: the ID of the panel
     * @param date: date of event
     * @param desc: description of event
     * @param prop: the properties for this event
     * @param capacity: the capacity for this event
     * @param speakers: the speakers for this event
     */
    public PanelEvent(String name, int room, int startTime, int endTime, int ID,
                      int capacity, int date, ArrayList<String> prop, ArrayList<Integer> speakers, String desc){
        super(name, room, startTime, endTime, ID, prop, capacity, date, desc);

        this.speakers = speakers;
    }

    /**
     * Get the speakers for the panel
     * @author Priyanka
     * @return list of speaker IDs
     */
    public ArrayList<Integer> getSpeakers() {
        return speakers;
    }

    /**
     * Sets speakers for the panel
     * @author Priyanka
     * @param speakerIDs: list of IDs of all speakers
     */
    public void setSpeakers(ArrayList<Integer> speakerIDs) {
        this.speakers = speakerIDs;
    }

    /**
     * Add a speaker to the list of speakers for the panel
     * @param speakerID: ID for the speaker being added
     */
    public void addSpeaker(Integer speakerID) {
        if (this.speakers.contains(speakerID)) {
            this.speakers.add(speakerID);
        }
    }

    /**
     * Removes a single speaker from the panel
     * @author Priyanka
     * @param speakerID: ID of the speaker
     */
    public void removeSpeaker(int speakerID) {speakers.remove(speakerID);}
}

