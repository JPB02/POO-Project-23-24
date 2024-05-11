import java.io.File;
import java.io.Serializable;
import java.sql.Array;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Menu implements Serializable {

    private Scanner sc;
    public Menu() {
        this.sc = new Scanner(System.in);

    }

    // Utility method to get integer input from user with error handling
    private int getIntInput() {
        int input = 0;
        boolean isValidInput = false;

        while (!isValidInput) {
            try {
                input = sc.nextInt();
                isValidInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                sc.nextLine(); // Clear the invalid input
            }
        }

        return input;
    }

    // Utility method to get double input from user with error handling
    private double getDoubleInput() {
        double input = 0.0;
        boolean isValidInput = false;

        while (!isValidInput) {
            try {
                input = sc.nextDouble();
                isValidInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine(); // Clear the invalid input
            }
        }

        return input;
    }

    // usado para dar print ao menu incial
    public void startMenu() {
        System.out.println("##############-UMINHO FIT-##############");
        System.out.println("\n1. Register");
        System.out.println("\n2. Login");
        System.out.println("\n3. Exit");
        System.out.println("\n4. Show All Users(debugging)");
        System.out.println("\n5. Show All Activities(debugging)");
        System.out.println("\n6. App statistics");
    }

    // usamos regex para ver se o email é válido
    public static boolean validEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regex);

    }

    // se a data de nascimento for igual ou depois da data atual, então é inválida
    public boolean validDateOfBirth(LocalDate dateOfBirth) {
        if(dateOfBirth.isBefore(LocalDate.now())) {
            return true;
        }
        return false;
    }

    public boolean validAvgHR(int avgHR) {
        return avgHR >= 0 && avgHR <= 220;
    }

    public boolean validUserType(String userType) {
        return (userType.equals("Amateur") ||userType.equals("Occasional") || userType.equals("Professional"));
    }

    // tamanho maior que 250cm
    public boolean validHeight(int height) {
        return (height > 0 && height <= 250);
    }

    public boolean validWeight(double weight) {
        return (weight > 0.0 && weight <= 300.0);
    }

    public void registerMenu() {
        File file = new File("data.ser");
        Fitness fit = new Fitness();


        if (file.exists()) {
            Fitness fileinfo = new Fitness();
            fileinfo = fileinfo.load();
            fit = new Fitness(fileinfo);
        } else {
            fit = new Fitness();
            fit.addBasicActivities(); // inicializa o mapa de atividades
        }

        String type = new String();
        boolean isValidUserType = false;
        while(!isValidUserType) {
            System.out.println("Input the type of user(Amateur, Occasional, Professional): ");
            type = sc.nextLine();

            if(validUserType(type)) {
                isValidUserType = true;
            }
        }

        User newUser = null;

        switch(type) {
            case "Amateur":
                newUser = new Amateur();
                break;

            case "Occasional":
                newUser = new Occasional();
                break;

            case "Professional":
                newUser = new Professional();
                break;
            default:
                System.out.println("Invalid user type!");
        }

        System.out.println("Input your name: ");
        String name = sc.nextLine();

        System.out.println("Input your username: ");
        String username = sc.nextLine();

        System.out.println("Input your password: ");
        String password = sc.nextLine();

        LocalDate dateOfBirth = null;
        boolean isValidDateOfBirth = false;
        while (!isValidDateOfBirth) {
            System.out.println("Input the date of birth (YYYY-MM-DD): ");
            String dateInput = sc.nextLine();

            try {
                dateOfBirth = LocalDate.parse(dateInput);
                if (validDateOfBirth(dateOfBirth)) {
                    isValidDateOfBirth = true;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in the format YYYY-MM-DD.");
            }
        }

        int height = 0;
        boolean isValidHeight = false;
        while (!isValidHeight) {
            System.out.println("Input your height(in cm): ");
            String heightInput = sc.nextLine(); // Read input as string

            try {
                height = Integer.parseInt(heightInput); // Parse input to integer
                if (validHeight(height)) {
                    isValidHeight = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for height.");
            }
        }


        double weight = 0.0;
        boolean isValidWeight = false;
        while (!isValidWeight) {
            System.out.println("Input your weight(kg): ");
            String weightInput = sc.nextLine(); // Read input as string

            try {
                weight = Double.parseDouble(weightInput); // Parse input to double
                if (validWeight(weight)) {
                    isValidWeight = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number for weight.");
            }
        }

        System.out.println("Input your address: ");
        String address = sc.next();

        sc.nextLine();
        String email = "";
        boolean isValidEmail = false;
        while(!isValidEmail) {
            System.out.println("Input your email: ");
            email = sc.nextLine();

            if(validEmail(email)) {
                isValidEmail = true;
            }
        }

        int avgHR = 0;
        boolean isValidAvgHR = false;

        while (!isValidAvgHR) {
            try {
                System.out.println("Input your average heart rate: ");
                avgHR = sc.nextInt();

                if (validAvgHR(avgHR)) {
                    isValidAvgHR = true;
                } else {
                    throw new IllegalArgumentException("Invalid average heart rate. Must be between 40 and 220.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
                // Clear the scanner buffer
                sc.nextLine();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.");
                // Clear the scanner buffer
                sc.nextLine();
            }
        }


        // já temos todos os dados para criar o utilizador novo
        assert newUser != null;
        newUser.setName(name);
        newUser.setUsername(username);
        newUser.setUserType(type);
        newUser.setDateOfBirth(dateOfBirth);
        newUser.setHeight(height);
        newUser.setWeight(weight);
        newUser.setAddress(address);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setAvgHR(avgHR);


        while(!fit.addUser(newUser)){
            System.out.println("\nThat username is already taken!");
            System.out.println("\nPlease input a new username:");
            String newUsername = sc.next();
            newUser.setUsername(newUsername);
        }

        newUser.saveUser();
        fit.save();

    }

    public String Login() {
        Fitness fit = new Fitness();
        fit = fit.load();

        boolean loggedIn = false;
        String username = null;
        while(!loggedIn) {

            System.out.println("##############-LOGIN-##############");
            System.out.println("\nUsername: ");
            username = sc.nextLine();
            System.out.println("Password: ");
            String password = sc.nextLine();

            if(fit.loginUser(username, password)) {
                System.out.println("Logged in!");
                loggedIn = true;
            }

            else {
                System.out.println("Invalid username or password!");
            }
        }

        return username;
    }

    public void loggedInMenu(String username) {
        System.out.println("##############-Bem-vindo " + username + "-##############");
        User loggedInUser = User.loadUser(username);  // Load the user along with its activities

        int option = 0;
        while(option!=4) {
            System.out.println("\n1.Details");
            System.out.println("\n2.Activities");
            System.out.println("\n3.Workout Plans");
            System.out.println("\n4.Logout");
            option = getIntInput();

            switch (option) {
                case 1:
                    assert loggedInUser != null;
                    System.out.println(loggedInUser.toString());
                    break;
                case 2:
                    activitiesMenu(username);
                    break;
                case 3:
                    workoutPlansMenu(loggedInUser);
                case 4:
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    public void workoutPlansMenu(User user) {
        Fitness fit = new Fitness();
        fit = fit.load();
        user = User.loadUser(user.getUsername());  // Load the user along with its activities


        int option = 0;
        while(option!=3) {
            System.out.println("\n1.Current Workout Plan");
            System.out.println("\n2.Generate Random Workout Plan");
            System.out.println("\n3.Go back");
            option = getIntInput();

            switch (option) {
                case 1:
                    assert user != null;
                    System.out.println(user.getWorkoutPlansList().toString());
                    break;
                case 2:
                    WorkoutPlan newWorkoutPlan = new WorkoutPlan();
                    System.out.println("Input how many days per week:");

                    int numActivities = 0;
                    try {
                        numActivities = getIntInput();
                    } catch (InputMismatchException e) {
                        System.out.println("Error: Please enter a valid option number.");
                        sc.nextLine(); // Clear the invalid input
                    }

                    if(numActivities <= 7 && numActivities >0) {
                        int curActivityNum;
                        for (curActivityNum = 1; curActivityNum <= numActivities; curActivityNum++) {

                            System.out.println("Choose activity type for day " + curActivityNum + ": ");
                            System.out.println("\n1.Distance");
                            System.out.println("\n2.DistanceAltitude");
                            System.out.println("\n3.Body-weight");
                            System.out.println("\n4.Weight-lifting");
                            int chooseOption = 0;
                            try {
                                chooseOption = getIntInput();
                            } catch (InputMismatchException e) {
                                System.out.println("Error: Please enter a valid option number.");
                                sc.nextLine(); // Clear the invalid input
                            }

                            if (chooseOption == 1) {
                                String activityType = "Distance";
                                newWorkoutPlan.allocateRandomActivity(fit, user, activityType, 1,newWorkoutPlan.getDate());
                            }

                            else if(chooseOption == 2) {
                                String activityType = "DistanceAltitude";
                                newWorkoutPlan.allocateRandomActivity(fit, user, activityType, 1,newWorkoutPlan.getDate());
                            }

                            else if (chooseOption == 3) {
                                String activityType = "Body-weight";
                                newWorkoutPlan.allocateRandomActivity(fit, user, activityType, 1,newWorkoutPlan.getDate());
                            }

                            else if (chooseOption == 4) {
                                String activityType = "Weight-lifting";
                                newWorkoutPlan.allocateRandomActivity(fit, user, activityType, 1,newWorkoutPlan.getDate());
                            }

                            else {
                                System.out.println("Invalid option!");
                                break;
                            }
                        }

                    }
                    assert user != null;
                    user.addWorkoutPlanToUser(newWorkoutPlan);
                    user.saveUser();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

        public void activitiesMenu(String username) {
            Fitness fit = new Fitness();
            fit = fit.load();
            User loggedInUser = User.loadUser(username);

            System.out.println("\n1.Execute an activity");
            System.out.println("\n2.Delete activity");
            System.out.println("\n3.Show activities");
            System.out.println("\n4.Register Custom Activities");
            int option=0;
            try {
                option = getIntInput();
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid option number.");
                sc.nextLine(); // Clear the invalid input
            }

            switch (option) {
                case 1:
                    Map<String, Activity> availableActivities = fit.getActivityMap();
                    System.out.println("Input activity type:");
                    System.out.println("\n1.Distance");
                    System.out.println("\n2.Distance&Altitude");
                    System.out.println("\n3.Weight-lifting");
                    System.out.println("\n4.Body-weight");
                    int typeOption;
                    try {
                        typeOption = getIntInput();
                    } catch (InputMismatchException e) {
                        System.out.println("Error: Please enter a valid option number.");
                        sc.nextLine(); // Clear the invalid input
                        break;
                    }

                    switch (typeOption) {
                        // EXECUTE DISTANCE ACTIVITY
                        case 1:
                            int activityOptionDistance;
                            int interfaceIndex = 1;
                            System.out.println("Select Distance Activity:");
                            for (Activity activity: availableActivities.values()) {
                                if(activity.getActivityType().equals("Distance")) {
                                    System.out.println("\n"+interfaceIndex+"."+activity.getActivityID());
                                    interfaceIndex++;
                                }
                            }
                            try {
                                activityOptionDistance = getIntInput();
                            } catch (InputMismatchException e) {
                                System.out.println("Error: Please enter a valid option number.");
                                sc.nextLine(); // Clear the invalid input
                                break;
                            }

                            sc.nextLine();
                            String id;
                            System.out.println("Input activity description ID: ");
                            try {
                                id = sc.nextLine();
                            } catch (InputMismatchException e) {
                                System.out.println("Error: Please enter a valid id.");
                                sc.nextLine(); // Clear the invalid input
                                break;
                            }

                            int duration;
                            System.out.println("Input activity duration: ");
                            try {
                                duration = getIntInput();
                            } catch (InputMismatchException e) {
                                System.out.println("Error: Please enter a valid distance integer.");
                                sc.nextLine(); // Clear the invalid input
                                break;
                            }

                            double distance;
                            System.out.println("Input distance(in km): ");
                            try {
                                distance = getDoubleInput();
                            } catch (InputMismatchException e) {
                                System.out.println("Error: Please enter a valid distance integer.");
                                sc.nextLine(); // Clear the invalid input
                                break;
                            }

                            String choiceDistance = fit.newDistanceActivityFromList(activityOptionDistance);

                            if (choiceDistance.equals("Running")) {
                                int steps = 1000 * (int) (distance);
                                double pace = duration / distance;
                                boolean isHard = fit.isHardRunning(pace);

                                Activity newRunning = new Running(id, "Distance", LocalDate.now(), duration, distance, pace, steps, isHard);
                                double caloriesRun = newRunning.calories(loggedInUser);
                                loggedInUser.setCalories(caloriesRun);
                                loggedInUser.addActivityToUser(newRunning);
                                loggedInUser.saveUser();
                                break;
                            }

                            else {
                                boolean isHard = fit.isHardDistance(duration, distance);

                                Activity newDistance = new Distance(id, choiceDistance, LocalDate.now(), duration, distance, isHard);
                                assert loggedInUser != null;
                                double caloriesDist = newDistance.calories(loggedInUser);
                                loggedInUser.setCalories(caloriesDist);
                                loggedInUser.addActivityToUser(newDistance);
                                loggedInUser.saveUser();
                                break;
                            }
                        // EXECUTE DISTANCE&ALTITUDE ACTIVITY
                        case 2:
                            int interfaceIndex2 = 1;
                            System.out.println("Select Distance&Altitude Activity:");
                            availableActivities = fit.getActivityMap();
                            for (Activity activity: availableActivities.values()) {
                                if(activity.getActivityType().equals("Distance&Altitude")) {
                                    System.out.println("\n"+interfaceIndex2+"."+activity.getActivityID());
                                    interfaceIndex2++;
                                }
                            }

                            int activityOptionDistanceAltitude;
                            try {
                                activityOptionDistanceAltitude = getIntInput();
                            } catch (InputMismatchException e) {
                                System.out.println("Error: Please enter a valid option number.");
                                sc.nextLine(); // Clear the invalid input
                                break;
                            }

                            sc.nextLine();
                            System.out.println("Input activity description ID: ");
                            String id2 = sc.nextLine();

                            System.out.println("Input activity duration: ");
                            int duration2;
                            try {
                                duration2 = getIntInput();
                            } catch (InputMismatchException e) {
                                System.out.println("Error: Please enter a valid option number.");
                                sc.nextLine(); // Clear the invalid input
                                break;
                            }

                            System.out.println("Input distance(in km): ");
                            double distance2;
                            try {
                                distance2 = getIntInput();
                            } catch (InputMismatchException e) {
                                System.out.println("Error: Please enter a valid option number.");
                                sc.nextLine(); // Clear the invalid input
                                break;
                            }

                            System.out.println("Input altitude(in metres): ");
                            double altitude;
                            try {
                                altitude = getDoubleInput();
                            } catch (InputMismatchException e) {
                                System.out.println("Error: Please enter a valid option number.");
                                sc.nextLine(); // Clear the invalid input
                                break;
                            }

                            double pace2 = duration2 / distance2;

                            String choiceDistanceAltitude = fit.newDistanceAltitudeActivityFromList(activityOptionDistanceAltitude);

                            if (choiceDistanceAltitude.equals("MountainBike")) {
                                boolean isHard = fit.isHardMountainBike(pace2);

                                Activity newMountainBike = new MountainBike(id2, "Distance&Altitude", LocalDate.now(), duration2, distance2, altitude, pace2, isHard);
                                assert loggedInUser != null;
                                double caloriesMountainBike = newMountainBike.calories(loggedInUser);
                                loggedInUser.setCalories(caloriesMountainBike);
                                loggedInUser.addActivityToUser(newMountainBike);
                                loggedInUser.saveUser();
                                break;
                            }

                            else {
                                boolean isHard = fit.isHardDistanceAltitude(duration2, distance2, altitude);

                                Activity newDistanceAltitude = new DistanceAltitude(choiceDistanceAltitude, "Distance&Altitude", LocalDate.now(), duration2, distance2, altitude, isHard);
                                assert loggedInUser != null;
                                double caloriesDistAlt = newDistanceAltitude.calories(loggedInUser);
                                loggedInUser.setCalories(caloriesDistAlt);
                                loggedInUser.addActivityToUser(newDistanceAltitude);
                                loggedInUser.saveUser();
                                break;
                            }
                        // EXECUTE WEIGHTLIFTING ACTIVITY
                        case 3:
                            int interfaceIndex3 = 1;
                            System.out.println("Select Weight-lifting Activity:");
                            availableActivities = fit.getActivityMap();
                            for (Activity activity: availableActivities.values()) {
                                if(activity.getActivityType().equals("Weight-lifting")) {
                                    System.out.println("\n"+interfaceIndex3+"."+activity.getActivityID());
                                    interfaceIndex3++;
                                }
                            }

                            int activityOptionWeightlifting;
                            try {
                                activityOptionWeightlifting = getIntInput();
                            } catch (InputMismatchException e) {
                                System.out.println("Error: Please enter a valid option number.");
                                sc.nextLine(); // Clear the invalid input
                                break;
                            }

                            sc.nextLine();
                            System.out.println("Input activity description ID: ");
                            String id3 = sc.nextLine();

                            System.out.println("Input activity duration: ");
                            int duration3;
                            try {
                                duration3 = getIntInput();
                            } catch (InputMismatchException e) {
                                System.out.println("Error: Please enter a valid option number.");
                                sc.nextLine(); // Clear the invalid input
                                break;
                            }

                            System.out.println("Input reps: ");
                            int reps1;
                            try {
                                reps1 = getIntInput();
                            } catch (InputMismatchException e) {
                                System.out.println("Error: Please enter a valid option number.");
                                sc.nextLine(); // Clear the invalid input
                                break;
                            }

                            System.out.println("Input sets: ");
                            int sets1;
                            try {
                                sets1 = getIntInput();
                            } catch (InputMismatchException e) {
                                System.out.println("Error: Please enter a valid option number.");
                                sc.nextLine(); // Clear the invalid input
                                break;
                            }

                            System.out.println("Input weight: ");
                            double weight1;
                            try {
                                weight1 = getDoubleInput();
                            } catch (InputMismatchException e) {
                                System.out.println("Error: Please enter a valid option number.");
                                sc.nextLine(); // Clear the invalid input
                                break;
                            }


                            String choiceWeightlifting = fit.newWeightliftingActivityFromList(activityOptionWeightlifting);

                            if (choiceWeightlifting.equals("BenchPress")) {

                                boolean incline;
                                System.out.println("Choose inclination: ");
                                System.out.println("\n1.Inclined");
                                System.out.println("\n2.Not inclined");
                                int inclination;
                                try {
                                    inclination = getIntInput();
                                } catch (InputMismatchException e) {
                                    System.out.println("Error: Please enter a valid option number.");
                                    sc.nextLine(); // Clear the invalid input
                                    break;
                                }

                                if(inclination==1) {
                                    incline=true;
                                }
                                else if(inclination==2) {
                                    incline=false;

                                }
                                else {
                                    System.out.println("Incorrect inclination...");
                                    break;
                                }

                                boolean isHard = fit.isHardBenchPress(reps1,sets1,weight1,incline,loggedInUser);

                                Activity newBenchPress = new BenchPress(id3, "Weight-lifting", LocalDate.now(), duration3, isHard, reps1, sets1, weight1,incline);
                                assert loggedInUser != null;
                                double caloriesBenchPress = newBenchPress.calories(loggedInUser);
                                loggedInUser.setCalories(caloriesBenchPress);
                                loggedInUser.addActivityToUser(newBenchPress);
                                loggedInUser.saveUser();
                                break;
                            }

                            else {
                                boolean isHard = fit.isHardWeightlifting(reps1, sets1, weight1, loggedInUser);

                                Activity newWeightlifting = new Weightlifting(choiceWeightlifting, "Weight-lifting", LocalDate.now(), duration3,isHard, reps1, sets1, weight1);
                                assert loggedInUser != null;
                                double caloriesWeightlifting = newWeightlifting.calories(loggedInUser);
                                loggedInUser.setCalories(caloriesWeightlifting);
                                loggedInUser.addActivityToUser(newWeightlifting);
                                loggedInUser.saveUser();
                                break;
                            }
                        // EXECUTE BODYWEIGHT
                        case 4:
                            int interfaceIndex4 = 1;
                            System.out.println("Select Body-weight Activity:");
                            availableActivities = fit.getActivityMap();
                            for (Activity activity: availableActivities.values()) {
                                if(activity.getActivityType().equals("Weight-lifting")) {
                                    System.out.println("\n"+interfaceIndex4+"."+activity.getActivityID());
                                    interfaceIndex4++;
                                }
                            }

                            int activityOptionBodyweight;
                            try {
                                activityOptionBodyweight = getIntInput();
                            } catch (InputMismatchException e) {
                                System.out.println("Error: Please enter a valid option number.");
                                sc.nextLine(); // Clear the invalid input
                                break;
                            }

                            sc.nextLine();
                            System.out.println("Input activity description ID: ");
                            String id4 = sc.nextLine();

                            System.out.println("Input activity duration: ");
                            int duration4;
                            try {
                                duration4 = getIntInput();
                            } catch (InputMismatchException e) {
                                System.out.println("Error: Please enter a valid option number.");
                                sc.nextLine(); // Clear the invalid input
                                break;
                            }

                            System.out.println("Input reps: ");
                            int reps2;
                            try {
                                reps2 = getIntInput();
                            } catch (InputMismatchException e) {
                                System.out.println("Error: Please enter a valid option number.");
                                sc.nextLine(); // Clear the invalid input
                                break;
                            }

                            System.out.println("Input sets: ");
                            int sets2;
                            try {
                                sets2 = getIntInput();
                            } catch (InputMismatchException e) {
                                System.out.println("Error: Please enter a valid option number.");
                                sc.nextLine(); // Clear the invalid input
                                break;
                            }

                            String choiceBodyweight = fit.newWeightliftingActivityFromList(activityOptionBodyweight);

                            if (choiceBodyweight.equals("Squat")) {

                                System.out.println("Input RPE(1-10): ");
                                int rpe;
                                try {
                                    rpe = getIntInput();
                                } catch (InputMismatchException e) {
                                    System.out.println("Error: Please enter a valid option number.");
                                    sc.nextLine(); // Clear the invalid input
                                    break;
                                }

                                if(rpe>10 || rpe<1) {
                                    System.out.println("Invalid RPE...");
                                    break;
                                }

                                boolean isHard = fit.isHardSquats(reps2,sets2,rpe);

                                Activity newSquat = new Squat(id4, "Body-weight", LocalDate.now(), duration4, reps2, sets2, isHard,rpe);
                                assert loggedInUser != null;
                                double caloriesSquat = newSquat.calories(loggedInUser);
                                loggedInUser.setCalories(caloriesSquat);
                                loggedInUser.addActivityToUser(newSquat);
                                loggedInUser.saveUser();
                                break;
                            }

                            else {
                                boolean isHard = fit.isHardBodyWeight(reps2, sets2);

                                Activity newBodyweight = new Bodyweight(choiceBodyweight, "Body-weight", LocalDate.now(), duration4,reps2, sets2, isHard);
                                assert loggedInUser != null;
                                double caloriesBodyweight = newBodyweight.calories(loggedInUser);
                                loggedInUser.setCalories(caloriesBodyweight);
                                loggedInUser.addActivityToUser(newBodyweight);
                                loggedInUser.saveUser();
                                break;
                            }

                    }
                    break; // End of case 1: Add activity

                case 2:
                    sc.nextLine();
                    // Code for delete activity
                    System.out.println("Which activity do you want to remove?");
                    if (loggedInUser != null && loggedInUser.getActivitiesList() != null) {
                        ArrayList<Activity> loggedUserActivityMap = loggedInUser.getActivitiesList();
                        for (Activity activity : loggedUserActivityMap) {
                            System.out.println(activity.toString());
                        }

                        System.out.println("Input activity ID: ");
                        String activityToRemove = sc.nextLine();

                        loggedInUser.removeActivityByID(loggedInUser, activityToRemove);
                        loggedInUser.saveUser();
                    }
                    else {
                        System.out.println("No such user exists, or user doesn't have any executed activities...");
                    }
                    break; // End of case 2: Delete activity

                case 3:
                    assert loggedInUser != null;
                    Map<String, Activity> allActivitiesMap = fit.getActivityMap();
                    System.out.println("Activities List: ");
                    for (String name : allActivitiesMap.keySet()) {
                        System.out.println(name);
                    }
                    break; // End of case 3: Show activities

                case 4:
                    // add custom activities
                    System.out.println("Input activity type: ");
                    System.out.println("\n1.Distance");
                    System.out.println("\n2.Distance&Altitude");
                    System.out.println("\n3.Weight-lifting");
                    System.out.println("\n4.Body-weight");
                    int customActivityTypeChoice;
                    try {
                        customActivityTypeChoice = getIntInput();
                    } catch (InputMismatchException e) {
                        System.out.println("Error: Please enter a valid option number.");
                        sc.nextLine(); // Clear the invalid input
                        break;
                    }

                    sc.nextLine(); // remove \n

                    if(customActivityTypeChoice == 1) {
                        String customActivityType = "Distance";

                        System.out.println("Input activity name: ");
                        String customActivityName = sc.nextLine();

                        fit.addCustomActivities(customActivityName, customActivityType);
                        fit.save();
                    }
                    else if (customActivityTypeChoice == 2) {
                        String customActivityType = "Distance&Altitude";

                        System.out.println("Input activity name: ");
                        String customActivityName = sc.nextLine();

                        fit.addCustomActivities(customActivityName, customActivityType);
                        fit.save();
                    }

                    else if (customActivityTypeChoice == 3) {
                        String customActivityType = "Weight-lifting";

                        System.out.println("Input activity name: ");
                        String customActivityName = sc.nextLine();

                        fit.addCustomActivities(customActivityName, customActivityType);
                        fit.save();
                    }

                    else if (customActivityTypeChoice == 4) {
                        String customActivityType = "Body-weight";

                        System.out.println("Input activity name: ");
                        String customActivityName = sc.nextLine();

                        fit.addCustomActivities(customActivityName, customActivityType);
                        fit.save();
                    }

                    else {
                        System.out.println("Invalid activity option!");
                    }

                    break;

                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }


    // debugging
    public void printAllUsers() {
        Fitness fit = new Fitness();
        fit = fit.load();

        Map<String, User> userMap = fit.getUserMap();
        for(User user: userMap.values()) {
            User loggedInUser = User.loadUser(user.getUsername());
            assert loggedInUser != null;
            System.out.println(loggedInUser.toString());
        }
    }

    // debugging
    public void printAllActivities() {
        Fitness fit = new Fitness();
        fit = fit.load();
        Map<String, Activity> activityMap = fit.getActivityMap();

        for(Activity activity: activityMap.values()) {
            System.out.println(activity.getActivityID());
        }
    }

    public void printStatisticsMenu() {
        Fitness fit = new Fitness();
        fit = fit.load();
        Map<String, User> userMap = fit.getUserMap();

        DecimalFormat df = new DecimalFormat("#.##");
        User topUser = fit.mostCaloriesBurnedUser(userMap);
        double calories = topUser.getCalories();
        System.out.println("Most Burned Calories User:" + topUser.getUsername()+ ", burned "+ df.format(calories)+"kcal");
    }

}
