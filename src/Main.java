import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        FitnessAppManager appManager = new FitnessAppManager();

        // Creating a user
        Amateur user = new Amateur();
        user.setName("John");
        user.setUsername("john");
        user.setUserType("Amateur");
        user.setDateOfBirth(LocalDate.EPOCH);
        user.setHeight(175);
        user.setWeight(67);
        user.setCalories(1070);
        user.setAddress("rua do caralho");
        user.setEmail("john@gmail.com");
        user.setPassword("password");
        user.setAvgHR(70);

        appManager.registerUser(user);

        // Creating an activity
        Running runningActivity = new Running("1", "Running", LocalDate.now(), 30, 5.0);

        // Adding the activity to the user
        appManager.addActivity(user, runningActivity);

        // Displaying the user's activities
        System.out.println("User: \n" + user.toString());;

        System.out.println("User's activities:");
        for (Activity activity : user.getActivitiesList()) {
            System.out.println(activity);
        }
    }
}
