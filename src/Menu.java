import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Menu implements Serializable {

    private Scanner sc;
    public Menu() {
        this.sc = new Scanner(System.in);

    }

    // usado para dar print ao menu incial
    public void startMenu() {
        System.out.println("##############-UMINHO FIT-##############");
        System.out.println("\n1. Register");
        System.out.println("\n2. Login");
        System.out.println("\n3. Exit");
        System.out.println("\n4. Show All Users(debugging)");
        System.out.println("\n5. Show All Activities(debugging)");
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
        while(option!=3) {
            System.out.println("\n1.Details");
            System.out.println("\n2.Activities");
            System.out.println("\n3.Logout");
            option = sc.nextInt();

            switch (option) {
                case 1:
                    System.out.println(loggedInUser.toString());
                    break;
                case 2:
                    activitiesMenu(username);
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

            System.out.println("\n1.Add activity");
            System.out.println("\n2.Delete activity");
            System.out.println("\n3.Show activities");
            System.out.println("\n4.Add Custom Activities");
            int option = sc.nextInt();

            switch (option) {
                case 1:
                    int typeOption = 0;

                    System.out.println("Input activity type:");
                    System.out.println("\n1.Distance");
                    System.out.println("\n2.Distance & Altitude");
                    System.out.println("\n3.Weight-lifting");
                    System.out.println("\n4.Body-weight");
                    typeOption = sc.nextInt();

                    switch (typeOption) {
                        case 1:
                            System.out.println("Select Distance Activity:");
                            System.out.println("\n1.Running");
                            int activityOptionDistance = sc.nextInt();

                            switch (activityOptionDistance) {
                                case 1:
                                    sc.nextLine();
                                    System.out.println("Input activity description ID: ");
                                    String id = sc.nextLine();

                                    System.out.println("Input activity duration: ");
                                    int duration = sc.nextInt();

                                    System.out.println("Input distance(in km): ");
                                    double distance = sc.nextDouble();

                                    int steps = 1000 * (int) (distance);
                                    double pace = duration / distance;
                                    boolean isHard = fit.isHardRunning(pace);

                                    Activity run = new Running(id, "Distance", LocalDate.now(), duration, distance, pace, steps, isHard);
                                    assert loggedInUser != null;
                                    double calories = run.calories(loggedInUser);
                                    loggedInUser.setCalories(calories);
                                    loggedInUser.addActivityToUser(run);
                                    loggedInUser.saveUser();
                                    /*
                                    fit.addActivity(run);
                                    fit.save();
                                     */
                                    break;

                                default:
                                    System.out.println("Invalid activity option!");
                                    break;
                            }
                            break; // End of case 1: Distance

                        case 2:
                            System.out.println("\n1.Mountain Bike");
                            int activityOptionDistanceAltitude = sc.nextInt();
                            switch (activityOptionDistanceAltitude) {
                                case 1:
                                    sc.nextLine();
                                    System.out.println("Input activity description ID: ");
                                    String id = sc.nextLine();

                                    System.out.println("Input activity duration: ");
                                    int duration = sc.nextInt();

                                    System.out.println("Input distance(in km): ");
                                    double distance = sc.nextDouble();

                                    System.out.println("Input altitude(in metres): ");
                                    double altitude = sc.nextDouble();

                                    double pace = duration / distance;
                                    boolean isHard = fit.isHardMountainBike(pace);

                                    Activity mountainBike = new MountainBike(id, "Distance&Altitude", LocalDate.now(), duration, distance, altitude, pace, isHard);
                                    assert loggedInUser != null;
                                    double calories = mountainBike.calories(loggedInUser);
                                    loggedInUser.setCalories(calories);
                                    loggedInUser.addActivityToUser(mountainBike);
                                    loggedInUser.saveUser();
                                    /*
                                    fit.addActivity(mountainBike);
                                    fit.save();
                                     */
                                    break;

                                default:
                                    System.out.println("Invalid activity option!");
                                    break;
                            }
                            break; // End of case 2: Distance & Altitude
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
                    int customActivityTypeChoice = sc.nextInt();

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



}
