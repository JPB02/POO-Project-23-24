import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

public abstract class User implements Serializable {
    /*
    i) three types of user: professional, amateur, occasional
        each type of user must have different formulas associated for calories burned arithmetics

    ii) information about each user: userid(email), name, address, email, average heart rate

    iii) all registered activities must be saved, including isolated and training plan activities
    */

    // variables
    private String                  name; // DONE
    private String                  username; // DONE
    private String                  userType; // DONE
    private LocalDate               dateOfBirth; // DONE
    private int                     height; // DONE
    private double                  weight; // DONE
    private double                  calories;
    private String                  address; //DONE
    private String                  email; // DONE
    private String                  password; // DONE
    private int                     avgHR; // DONE
    private ArrayList<Activity>     activitiesList;
    private ArrayList<WorkoutPlan>  workoutPlansList;

    public User() {
        this.name = "";
        this.username = "";
        this.userType = "";
        this.dateOfBirth = LocalDate.EPOCH;
        this.height = 0;
        this.weight = 0;
        this.calories = 0;
        this.address = "";
        this.email = "";
        this.password = "";
        this.avgHR = 0;
        this.activitiesList = new ArrayList<>();
        this.workoutPlansList = new ArrayList<>();

    }

    public User(String name, String username, String userType, LocalDate dateOfBirth ,int height, double weight, double calories, String address, String email, String password, int avgHR, ArrayList<Activity> activitiesList, ArrayList<WorkoutPlan> workoutPlansList) {
        this.name = name;
        this.username = username;
        this.userType = userType;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.weight = weight;
        this.calories = calories;
        this.address = address;
        this.email = email;
        this.password = password;
        this.avgHR = avgHR;
        this.activitiesList = new ArrayList<>(activitiesList);
        this.workoutPlansList = new ArrayList<>(workoutPlansList);
    }

    public User(User other) {
        this.name = other.getName();
        this.username = other.getUsername();
        this.userType = other.getUserType();
        this.dateOfBirth = other.getDateOfBirth();
        this.height = other.getHeight();
        this.weight = other.getWeight();
        this.calories = other.getCalories();
        this.address = other.getAddress();
        this.email = other.getEmail();
        this.password = other.getPassword();
        this.avgHR = other.getAvgHR();
        this.activitiesList = other.getActivitiesList();
        this.workoutPlansList = other.getWorkoutPlansList();
    }

    // getters & setters
    public ArrayList<Activity> getActivitiesList() {
        return new ArrayList<>(this.activitiesList);
    }

    public void setListaAtividades(ArrayList<Activity> activitiesList) {
        this.activitiesList = activitiesList;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<WorkoutPlan> getWorkoutPlansList(){
        return new ArrayList<WorkoutPlan>(this.workoutPlansList);
    }

    public void setWorkoutPlansList(ArrayList<WorkoutPlan> workoutPlan){
        this.workoutPlansList = workoutPlan;
    }

    public void addActivityToUser(Activity activity){
        this.activitiesList.add(activity);
    }

    public void removeActivityByID(User user, String activityID) {
        for (Activity activity : this.activitiesList) {
            if (activity.getActivityID().equals(activityID)) {
                double calories = activity.calories(user);
                user.removeCalories(calories);
                this.activitiesList.remove(activity);
                break;
            }
        }
    }

    public void addWorkoutPlanToUser(WorkoutPlan workoutPlan){
        this.workoutPlansList.add(workoutPlan);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setUserType(String userType){
        this.userType = userType;
    }

    public String getUserType(){
        return this.userType;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }

    private boolean validWeight(double weight) {
        return weight >= 10 && weight <= 200;
    }


    public boolean setWeight(double weight){
        if((validWeight(weight))) {
            this.weight = weight;
            return true;
        } else {
            return false;
        }
    }

    public double getWeight() {
        return this.weight;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setAvgHR(int avgHR) {
        this.avgHR = avgHR;
    }

    public int getAvgHR() {
        return this.avgHR;
    }

    public double getCalories() {
        return this.calories;
    }

    public void setCalories(double calories) {
        this.calories += calories;
    }

    public void removeCalories(double calories) {
        this.calories -= calories;
    }


    public void saveUser() {
        try {
            FileOutputStream fileOut = new FileOutputStream(username + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);  // Serialize user
            out.writeObject(activitiesList);  // Serialize activities list
            out.close();
            fileOut.close();
        } catch (IOException i) {
            System.out.println("Saving user error");
            i.printStackTrace();
        }
    }

    // Deserialization method to load user along with its activities
    public static User loadUser(String username) {
        try {
            FileInputStream fileIn = new FileInputStream(username + ".ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            User user = (User) in.readObject();  // Deserialize user
            ArrayList<Activity> activities = (ArrayList<Activity>) in.readObject();  // Deserialize activities list
            user.setListaAtividades(activities);  // Set activities list to loaded activities
            in.close();
            fileIn.close();
            return user;
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Loading user error");
            e.printStackTrace();
            return null;
        }
    }

    // equals

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return      this.name.equals(that.getName())
                &&  this.userType.equals(that.getUserType())
                &&  this.dateOfBirth.equals(that.getDateOfBirth())
                &&  this.height == that.getHeight()
                &&  this.weight == that.getWeight()
                &&  this.address.equals(that.getAddress())
                &&  this.email.equals(that.getEmail())
                &&  this.password.equals(that.getPassword())
                &&  this.avgHR == that.getAvgHR()
                &&  this.activitiesList.equals(that.getActivitiesList());
    }

    @Override
    public String toString() {
        return  "\nAccount Information:" +
                "\n--------------------" +
                "\nName: " + this.name +
                "\nEmail: " + this.email +
                "\nType: " + this.userType +
                "\nDate of Birth: " + this.dateOfBirth +
                "\nHeight: " + this.height +
                "\nWeight: " + this.weight +
                "\nAddress: " + this.address +
                "\nAverage Heart Rate: " + this.avgHR +
                "\nActivities List: " + this.activitiesList +
                "\nTotal Calories Burned: " + this.calories;
    }

    public abstract User clone();
    public abstract double caloriesFactor();

}
