import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Gere os dados para a aplicação
 * Tem suporte para serialization para guardar dados de forma persistente.
 */
public class Fitness implements Serializable {
    private Map<String, User> userMap;
    private Map<String, Activity> activityMap;
    private LocalDate currDate;


    /**
     * Construtor por omissao
     * Inicializa os maps e atualiza a data para a data atual.
     */
    public Fitness() {
        this.userMap = new HashMap<>();
        this.activityMap = new HashMap<>();
        this.currDate = LocalDate.now();
    }

    /**
     * Construtor parametrizado para começar a aplicacao
     *
     * @param userMap Um map dos ID's dos objetos utilizadores
     * @param activityMap Um map dos ID's dos objetos atividades
     * @param currDate Data atual
     */
    public Fitness(Map<String, User> userMap, Map<String, Activity> activityMap, ArrayList<WorkoutPlan> workoutPlans, LocalDate currDate) {
        this.userMap = userMap;
        this.activityMap = activityMap;
        this.currDate = currDate;
    }

    /**
     * Construtor cópia
     *
     * @param fit Um objeto cópia
     */
    public Fitness(Fitness fit) {
        this.userMap = new HashMap<>(fit.getUserMap());
        this.activityMap = new HashMap<>(fit.getActivityMap());
        this.currDate = LocalDate.of(fit.getCurrDate().getYear(), fit.getCurrDate().getMonth(), fit.getCurrDate().getDayOfMonth());
    }

