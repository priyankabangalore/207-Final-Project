/*
@layer: Use Case
@collaborators: AdminUser
 */

package UseCases;

import Entities.AdminUser;

import java.util.ArrayList;
import java.util.HashMap;

public class AdminUserManager {

    private final ArrayList<AdminUser> adminUsers;

    /**
     * Constructor for AdminUserManager
     * @param adminUsers list of admin users in the system
     */
    public AdminUserManager(ArrayList<AdminUser> adminUsers){ this.adminUsers = adminUsers; }

    /**
     * Get a list of all admin users in the system
     * @return list of AdminUsers
     */
    public ArrayList<AdminUser> getAdminUserObject(){ return adminUsers;}

    /**
     * Return a hashmap for information about admin users
     * @return hashmap where
     *         K: the username for the admin user
     *         V: the ID for the admin user
     */
    public HashMap<String, Integer> getAdminUser() {
        HashMap<String, Integer> adminMap = new HashMap<>();
        for (AdminUser a : this.adminUsers) {
            int ID = a.getID();
            adminMap.put(a.getUsername(), ID);
        }
        return adminMap;
    }

    /**
     * Get the ID for the given admin user
     * @param admin the AdminUser
     * @return ID of the AdminUser
     */
    public Integer getAdminUserID(AdminUser admin) {
        return admin.getID();
    }

    /**
     * Get the AdminUser with the given ID
     * @param adminID ID of the admin user
     * @return AdminUser object iff it exists
     */
    public AdminUser getAdminUser(Integer adminID) {
        for (AdminUser adminUser : adminUsers) {
            if (adminUser.getID() == adminID) {
                return adminUser;
            }
        }
        return null;
    }

    /**
     * Add a new admin user to the system
     * @param adminUser the AdminUser object to be added
     */
    private void addAdminUser(AdminUser adminUser) {
        this.adminUsers.add(adminUser);
    }

    /**
     * Create a new AdminUser
     * @param username the username for the new AdminUser
     * @param password the password for the new AdminUser
     * @param userManager UserManager
     * @param email the email address for the new AdminUser
     * @return the ID for the new AdminUser
     */
    public int createAdminUser(String username, String password, UserManager userManager, String email) {
        int id = userManager.generateID();
        AdminUser adminUser = new AdminUser(username, password, id, email);
        addAdminUser(adminUser);
        userManager.addUser(adminUser);
        return id;
    }

    /**
     * Get the number of registered admins
     * @return the size of the admin list
     */
    public int getNumberOfAdmins() {
        return adminUsers.size();
    }
}
