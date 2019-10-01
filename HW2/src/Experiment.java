import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Experiment class.
 * @author Goktug Akin, 161044018
 * @version HW2
 * @since 2019
 */
public class Experiment{
    private String setup;
    private int day;
    private String time;
    private boolean completed;
    private float accuracy;

    /**
     * Simple constructor that constructs an experiment.
     * @param setupVal Setup string value of experiment.
     * @param dayVal Day that experiment has been done.
     * @param accuracyVal Accuracy value of an experiment.
     * @param isCompleted Describes experiment is completed or not.
     */
    public Experiment(String setupVal,int dayVal,float accuracyVal,boolean isCompleted)
    {
        setup = setupVal;
        day = dayVal;
        accuracy = accuracyVal;
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");/*takes the current time*/
        Date date = new Date();
        time=dateFormat.format(date);
        completed=isCompleted;
    }

    /**
     * Copy constructor.
     * @param copy other experiment object(ref).
     */
    public Experiment(Experiment copy)/* copy constructor*/
    {
        setup=copy.setup;
        day=copy.day;
        time=copy.time;
        completed=copy.completed;
        accuracy=copy.accuracy;
    }

    /**
     * Gets the day of an experiment.
     * @return day of an experiment.
     */
    public int getDay() { return day; }

    /**
     * Gets the time of an experiment.
     * @return Time of an experiment as a string.
     */
    public String getTime()
    {
        return time;
    }

    /**
     * Returns the experiment's completion condiiton.
     * @return true if the experiment is completed.
     */
    public boolean isCompleted() { return completed; }

    /**
     * Gets the accuracy of an experiment.
     * @return accuracy of an experiment.
     */
    public double getAccuracy() { return accuracy; }
    @Override
    public String toString()
    {
        String completed = isCompleted() ?  "completed" : "not completed";
        return String.format("Day:"+day+" "+time+" | "+setup+" | "+"Acc: %%"+accuracy+" | "+completed);
    }

}