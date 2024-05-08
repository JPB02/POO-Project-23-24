import java.time.LocalDate;

public class Stretches extends Activity {
    private int repetitions;
    private int sets;

    public Stretches() {
        super();
        this.repetitions = 0;
        this.sets = 0;
    }

    public Stretches(String activityID, String description, LocalDate date, int duration, int repetitions, int sets) {
        super(activityID, description, date, duration);
        this.repetitions = repetitions;
        this.sets = sets;
    }

    public Stretches(Stretches other) {
        super(other);
        this.repetitions = other.getRepetitions();
        this.sets = other.getSets();
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    @Override
    public double calories() {

        return 0;
    }

    @Override
    public Activity clone() {
        return new Stretches(this);
    }
}


