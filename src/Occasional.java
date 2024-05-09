import java.util.ArrayList;
import java.time.LocalDate;
import java.io.*;
import java.util.Map;

public class Occasional extends User implements Serializable{

    public Occasional(){
        super();
    }

    public Occasional(String name, String username, String userType, LocalDate dateOfBirth, double height, double weight, double calories, String address, String email, String password, int avgHR, ArrayList<Activity> activitiesList, ArrayList<WorkoutPlan> workoutPlansList) {
        super(name, username, userType, dateOfBirth, height, weight, calories, address, email, password, avgHR, activitiesList, workoutPlansList);
    }

    public Occasional(User other){
        super(other);
    }

    public User clone(){
        return new Occasional((this));
    }

    public String toString() {
        return super.toString();
    }

    public double caloriesFactor() {
        if(this.getAvgHR() > 85){
            return 1.2; // each 2 less
        }
        else return 1;
    }
}