    /**
     * Cria um clone do objeto.
     *
     * @return Um objeto que é uma copia do atual.
     */
    public Fitness clone() {
        return new Fitness(this);
    }

    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("Users: ").append(userMap.toString()).append('\n');
        sb.append("Activities: ").append(activityMap.toString()).append("\n");
        return sb.toString();
    }


    // ----------------------------Getter and setter methods----------------------------------------------------------

    public Map<String, User> getUserMap() {
        return this.userMap.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public void setUserMap(Map<String, User> userMap) {
        this.userMap =  userMap.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public Map<String, Activity> getActivityMap() {
        return this.activityMap.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public void setActivityMap(Map<String,Activity> activityMap) {
        this.activityMap = activityMap.entrySet().stream().collect(Collectors.toMap(k->k.getKey(),v->v.getValue().clone()));
    }

    public LocalDate getCurrDate() {
        return this.currDate;
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
// ----------------------------END OF ------Getter and setter methods----------------------------------------------------------
    /**
     * Guarda os dados num ficheiro persistente.
     */
    public void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("data.ser");
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

    /**
     * Carrega um estado de aplicação
     * @return o estado da aplicação
     */
    public Fitness load() {
        Fitness fit = new Fitness();
        try {
            FileInputStream fileIn = new FileInputStream("data.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            fit = (Fitness) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("File loaded successfully.");
            System.out.println("Found userMap with size: " + fit.getUserMap().size());

            for (User user : fit.getUserMap().values()) {
                user.setListaAtividades(new ArrayList<>()); // Reset activitiesList
                for (Activity activity : fit.getActivityMap().values()) {
                    if (user.getActivitiesList().contains(activity)) {
                        user.addActivityToUser(activity.clone()); // Add activity to user
                    }
                }
            }

        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Loading error");
            e.printStackTrace();
        }
        return fit;
    }

    // adiciona atividade apenas se ainda não existir uma atividade com o mesmo nome ID
    public boolean addActivity (Activity activity) {
        if (!this.activityMap.containsValue(activity)) {
            this.activityMap.put(activity.getActivityID(), activity.clone());
            return true;
        }
        return false;
    }

    public void daySkip(int daysToSkip, Fitness fit) {
        fit = fit.load();
        this.currDate = this.currDate.plusDays(daysToSkip);
        Map<String, User> userMap = fit.getUserMap();

        for (User user : userMap.values()) {
            user = User.loadUser(user.getUsername());
            assert user != null;
            ArrayList<WorkoutPlan> workoutPlanList = user.getWorkoutPlansList();

            for (WorkoutPlan workoutPlan : workoutPlanList) {
                System.out.println("Workout Plan Date: " + workoutPlan.getDate());
                List<Activity> activities = workoutPlan.getActivities();

                if(workoutPlan.getDate().equals(this.currDate)|| workoutPlan.getDate().isBefore(this.currDate)) {
                    for (Activity activity : activities) {

                        activity.setDate(workoutPlan.getDate());
                        double calories = activity.calories(user);
                        user.setCalories(calories);

                        for(int i=1; i<=workoutPlan.getIterations(); i++) {
                            user.addActivityToUser(activity);
                        }
                    }
                    user.removeWorkoutPlan(workoutPlan);
                }
            }
            user.saveUser();
        }

    }



    public boolean existsUsername(String username) {
        return userMap.containsKey(username);
    }

    public boolean addUser(User user) {
        if(!existsUsername(user.getUsername())) {
            userMap.put(user.getUsername(), user);
            return true;
        }
        return false;
    }

    public boolean loginUser(String username, String password) {
        if(userMap.containsKey(username)) {
            User user = userMap.get(username);
            String realPassword = user.getPassword();

            if(password.equals(realPassword)) {
                return true;
            }
        }
        return false;
    }

    public boolean isHardRunning(double pace){
        if(pace < 5) {
            return true;
        }
        return false;
    }

    public boolean isHardMountainBike(double pace){
        if(pace < 3) {
            return true;
        }
        return false;
    }

    public boolean isHardDistance(int duration, double distance) {
        if(duration >= 60) { // mais de 60 minutos
            return true;
        }

        else if(distance >= 10) { // mais de 10km
            return true;
        }
        return false;
    }

    public boolean isHardDistanceAltitude(int duration, double distance, double altitude) {
        if(duration >= 60) { // mais de 60 minutos
            return true;
        }

        else if(distance >= 10) { // mais de 10km
            return true;
        }
        else if (altitude >= 700) {
            return true;
        }
        return false;
    }

    public boolean isHardWeightlifting(double reps, double sets, double weight, User user) {
        if(sets>=4) {
            return true;
        }

        else if(reps>=20) {
            return true;
        }

        else if (weight>=user.getWeight()) {
            return true;
        }
        return false;
    }

    public boolean isHardBenchPress(double reps, double sets, double weight, boolean incline,User user) {
        if(sets>=4) {
            return true;
        }

        else if(reps>=20) {
            return true;
        }

        else if (weight>=user.getWeight()) {
            return true;
        } else if (incline) {
            return true;
        }
        return false;
    }

    public boolean isHardBodyWeight(double reps, double sets) {
        if(sets>=3) {
            return true;
        }
        else if(reps>=50) {
            return true;
        }
        return false;
    }

    public boolean isHardSquats(double reps, double sets, int rpe) {
        if(sets>=6) {
            return true;
        }
        else if(reps>=100) {
            return true;
        }

        else if(rpe>7) {
            return true;
        }
        return false;
    }

    public void addBasicActivities(){
        LocalDate date = LocalDate.now();

        Activity baseSquat = new Squat("Squat", "Body-weight", date, 0, 0, 0, false, 0);
        addActivity(baseSquat);

        Activity baseRunning = new Running("Running", "Distance", date, 0, 0.0, 0.0, 0, false); // em km
        addActivity(baseRunning);

        Activity baseMountainBike = new MountainBike("MountainBike", "Distance&Altitude", date, 0, 0.0, 0.0, 0.0, false);
        addActivity(baseMountainBike);

        Activity baseBenchPress = new BenchPress("BenchPress", "Weight-lifting", date, 0,false, 0,0, 0.0, false);
        addActivity(baseBenchPress);

    }

    public void addCustomActivities(String activityName, String activityType) {
        LocalDate date = LocalDate.now();

        if(activityType.equals("Distance")) {
            Activity newActivity = new Distance(activityName, activityType, LocalDate.now(), 0, 0.0, false);
            addActivity(newActivity);
        }

        else if(activityType.equals("Weight-lifting")) {
            Activity newActivity = new Weightlifting(activityName, activityType, LocalDate.now(), 0, false, 0, 0, 0.0);
            addActivity(newActivity);

        }

        else if (activityType.equals("Body-weight")) {
            Activity newActivity = new Bodyweight(activityName, activityType, LocalDate.now(), 0, 0, 0, false);
            addActivity(newActivity);
        }
        else if (activityType.equals("Distance&Altitude")) {
            Activity newActivity = new DistanceAltitude(activityName, activityType, LocalDate.now(), 0, 0.0, 0.0, false);
            addActivity(newActivity);
        }

        else {
            System.out.println("Invalid activity type");
        }
    }

    public String newDistanceActivityFromList(int index) {
        int i = 1;
        for (Activity a : this.activityMap.values()) {
            if(a.getActivityID().equals("Distance") || a.getActivityID().equals("Running") || a.getActivityType().equals("Distance")) {
                if(i == index) {
                    return a.getActivityID();
                }
                i++;
            }

        }
        return null;
    }

    public String newDistanceAltitudeActivityFromList(int index) {
        int i = 1;
        for (Activity a : this.activityMap.values()) {
            if(a.getActivityID().equals("Distance&Altitude") || a.getActivityID().equals("MountainBike") || a.getActivityType().equals("Distance&Altitude")) {
                if(i == index) {
                    return a.getActivityID();
                }
                i++;
            }

        }
        return null;
    }

    public String newBodyweightFromList(int index) {
        int i = 1;
        for (Activity a : this.activityMap.values()) {
            if(a.getActivityType().equals("Body-weight") || a.getActivityType().equals("Squat")) {
                if(i == index) {
                    return a.getActivityID();
                }
                i++;
            }

        }
        return null;
    }

    public String newWeightliftingActivityFromList(int index) {
        int i = 1;
        for (Activity a : this.activityMap.values()) {
            if(a.getActivityType().equals("Weight-lifting") || a.getActivityType().equals("BenchPress")) {
                if(i == index) {
                    return a.getActivityID();
                }
                i++;
            }

        }
        return null;
    }

    public User mostCaloriesBurnedUser(Map<String,User> userMap) {
        double mostCalories = 0;
        User topUser = null;
        for(User user : userMap.values()) {
            user = User.loadUser(user.getUsername());
            assert user != null;
            if(user.getCalories() > mostCalories) {
                topUser = user;
                mostCalories = user.getCalories();
            }
        }
        return topUser;
    }

    public User mostActivitiesUser(Map<String,User> userMap) {
        User topUser = null;
        int activity_number = 0;

        for(User user : userMap.values()) {
            user = User.loadUser(user.getUsername());
            assert user != null;

            if (activity_number<user.getActivitiesList().size()) {
                topUser = user;
                activity_number = user.getActivitiesList().size();
            }
        }
        return topUser;
    }

    public String mostExecutedActivity(Map<String, User> userMap) {
        // Map to store activity type counts
        Map<String, Integer> activityCounts = new HashMap<>();

        // Iterate through each user
        for (User user : userMap.values()) {
            user = User.loadUser(user.getUsername());
            assert user != null;

            // Iterate through each activity of the user
            for (Activity activity : user.getActivitiesList()) {
                String activityType = activity.getActivityType();

                // Update the count for the activity type
                activityCounts.put(activityType, activityCounts.getOrDefault(activityType, 0) + 1);
            }
        }

        // Find the activity type with the maximum count
        int maxCount = 0;
        String topActivityType = null;
        for (Map.Entry<String, Integer> entry : activityCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                topActivityType = entry.getKey();
            }
        }

        return topActivityType;
    }

    public double kilometresUser(User user) {
        double totalKilometres = 0;
        ArrayList<Activity> activitiesList = user.getActivitiesList();

        for (Activity a : activitiesList) {
            if (a.getActivityType().equals("Distance")) {
                totalKilometres += ((Distance) a).getDistance();
            }
        }
        return totalKilometres;
    }

    public double travelledAltitudeUser(User user) {
        double totalAltitude = 0;
        ArrayList<Activity> activitiesList = user.getActivitiesList();

        for (Activity a : activitiesList) {
            if (a.getActivityType().equals("Distance&Altitude")) {
                totalAltitude += ((DistanceAltitude) a).getAltitude();
            }
        }
        return totalAltitude;
    }

    public WorkoutPlan hardestWorkoutPlan(ArrayList<WorkoutPlan> workoutPlanList, User user) {
        double total_calories = 0;
        double most_calories = 0;
        WorkoutPlan topWorkoutPlan = null;

        for (WorkoutPlan workoutPlan : workoutPlanList) {
            List<Activity> activities = workoutPlan.getActivities();

            for (Activity activity : activities) {
                double calories = activity.calories(user);
                    total_calories += calories;
                }
            if (total_calories>most_calories) {
                most_calories = total_calories;
                topWorkoutPlan = workoutPlan;
            }
        }
        return topWorkoutPlan;
    }

    public String getRandomActivityType() {
        List<String> types = new ArrayList<>();

        // Gather all available activity types
        for (Activity a : this.activityMap.values()) {
            String activityType = a.getActivityType();
            if (activityType != null) {
                types.add(activityType);
            }
        }

        // Check if there are any non-null activity types
        if (types.isEmpty()) {
            return null; // No activity types available
        }

        // Generate a random index to select a random activity type
        Random rand = new Random();
        int randomIndex = rand.nextInt(types.size());

        // Return the randomly selected activity type
        return types.get(randomIndex);
    }

}