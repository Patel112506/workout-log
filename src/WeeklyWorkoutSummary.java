import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.workoutlog.WorkoutLog;
import components.workoutlog.WorkoutLog1L;

/**
 * Use case 1: log a week of gym sessions, then combine them into one weekly
 * summary that shows total volume and what I trained the most.
 *
 * @author Alok Patel
 */
public final class WeeklyWorkoutSummary {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private WeeklyWorkoutSummary() {
    }

    /**
     * Prints a summary of one day's workout.
     *
     * @param out
     *            the output stream
     * @param day
     *            name of the day
     * @param log
     *            that day's workout log
     * @updates out.content
     * @requires out.is_open
     */
    private static void printDay(SimpleWriter out, String day,
            WorkoutLog log) {
        out.println(day + ": " + log + " (" + log.totalReps() + " reps)");
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();

        // Push day
        WorkoutLog monday = new WorkoutLog1L();
        monday.logSet("bench press", 8);
        monday.logSet("bench press", 8);
        monday.logSet("bench press", 6);
        monday.logSet("shoulder press", 10);
        monday.logSet("shoulder press", 10);
        monday.logSet("tricep pushdown", 12);
        monday.logSet("tricep pushdown", 12);

        // Pull day
        WorkoutLog wednesday = new WorkoutLog1L();
        wednesday.logSet("deadlift", 5);
        wednesday.logSet("deadlift", 5);
        wednesday.logSet("pull up", 10);
        wednesday.logSet("pull up", 8);
        wednesday.logSet("barbell row", 10);
        wednesday.logSet("bicep curl", 12);

        // Leg day
        WorkoutLog friday = new WorkoutLog1L();
        friday.logSet("squat", 5);
        friday.logSet("squat", 5);
        friday.logSet("squat", 5);
        friday.logSet("leg press", 12);
        friday.logSet("leg press", 12);
        friday.logSet("calf raise", 20);

        out.println("=== Daily Logs ===");
        printDay(out, "Monday", monday);
        printDay(out, "Wednesday", wednesday);
        printDay(out, "Friday", friday);

        // Combine every day into one week
        WorkoutLog week = new WorkoutLog1L();
        week.combine(monday);
        week.combine(wednesday);
        week.combine(friday);

        out.println();
        out.println("=== Weekly Summary ===");
        out.println("Different exercises: " + week.size());
        out.println("Total reps: " + week.totalReps());
        out.println("Most trained: " + week.mostTrainedExercise() + " ("
                + week.repsFor(week.mostTrainedExercise()) + " reps)");

        // Check if I skipped anything I meant to do
        String[] plan = { "bench press", "squat", "deadlift", "lunges" };
        out.println();
        out.println("=== Did I Hit My Plan? ===");
        for (String exercise : plan) {
            if (week.hasExercise(exercise)) {
                out.println("[x] " + exercise);
            } else {
                out.println("[ ] " + exercise + " (skipped this week)");
            }
        }

        out.close();
    }

}
