import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Create activities map (optional)
        Map<String, Activity> activities = new HashMap<>();
        // activities.put("activity1", new Activity(/* parameters */));


        // Create a user
        User user = new User();
        user.setName("John Doe");                                                   // name
        user.setUserType(UserType.Professional);                                    // userType
        user.setGender(Gender.Male);                                                // gender
        user.setDateOfBirth(LocalDate.of(1990, 5, 15));      // dateOfBirth
        user.setHeight(180);                                                        // height
        user.setWeight(75);                                                         // weight
        user.setAddress("123 Street, City, Country");                               // address
        user.setEmail("john@example.com");                                          // email
        user.setPassword("password123");                                            // password
        user.setAvgHR(70.5);                                                        // avgHR

        Running run = new Running();
                run.setDistance(10);
                run.setAltitude(500);
                run.setDate(LocalDate.now());
                run.setDescription("running in wind");
                run.setDuration(40);
                run.setUser(user);

        activities.put(run.getActivityID(), run);

        user.setActivities(activities);

        // Display the user information
        System.out.println(user);
        System.out.println(activities);
    }
}
