import java.io.Serializable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Running extends Activity implements Serializable{
    private double distance;

    public Running(){
        super();
        this.distance = 0;
    }

    public Running(String activityID, String type, LocalDate date, int duration, double distance){
        super(activityID, type, date, duration);
        this.distance = distance;
    }

    public Running(Running other){
        super(other);
        this.distance = other.getDistance();
    }

    public double getDistance(){
        return distance;
    }

    public void setDistance(double distance){
        this.distance = distance;
    }

    @Override
    public double calories(User user) {
        long age =  ChronoUnit.YEARS.between(user.getDateOfBirth(),LocalDate.now());
        double calories = user.caloriesFactor()*1; // FAZER FÒRMULA
        return calories;
    }

    @Override
    public Activity clone() {
        return new Running(this);
    }

    @Override
    public String toString() {
        return "Running: \n" +
                super.toString()+
                "\nDistance: " + distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Running running = (Running) o;
        return Double.compare(running.getDistance(), getDistance()) == 0;
    }


}

