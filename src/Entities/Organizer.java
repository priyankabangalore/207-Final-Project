/*
@layer: Entity
 */

package Entities;

/**
 * An organizer of this system
 */
public class Organizer extends User {

    /**
     * Organizer instance variable that represents an organizer
     * @param username Organizer's username
     * @param password Organizer's password
     * @param id Organizer's unique ID
     * @param email email of organizer
     */
    public Organizer(String username, String password, int id, String email) {
        super(username, password, id, email);
    }

    /**
     * Gets permission level of Organizer user
     * @return int of permission level
     */
    @Override
    public int getPermission() {
        return 2;
    }
}
