import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class User implements Serializable {
    /*
    i) three types of user: professional, amateur, occasional
        each type of user must have different formulas associated for calories burned arithmetics

    ii) information about each user: userid(email), name, address, email, average heart rate

    iii) all registered activities must be saved, including isolated and training plan activities
    */

    // variables
    private String      name;
    private UserType    userType;
    private Gender      gender;
    private LocalDate   dateOfBirth;
    private double      height;
    private double      weight;
    private String      address;
    private String      email;
    private String      password;
    private double      avgHR;
    private Map<String, Activity> activities;

    public User() {
        this.name = "";
        this.userType = null;
        this.gender = null;
        this.dateOfBirth = LocalDate.EPOCH;
        this.height = 0;
        this.weight = 0;
        this.address = "";
        this.email = "";
        this.password = "";
        this.avgHR = 0;
        this.activities = new HashMap<>();
    }

    public User(String name, UserType userType, Gender gender, LocalDate dateOfBirth ,double height, double weight, String address, String email, String password, double avgHR, Map<String, Activity> activities) {
        this.name = name;
        this.userType = userType;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.weight = weight;
        this.address = address;
        this.email = email;
        this.password = password;
        this.avgHR = avgHR;
        this.activities = activities.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public User(String name, UserType userType, Gender gender, LocalDate dateOfBirth ,double height, double weight, String address, String email, String password, double avgHR) {
        this.name = name;
        this.userType = userType;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.height = height;
        this.weight = weight;
        this.address = address;
        this.email = email;
        this.password = password;
        this.avgHR = avgHR;
        this.activities = new HashMap<>();
    }

    public User(User other) {
        this.name = other.getName();
        this.userType = other.getUserType();
        this.gender = other.getGender();
        this.dateOfBirth = other.getDateOfBirth();
        this.height = other.getHeight();
        this.weight = other.getWeight();
        this.address = other.getAddress();
        this.email = other.getEmail();
        this.password = other.getPassword();
        this.avgHR = other.getAvgHR();
        this.activities = other.getActivities();
    }

    // getters & setters
    public Map<String, Activity> getActivities() {
        return activities.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public void setActivities(Map<String, Activity> activities) {
        this.activities =  activities.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setUserType(UserType userType){
        this.userType = userType;
    }

    public UserType getUserType(){
        return userType;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setAvgHR(double avgHR) {
        this.avgHR = avgHR;
    }

    public double getAvgHR() {
        return avgHR;
    }

    // equals

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return      Objects.equals(getName(), that.name)
                &&  Objects.equals(getUserType(), that.userType)
                &&  Objects.equals(getGender(), that.gender)
                &&  Objects.equals(getDateOfBirth(), that.dateOfBirth)
                &&  Objects.equals(getHeight(), that.height)
                &&  Objects.equals(getWeight(), that.weight)
                &&  Objects.equals(getAddress(), that.address)
                &&  Objects.equals(getEmail(), that.email)
                &&  Objects.equals(getPassword(), that.password)
                &&  Objects.equals(getAvgHR(), that.avgHR)
                && this.activities.equals(that.getActivities());
    }

    @Override
    public String toString() {
        return  "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", user type='" + userType + '\'' +
                ", gender='" + gender + '\'' +
                ", date of birth='" + dateOfBirth + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", address=" + address +
                ", avgHR=" + avgHR +
                '}';
    }

    public User clone(){
        return new User(this);
    }

}
