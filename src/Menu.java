import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
        while(!isValidAvgHR) {
            System.out.println("Input your average heart rate: ");
            avgHR = sc.nextInt();

            if(validAvgHR(avgHR)) {
                isValidAvgHR = true;
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


        File file = new File("data.ser");
        Fitness fit = new Fitness();


        if (file.exists()) {
            Fitness fileinfo = new Fitness();
            fileinfo = fileinfo.load();
            fit = new Fitness(fileinfo);
        } else {
            fit = new Fitness();
            fit.basicActivities(); // inicializa o mapa de atividades
        }

        while(!fit.addUser(newUser)){
            System.out.println("\nThat username is already taken!");
            System.out.println("\nPlease input a new username:");
            String newUsername = sc.next();
            newUser.setUsername(newUsername);
        }

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
        Fitness fit = new Fitness();
        fit = fit.load();
        User loggedInUser = fit.getUserMap().get(username);

        int option = 0;
        while(option!=2) {
            System.out.println("\n1.Details");
            System.out.println("\n2.Logout");
            option = sc.nextInt();

            switch (option) {
                case 1:
                    System.out.println(loggedInUser.toString());

                case 2:
                    break;

                default:
                    System.out.println("Invalid option!");
            }
        }

    }

    // debugging
    public void printAllUsers() {
        Fitness fit = new Fitness();
        fit = fit.load();
        Map<String, User> userMap = fit.getUserMap();
        for(User user: userMap.values()) {
            System.out.println(user.toString());
        }
    }



}
