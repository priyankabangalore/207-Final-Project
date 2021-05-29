/*
@layer: Gateway
 */

package Gateways;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

@SuppressWarnings("unchecked")
interface FileInteractable {
    /**
     * Saves info into files for running program in the future
     * @param obj the Object being saved
     * @param filepath Where it's being saved
     */
    default void save(Object obj, String filepath) {

        String[] paths = filepath.split("[\\\\/]", 0);
        String dir = "";
        for (int i = 0; i < Array.getLength(paths) - 1; i++) {
            dir = paths[i] + "/";
        }
        new File(dir).mkdirs();

        try {
            OutputStream file = new FileOutputStream(filepath);
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer);

            // serialize the Map
            output.writeObject(obj);
            output.close();

            // System.out.println("Serialized data is saved in " + filepath);
        } catch (IOException e) {
            // System.out.println("Something is wrong.");
            e.printStackTrace();
        }
    }

    /**
     * Loading files when program is re-started
     * @param filepath The file being opened
     * @return arraylist objs
     */
    default Object loadList(String filepath) {
        ArrayList<Object> objs;
        try {
            FileInputStream file = new FileInputStream(filepath);
            ObjectInputStream buffer = new ObjectInputStream(file);
            objs = (ArrayList<Object>) buffer.readObject();
            buffer.close();
            file.close();
        } catch (IOException i) {
            objs = new ArrayList<>();
        } catch (ClassNotFoundException i) { // should never happen since we are using built-in class ArrayList<Object>
            // System.out.println("Class not found");
            i.printStackTrace();
            objs = new ArrayList<>();
        }
        return objs;
    }
}
