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
        return this.date;
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
        return this.iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public double getTotalCalories() {
        return this.totalCalories;
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

    public WorkoutPlan allocateRandomActivity(Fitness fit,User user, String type, int numberActivities, LocalDate date) {
        Random rnd = new Random();

        if(user == null) {
            System.out.println("User not found...");
            return null;
        }

        ArrayList<Activity> activities= fit.getActivityByType(type);
        int randomIndex = rnd.nextInt(activities.size());

        WorkoutPlan workoutPlan = new WorkoutPlan();

        for(int i = 0; i < numberActivities; i++){
            switch(type) {
                case "Distance":
                    String distanceActivityID = activities.get(randomIndex).getActivityID();
                    allocateRandomDistance(fit,rnd, date, distanceActivityID);
                    this.iterations++;
                    break;

                case "DistanceAltitude":
                    String distanceAltitudeActivityID = activities.get(randomIndex).getActivityID();
                    allocateRandomDistanceAltitude(fit,rnd, date, distanceAltitudeActivityID);
                    this.iterations++;
                    break;

                case "Weight-lifting":
                    String weightliftingActivityID = activities.get(randomIndex).getActivityID();
                    allocateRandomWeightlifting(fit,rnd, date, weightliftingActivityID, user);
                    this.iterations++;
                    break;
                case "Body-weight":
                    String activityID = activities.get(randomIndex).getActivityID();
                    allocateRandomBodyweight(fit,rnd, date, activityID);
                    this.iterations++;
                    break;

                default:
                    System.out.println("Activity type not found...");
                    break;
            }
        }

        System.out.println("Workout plan allocated successfully...");
        return workoutPlan;
    }

    public void allocateRandomDistance(Fitness fit, Random rnd, LocalDate date, String ID) {
        String activityID = ID + (this.iterations+1);
        String type = "Distance";

        int duration = 1+ rnd.nextInt(179);
        double distance = 1+ rnd.nextDouble(29);

        if(ID.equals("Running")) {
            double pace = 1 + (rnd.nextDouble() * 4);
            boolean isHardRunning = fit.isHardRunning(pace);
            int steps = 1000 * (int)(distance);
            Activity newRunning = new Running(activityID, type, date, duration, distance,pace,steps, isHardRunning);
            this.activities.add(newRunning);
        }
        else {
            boolean isHardDistance = fit.isHardDistance(duration,distance);
            Activity newDistance = new Distance(activityID, type, date, duration, distance, isHardDistance);
            this.activities.add(newDistance);
        }

    }


    public void allocateRandomDistanceAltitude(Fitness fit, Random rnd, LocalDate date, String ID) {
        String activityID = ID + (this.iterations+1);
        String type = "DistanceAltitude";

        int duration = 1+ rnd.nextInt(179);
        double distance = 1+ rnd.nextDouble(29);
        double altitude = 1+ rnd.nextDouble(1000);

        if(ID.equals("MountainBike")) {
            double pace = 1 + (rnd.nextDouble() * 4);
            boolean isHardMountainBike = fit.isHardMountainBike(pace);
            int steps = 1000 * (int)(distance);
            Activity newMountainBike = new MountainBike(activityID, type, date, duration, distance,altitude,pace, isHardMountainBike);
            this.activities.add(newMountainBike);
        }
        else {
            boolean isHardDistanceAltitude = fit.isHardDistanceAltitude(duration,distance,altitude);
            Activity newDistanceAltitude = new DistanceAltitude(activityID, type, date, duration, distance,altitude, isHardDistanceAltitude);
            this.activities.add(newDistanceAltitude);
        }

    }

    public void allocateRandomBodyweight(Fitness fit, Random rnd, LocalDate date, String ID) {
        String activityID = ID + (this.iterations+1);
        String type = "Body-weight";

        int duration = 1+ rnd.nextInt(179);
        int reps = 1+rnd.nextInt(20);
        int sets = 1+rnd.nextInt(4);

        if(ID.equals("Squat")) {
            int rpe = 1+rnd.nextInt(10);
            boolean isHardSquat = fit.isHardSquats(reps,sets,rpe);
            Activity newSquat = new Squat(activityID, type, date, duration, reps,sets, isHardSquat, rpe);
            this.activities.add(newSquat);
        }
        else {
            boolean isHardBodyweight = fit.isHardBodyWeight(reps,sets);
            Activity newBodyweight = new Bodyweight(activityID, type, date, duration, reps, sets, isHardBodyweight);
            this.activities.add(newBodyweight);
        }

    }

    public void allocateRandomWeightlifting(Fitness fit, Random rnd, LocalDate date, String ID, User user) {
        String activityID = ID + (this.iterations+1);
        String type = "Weight-lifting";

        int duration = 1+ rnd.nextInt(179);
        double weight = 1+rnd.nextDouble(user.getWeight()-1);
        int reps = 1+rnd.nextInt(20);
        int sets = 1+rnd.nextInt(4);

        if(ID.equals("BenchPress")) {
            boolean incline = rnd.nextBoolean();
            boolean isHardBenchPress = fit.isHardBenchPress(reps,sets,weight, incline, user);
            Activity newBenchPress = new BenchPress(activityID, type, date, duration, isHardBenchPress, reps,sets, weight, incline);
            this.activities.add(newBenchPress);
        }
        else {
            boolean isHardWeightlifting = fit.isHardWeightlifting(reps,sets,weight,user);
            Activity newWeightlifting = new Weightlifting(activityID, type, date, duration, isHardWeightlifting,reps, sets,weight);
            this.activities.add(newWeightlifting);
        }

    }

    public String toString() {
        return new String(
                "Activities in plan: "+ this.activities.toString() + "\n"
                        + "\n-Date: "+ this.date
                        + "\n-Days: " + this.iterations);
    }


}
