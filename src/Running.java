import java.io.Serializable;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


/**
 * Representa a atividade running e os seus parametros
 */
public class Running extends Distance implements Serializable{
    private double pace; // minutes por km
    private int steps;

    /**
     * Construtor por omissao
     */
    public Running(){
        super();
        this.pace = 0.0;
        this.steps = 0;
    }

    /**
     * Construtor parametrizado
     *

     * @param distance Distancia percorrida
     */
    public Running(String activityID, String type, LocalDate date, int duration, double distance, double pace, int steps, boolean isHard){
        super(activityID, type, date, duration, distance, isHard);
        this.pace = pace;
        this.steps = steps;
    }

    /**
     * Construtor cópia
     *
     * @param other Recebe uma objeto da classe
     */
    public Running(Running other){
        super(other);
        this.pace = other.getPace();
        this.steps = other.getSteps();
    }
    // ----------------------------Getter and setter methods----------------------------------------------------------

    public double getPace(){
        return this.pace;
    }

    public void setPace(double pace){
        this.pace = pace;
    }

    public int getSteps() {
        return this.steps;
    }

    public void setSteps(int steps){
        this.steps = steps;
    }

    public int calculateSteps(double distance){
        return 1000 * (int)(distance);
    }

    public double calculatePace(double distance, int duration){
        return duration/distance;
    }

    public int calculateMETRunning() {
        int MET = 8;
        if(pace >= 6) {
            return MET;
        } else if (pace < 6 && pace >= 5) {
            MET = 10;
        }
        else if(pace < 5) {
            MET = 12;
        }
        return MET;
    }

    // ----------------------------END OF ------Getter and setter methods----------------------------------------------------------

    @Override
    public double calories(User user) {
        long age =  ChronoUnit.YEARS.between(user.getDateOfBirth(),LocalDate.now());
        double calories = calculateMETRunning() * getDuration() * user.getWeight()*user.caloriesFactor()*1;
        return calories/70;
    }

    @Override
    public Activity clone() {
        return new Running(this);
    }

    // Creating DecimalFormat object with two decimal place pattern
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public String toString() {
        String formattedPace = df.format(this.pace);
        return "Running: \n" +
                super.toString()+
                "\nPace: " + formattedPace+
                "\nSteps: " + this.steps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Running running = (Running) o;
        return Double.compare(running.getPace(), this.getPace()) == 0
                && running.getSteps() == this.getSteps();
    }
}

