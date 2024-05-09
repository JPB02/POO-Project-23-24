import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Fitness implements Serializable {
    private Map<String, User> userMap;
    private Map<String, Activity> activityMap;
    private LocalDate currDate;

    public Fitness() {
        this.userMap = new HashMap<>();
        this.activityMap = new HashMap<>();
        this.currDate = LocalDate.now();
    }

    public Fitness(Map<String, User> userMap, Map<String, Activity> activityMap, ArrayList<WorkoutPlan> workoutPlans, LocalDate date) {
        this.userMap = userMap;
        this.activityMap = activityMap;
        this.currDate = currDate;
    }

    public Fitness(Fitness fit) {
        this.userMap = new HashMap<>(fit.getUserMap());
        this.activityMap = new HashMap<>(fit.getActivityMap());
        this.currDate = LocalDate.of(fit.getCurrDate().getYear(), fit.getCurrDate().getMonth(), fit.getCurrDate().getDayOfMonth());
    }


    public Fitness clone() {
        return new Fitness(this);
    }

    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("Users: ").append(userMap.toString()).append('\n');
        sb.append("Activities: ").append(activityMap.toString()).append("\n");
        return sb.toString();
    }

    public Map<String, User> getUserMap() {
        return userMap.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public void setUserMap(Map<String, User> userMap) {
        this.userMap =  userMap.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public Map<String, Activity> getActivityMap() {
        return activityMap.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public void setActivityMap(Map<String,Activity> activityMap) {
        this.activityMap = activityMap.entrySet().stream().collect(Collectors.toMap(k->k.getKey(),v->v.getValue().clone()));
    }

    public LocalDate getCurrDate() {
        return currDate;
    }

    public ArrayList<Activity> getActivityByType(String activityType){
        ArrayList<Activity> activities = new ArrayList<>();
        for (Map.Entry<String, Activity> entry : activityMap.entrySet()) {
            Activity activity = entry.getValue();
            if (activity.getActivityType().equals(activityType)) {
                activities.add(activity);
            }
        }
        return activities;
    }

    public void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("../file.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.flush();
            fileOut.close();
        } catch (IOException i) {
            System.out.println("Saving error");
            i.printStackTrace();
        }
    }

    public Fitness load() {
        Fitness fit = new Fitness();
        try {
            FileInputStream fileIn = new FileInputStream("../file.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            fit = (Fitness) in.readObject();
            in.close();
            fileIn.close();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Loading error");
            e.printStackTrace();
        }
        return fit;
    }

}