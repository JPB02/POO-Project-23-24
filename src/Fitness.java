import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Gere os dados para a aplicação
 * Tem suporte para serialization para guardar dados de forma persistente.
 */
public class Fitness implements Serializable {
    private Map<String, User> userMap;
    private Map<String, Activity> activityMap;
    private LocalDate currDate;

    /**
     * Construtor por omissao
     * Inicializa os maps e atualiza a data para a data atual.
     */
    public Fitness() {
        this.userMap = new HashMap<>();
        this.activityMap = new HashMap<>();
        this.currDate = LocalDate.now();
    }

    /**
     * Construtor parametrizado para começar a aplicacao
     *
     * @param userMap Um map dos ID's dos objetos utilizadores
     * @param activityMap Um map dos ID's dos objetos atividades
     * @param date Data atual
     */
    public Fitness(Map<String, User> userMap, Map<String, Activity> activityMap, ArrayList<WorkoutPlan> workoutPlans, LocalDate date) {
        this.userMap = userMap;
        this.activityMap = activityMap;
        this.currDate = currDate;
    }

    /**
     * Construtor cópia
     *
     * @param fit Um objeto cópia
     */
    public Fitness(Fitness fit) {
        this.userMap = new HashMap<>(fit.getUserMap());
        this.activityMap = new HashMap<>(fit.getActivityMap());
        this.currDate = LocalDate.of(fit.getCurrDate().getYear(), fit.getCurrDate().getMonth(), fit.getCurrDate().getDayOfMonth());
    }

    /**
     * Cria um clone do objeto.
     *
     * @return Um objeto que é uma copia do atual.
     */
    public Fitness clone() {
        return new Fitness(this);
    }

    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("Users: ").append(userMap.toString()).append('\n');
        sb.append("Activities: ").append(activityMap.toString()).append("\n");
        return sb.toString();
    }


    // ----------------------------Getter and setter methods----------------------------------------------------------

    public Map<String, User> getUserMap() {
        return userMap.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public void setUserMap(Map<String, User> userMap) {
        this.userMap =  userMap.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public Map<String, Activity> getActivityMap() {
        return activityMap.entrySet().stream().collect(Collectors.toMap(k->k.getKey(), v-> v.getValue().clone()));
    }

    public void setActivityMap(Map<String,Activity> activityMap) {
        this.activityMap = activityMap.entrySet().stream().collect(Collectors.toMap(k->k.getKey(),v->v.getValue().clone()));
    }

    public LocalDate getCurrDate() {
        return currDate;
    }

    public ArrayList<Activity> getActivityByType(String activityType){
        ArrayList<Activity> activities = new ArrayList<>();
        for (Map.Entry<String, Activity> entry : activityMap.entrySet()) {
            Activity activity = entry.getValue();
            if (activity.getActivityType().equals(activityType)) {
                activities.add(activity);
            }
        }
        return activities;
    }
// ----------------------------END OF ------Getter and setter methods----------------------------------------------------------
    /**
     * Guarda os dados num ficheiro persistente.
     */
    public void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("../file.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.flush();
            fileOut.close();
        } catch (IOException i) {
            System.out.println("Saving error");
            i.printStackTrace();
        }
    }

    /**
     * Carrega um estado de aplicação
     * @return o estado da aplicação
     */
    public Fitness load() {
        Fitness fit = new Fitness();
        try {
            FileInputStream fileIn = new FileInputStream("../file.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            fit = (Fitness) in.readObject();
            in.close();
            fileIn.close();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Loading error");
            e.printStackTrace();
        }
        return fit;
    }

}