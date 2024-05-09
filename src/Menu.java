import java.util.Scanner;

public class Menu {

    private Scanner sc;
    public Menu() {
        this.sc = new Scanner(System.in);
    }

    public void printStartMenu(){
        System.out.println("------------- Welcome -------------");
        System.out.println("\n1. Register");
        System.out.println("\n2. Log-in");
        System.out.println("\n3. Exit");
    }

}
