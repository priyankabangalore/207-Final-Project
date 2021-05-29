/*
@layer: Entity
 */

package Entities;

public class AdminUser extends Organizer {

    /**
     * Constructor for AdminUser
     * @param id ID of user
     * @param username username of user
     * @param password password of user
     * @param email email of user
     */
    public AdminUser(String username, String password, int id, String email) {
        super(username, password, id, email);
    }

    /**
     * Gets request permission level
     * @return request permission
     */
    @Override
    public int getPermission() {
        return 3;
    }
}
