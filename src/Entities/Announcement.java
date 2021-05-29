/*
@layer: Entity
 */

package Entities;

import java.util.ArrayList;

public class Announcement extends Conversation{

    /**
     * Constructor for Announcement
     */
    public Announcement(ArrayList<Integer> participants, int ID, String name) {
        super(participants, ID, name);
    }

    /**
     * Checks if announcement is one-way.
     * @return true iff announcement is one-way
     */
    @Override
    public boolean isOneway() {
        return true;
    }
}
