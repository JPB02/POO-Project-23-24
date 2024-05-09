import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        // Create a user
        Amateur user = new Amateur();
        user.setName("John");                                                       // name
        user.setUserType("Professional");                                           // userType
        user.setDateOfBirth(LocalDate.of(1990, 5, 15));      // dateOfBirth
        user.setHeight(180);                                                        // height
        user.setWeight(75);                                                         // weight
        user.setAddress("123 Street, City, Country");                               // address
        user.setEmail("john@example.com");                                          // email
        user.setPassword("password123");                                            // password
        user.setAvgHR(70);                                                          // avgHR

        // Display the user information
        System.out.println(user);

    }
}
