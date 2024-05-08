import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserSerializationUtil implements Serializable{

    public static void saveUsersToFile(Map<String, User> usersMap, String filePath) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(usersMap);
            System.out.println("Users saved to file: " + filePath);
        } catch (IOException e) {
            System.out.println("Error saving users to file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, User> loadUsersFromFile(String filePath) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            Map<String, User> usersMap = (Map<String, User>) inputStream.readObject();
            System.out.println("Users loaded from file: " + filePath);
            return usersMap;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading users from file: " + e.getMessage());
            return new HashMap<>(); // return an empty map if file cannot be loaded
        }
    }
}
