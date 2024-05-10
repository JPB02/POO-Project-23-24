import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorkoutPlan implements Serializable {

    private LocalDate date;
    private List<Activity> activities;
    private int iterations;
    private double totalCalories;

    public WorkoutPlan() {
        this.date = LocalDate.now();
        this.activities = new ArrayList<>();
        this.iterations = 0;
    }

    public WorkoutPlan(LocalDate date, List<Activity> activities, int iterations) {
        this.date = date;
        this.activities = new ArrayList<>(activities);
        this.iterations = iterations;
    }

    public WorkoutPlan(WorkoutPlan other) {
        this.date = other.getDate();
        this.activities = other.getActivities();
        this.iterations = other.getIterations();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Activity> getActivities() {
        return new ArrayList<>(this.activities);
    }

    public void setActivities(List<Activity> activities) {
        this.activities = new ArrayList<>(activities);
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public double getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(double totalCalories) {
        this.totalCalories = totalCalories;
    }

    public int getPlanSize() {
        return this.activities.size();
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public double caloriesBurned(User user) {
        double calories = 0.0;

        for (Activity activity : activities) {
            calories += activity.calories(user);
        }
        this.totalCalories = calories;

        return calories;
    }
/*
    public WorkoutPlan allocateRandomActivity(Fitness fit,User user, String type, int numberActivities, LocalDate date) {
        Random rnd = new Random();
        if(user == null) {
            System.out.println("User not found...");
            return null;
        }

        ArrayList<Activity> activities= fit.getActivityByType(type);

        WorkoutPlan workoutPlan = new WorkoutPlan();

        for(int i = 0; i < numberActivities; i++){
            switch(type) {
                case "Distance":
                    workoutPlan.addActivity(allocateRandomRunning(activities,rnd, date));
                    break;
                case "Distance&Altitude":
                    workoutPlan.addActivity(allocateRandomMountainBike(activities,rnd, date));
                    break;
                case "Weightlifting":
                    workoutPlan.addActivity(allocateRandomBenchPress(activities,rnd, date));
                    break;
                case "Body-weight":
                    workoutPlan.addActivity(allocateRandomSquat(activities,rnd, date));
                    break;
                default:
                    System.out.println("Activity type not found...");
                    return null;
            }
        }

        System.out.println("Workout plan allocated successfully...");

        return workoutPlan;
    }

    public Running allocateRandomRunning(ArrayList<Activity> activities, Random rnd, LocalDate date) {
        ArrayList<String> names = new ArrayList<>();
        for (Activity activity : activities) {
            names.add(activity.getActivityID());
        }

        String activityID = names.get(rnd.nextInt(names.size()));
        String type = "Distance";
        int duration = rnd.nextInt(120); // in minutes
        double distance = 1 + (rnd.nextDouble() * 41); // max == marathon(42km), min == 1km
        double pace = 1 + (rnd.nextDouble() * 4);
        int steps = 1000 * (int)(distance);

        return new Running(activityID, type, date, duration, distance,pace,steps);
    }

    public BenchPress allocateRandomBenchPress(ArrayList<Activity> activities, Random rnd, LocalDate date) {
        ArrayList<String> names = new ArrayList<>();
        for(Activity activity : activities){
            names.add(activity.getActivityID());
        }

        String activityID = names.get(rnd.nextInt(names.size()));
        String type = "Weightlifting";
        int duration = rnd.nextInt(120) + 1;
        int weight = rnd.nextInt(100) + 1;
        int reps = rnd.nextInt(8) + 1;
        int sets = rnd.nextInt(3) + 1;

        return new BenchPress(activityID, type, date, duration, reps, sets, weight, isHard);
    }

    public Squat allocateRandomSquat(ArrayList<Activity> activities, Random rnd, LocalDate date) {
        ArrayList<String> names = new ArrayList<>();
        for(Activity activity : activities){
            names.add(activity.getActivityID());
        }

        String activityID = names.get(rnd.nextInt(names.size()));
        String type = "Body-weight";
        int duration = rnd.nextInt(20) + 5; // max 21min , min 5min
        int reps = rnd.nextInt(20) + 10;    // max 30 reps, min 10 reps
        int sets = rnd.nextInt(3) + 1;      // max 4 sets, min 1 set

        return new Squat(activityID, type, date, duration, reps, sets);
    }

    public MountainBike allocateRandomMountainBike(ArrayList<Activity> activities, Random rnd, LocalDate date) {
        ArrayList<String> names = new ArrayList<>();
        for(Activity activity : activities){
            names.add(activity.getActivityID());
        }

        String activityID = names.get(rnd.nextInt(names.size()));
        String type = "Distance&Altitude";
        int duration = rnd.nextInt(100) + 20;   // max 120min , min 20min
        double distance = 5 + (rnd.nextDouble() * 37); // max == marathon(42km), min == 5km
        double altitude = 50 + (rnd.nextDouble() * 500); // max == 550metres, min == 50metres
        double pace = 1000*distance;
        return new MountainBike(activityID, type, date, duration, distance, altitude, pace);
    }
*/
    public String toString() {
        return new String(
                "Activities in plan: "+ this.activities.toString() + "\n"
                        + "\n-Date: "+ this.date
                        + "\n-Days: " + this.iterations);
    }


}
