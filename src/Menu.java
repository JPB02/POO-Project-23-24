import java.io.File;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;

public class Menu implements Serializable {

    private Scanner sc;
    public Menu() {
        this.sc = new Scanner(System.in);
    }

    // usado para dar print ao menu incial
    public void startMenu() {
        System.out.println("##############-Welcome-##############");
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

    public boolean validWeight(int weight) {
        return (weight > 0 && weight <= 300);
    }

    public void registerMenu() {

        String type = new String();
        boolean isValidUserType = false;
        while(!isValidUserType) {
            System.out.println("Input the type of user: ");
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
        }

        System.out.println("Input your name: ");
        String name = sc.nextLine();

        System.out.println("Input your username: ");
        String username = sc.nextLine();

        System.out.println("Input your password: ");
        String password = sc.nextLine();

        LocalDate dateOfBirth = null;
        boolean isValidDateOfBirth = false;
        while(!isValidDateOfBirth) {
            System.out.println("Input the date of birth (year-month-day): ");
            dateOfBirth = LocalDate.parse(sc.nextLine());

            if(validDateOfBirth(dateOfBirth)) {
                isValidDateOfBirth = true;
            }
        }

        int height = 0;
        boolean isValidHeight = false;
        while(!isValidHeight) {
            System.out.println("Input your height(in cm): ");
            height = sc.nextInt();

            if(validHeight(height)) {
                isValidHeight = true;
            }
        }

        int weight = 0;
        boolean isValidWeight = false;
        while(!isValidWeight) {
            System.out.println("Input your weight: ");
            weight = sc.nextInt();

            if(validWeight(weight)) {
                isValidWeight = true;
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
