import java.io.Serializable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class IceSkating extends Activity implements Serializable{
    private double distance;

    public IceSkating(){
        super();
        this.distance = 0;
    }

    public IceSkating(double distance){
        super();
        this.distance = distance;
    }

    public IceSkating(IceSkating other){
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
    public double calories() {
        UserType userType = getUser().getUserType();
        if(userType == UserType.Professional) {
            long age = ChronoUnit.YEARS.between(getUser().getDateOfBirth(),LocalDate.now());
            double calories = (distance * age) / 150 * 0; // FAZER FÒR
            return calories;
        }

        else {
            long age = ChronoUnit.YEARS.between(getUser().getDateOfBirth(), LocalDate.now());
            double calories = (distance * age) / 150; // FAZER FÒRMULA
            return calories;
        }

    }

    @Override
    public IceSkating clone() {
        return new IceSkating(this);
    }

    @Override
    public String toString() {
        return "Ice Skating{" +
                super.toString()+
                "distance=" + distance +
                ", calories=" + calories() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        IceSkating iceSkating = (IceSkating) o;
        return Double.compare(iceSkating.getDistance(), getDistance()) == 0;
    }

}
