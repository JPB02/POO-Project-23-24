import java.io.Serializable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MountainBike extends Activity implements Serializable{
    private double distance;
    private double altitude;


    public MountainBike(){
        super();
        this.distance = 0;
        this.altitude = 0;
    }

    public MountainBike(String activityID, String type, LocalDate date, int duration, double distance, double altitude){
        super(activityID, type, date, duration);
        this.distance = distance;
        this.altitude = altitude;
    }

    public MountainBike(MountainBike other){
        super(other);
        this.distance = other.getDistance();
        this.altitude = other.getAltitude();
    }

    public double getDistance(){
        return distance;
    }

    public void setDistance(double distance){
        this.distance = distance;
    }

    public double getAltitude(){
        return altitude;
    }

    public void setAltitude(double altitude){
        this.altitude = altitude;
    }

    @Override
    public double calories(User user) {
        long age =  ChronoUnit.YEARS.between(user.getDateOfBirth(),LocalDate.now());
        double calories = user.caloriesFactor()*1; // FAZER FÒRMULA
        return calories;
    }

    @Override
    public Activity clone() {
        return new MountainBike(this);
    }

    @Override
    public String toString() {
        return "MountainBike{" +
                super.toString()+
                "distance=" + distance +
                ", altitude=" + altitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MountainBike mountainBike = (MountainBike) o;
        return Double.compare(mountainBike.getDistance(), getDistance()) == 0
                && Double.compare(mountainBike.getAltitude(), getAltitude()) == 0;
    }


}

