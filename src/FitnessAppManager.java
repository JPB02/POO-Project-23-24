import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FitnessAppManager {
    private Map<String, User> users;
    private Fitness fitness;

    public FitnessAppManager() {
        this.users = new HashMap<>();
        this.fitness = new Fitness();
    }

    // Method to register a new user
    public void registerUser(User user) {
        users.put(user.getEmail(), user);
    }

    // Method to log in a user
    public boolean login(String email, String password) {
        User user = users.get(email);
        if (user != null && user.getPassword().equals(password)) {
            // Successful login
            return true;
        }
        return false;
    }

    // Method to add an activity
    public void addActivity(User user, Activity activity) {
        user.addActivityToUser(activity);
        fitness.getActivityMap().put(activity.getActivityID(), activity);
    }

    // Method to add a workout plan
    public void addWorkoutPlan(User user, WorkoutPlan workoutPlan) {
        user.addWorkoutPlanToUser(workoutPlan);
        // Additional logic if needed
    }
}