import java.util.HashMap;
import java.util.Map;

/**
 * Proof of concept for my WorkoutLog component. It keeps track of the total
 * reps I've done for each exercise.
 *
 * @author Alok Patel
 */
public final class WorkoutLogProof {

    /**
     * Exercise name -> total reps.
     */
    private Map<String, Integer> log;

    /**
     * Makes an empty workout log.
     */
    public WorkoutLogProof() {
        this.log = new HashMap<>();
    }

    /**
     * Adds reps to an exercise. If the exercise isn't in the log yet, it gets
     * added.
     *
     * @param exercise
     *            name of the exercise
     * @param reps
     *            number of reps
     * @requires reps > 0
     */
    public void logSet(String exercise, int reps) {
        if (this.log.containsKey(exercise)) {
            this.log.put(exercise, this.log.get(exercise) + reps);
        } else {
            this.log.put(exercise, reps);
        }
    }

    /**
     * Removes an exercise from the log.
     *
     * @param exercise
     *            name of the exercise
     * @return the total reps the exercise had
     * @requires exercise is in this
     */
    public int removeExercise(String exercise) {
        return this.log.remove(exercise);
    }

    /**
     * Gives back the name of some exercise in the log (doesn't remove it).
     *
     * @return some exercise in this
     * @requires size() > 0
     */
    public String anyExercise() {
        return this.log.keySet().iterator().next();
    }

    /**
     * Checks if an exercise is in the log.
     *
     * @param exercise
     *            name of the exercise
     * @return true if exercise is in this
     */
    public boolean hasExercise(String exercise) {
        return this.log.containsKey(exercise);
    }

    /**
     * Number of different exercises in the log.
     *
     * @return the number of exercises
     */
    public int size() {
        return this.log.size();
    }

    // The methods below only use the methods above, so they can become
    // secondary methods later.

    /**
     * Adds up the reps for every exercise.
     *
     * @return total reps in this
     */
    public int totalReps() {
        WorkoutLogProof copy = new WorkoutLogProof();
        int total = 0;

        while (this.size() > 0) {
            String exercise = this.anyExercise();
            int reps = this.removeExercise(exercise);
            total = total + reps;
            copy.logSet(exercise, reps);
        }

        // put everything back
        this.log = copy.log;
        return total;
    }

    /**
     * Finds the exercise with the most reps.
     *
     * @return the most trained exercise
     * @requires size() > 0
     */
    public String mostTrainedExercise() {
        WorkoutLogProof copy = new WorkoutLogProof();
        String best = "";
        int bestReps = 0;

        while (this.size() > 0) {
            String exercise = this.anyExercise();
            int reps = this.removeExercise(exercise);
            if (reps > bestReps) {
                best = exercise;
                bestReps = reps;
            }
            copy.logSet(exercise, reps);
        }

        this.log = copy.log;
        return best;
    }

    /**
     * Adds all of other's exercises and reps into this log. other stays the
     * same.
     *
     * @param other
     *            the log to add in
     */
    public void combine(WorkoutLogProof other) {
        WorkoutLogProof copy = new WorkoutLogProof();

        while (other.size() > 0) {
            String exercise = other.anyExercise();
            int reps = other.removeExercise(exercise);
            this.logSet(exercise, reps);
            copy.logSet(exercise, reps);
        }

        other.log = copy.log;
    }

    @Override
    public String toString() {
        return this.log.toString();
    }

    /**
     * Logs two gym days and combines them into a week.
     *
     * @param args
     *            not used
     */
    public static void main(String[] args) {
        WorkoutLogProof monday = new WorkoutLogProof();
        monday.logSet("squat", 5);
        monday.logSet("squat", 5);
        monday.logSet("squat", 5);
        monday.logSet("bench press", 8);
        monday.logSet("bench press", 8);

        WorkoutLogProof wednesday = new WorkoutLogProof();
        wednesday.logSet("deadlift", 5);
        wednesday.logSet("pull up", 10);
        wednesday.logSet("pull up", 8);
        wednesday.logSet("bench press", 10);

        System.out.println("Monday: " + monday);
        System.out.println("Wednesday: " + wednesday);

        WorkoutLogProof week = new WorkoutLogProof();
        week.combine(monday);
        week.combine(wednesday);

        System.out.println("This week: " + week);
        System.out.println("Exercises: " + week.size());
        System.out.println("Total reps: " + week.totalReps());
        System.out.println("Most trained: " + week.mostTrainedExercise());
        System.out.println("Did rows? " + week.hasExercise("barbell row"));

        int pullUps = week.removeExercise("pull up");
        System.out.println("Removed pull ups (" + pullUps + " reps): " + week);
    }

}
