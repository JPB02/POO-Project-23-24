import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Menu menu = new Menu();
        Scanner scanner = new Scanner(System.in);
        boolean active = true;

        while (active) {
            menu.startMenu();

            System.out.println("Choose an option: ");
            int option = 0;
            boolean isValidInput = false;
            while (!isValidInput) {
                try {
                    option = scanner.nextInt();
                    isValidInput = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    scanner.nextLine(); // Clear the invalid input
                }
            }

            switch (option) {
                case 1:
                    menu.registerMenu();
                    break;
                case 2:
                    String username = menu.Login();
                    if(username != null) {
                        menu.loggedInMenu(username);
                    }
                    break;
                case 3:
                    active = false;
                    break;
                case 4:
                    menu.printAllUsers();
                    break;
                case 5:
                    menu.printAllActivities();
                    break;
                case 6:
                    menu.printStatisticsMenu();
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}
