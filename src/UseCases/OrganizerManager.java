/*
@layer: Use Case
@collaborators: Organizer entity, UserManager
 */

package UseCases;

import Entities.Organizer;

import java.util.ArrayList;

public class OrganizerManager {

    private final ArrayList<Organizer> organizers;

    /**
     * Constructor for OrganizerManager
     * @param organizers: list of all Organizers in system
     */
    public OrganizerManager(ArrayList<Organizer> organizers) {
        this.organizers = organizers;
    }

    /**
     * Get all the Organizer stores in this manager
     *
     * @return list of organizers
     */
    public ArrayList<Organizer> getOrganizerObjects() {
        return organizers;
    }

    /**
     * Get the number of registered organisers
     *
     * @return the size of the organisers list
     */
    public int getNumberOfOrganisers() {
        return organizers.size();
    }

    /**
     * Add organiser to the list of all organisers
     *
     * @param organizer the organiser that needs to be added
     */
    private void addOrganizer(Organizer organizer) {
        this.organizers.add(organizer);
    }

    /**
     * Create a new organiser
     *
     * @param username the username for the new organiser
     * @param password the password for the new password
     * @param userManager UserManager
     * @param email the email address for the new organiser
     */
    public int createOrganizer(String username, String password, UserManager userManager, String email) {
        int id = userManager.generateID();
        Organizer organizer = new Organizer(username, password, id, email);
        addOrganizer(organizer);
        userManager.addUser(organizer);
        return id;
    }
}
