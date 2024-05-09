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
        MountainBike mountainBikeActivity = new MountainBike("2", "Mountain Bike", LocalDate.now(), 40, 16, 500);
        Squat squatActivity = new Squat("3", "Squat", LocalDate.now(), 10, 20, 3);

        // Adding the activity to the user
        appManager.addActivity(user, runningActivity);

        // Displaying the user's activities
        System.out.println("User: \n" + user.toString());;

        System.out.println("User's activities:");
        for (Activity activity : user.getActivitiesList()) {
            System.out.println(activity);
        }

        // Creating a workout plan
        WorkoutPlan workoutPlan = new WorkoutPlan();
        workoutPlan.addActivity(runningActivity);
        workoutPlan.addActivity(mountainBikeActivity);
        workoutPlan.addActivity(squatActivity);
        workoutPlan.setIterations(3); // Set the number of iterations for the workout plan

        // Adding the workout plan to the user
        appManager.addWorkoutPlan(user, workoutPlan);

        // Displaying the user's workout plans
        System.out.println("User's workout plans:");
        for (WorkoutPlan plan : user.getWorkoutPlansList()) {
            System.out.println(plan);
        }
    }
}
