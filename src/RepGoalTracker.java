import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.workoutlog.WorkoutLog;
import components.workoutlog.WorkoutLog1L;

/**
 * Use case 2: a small class that uses two WorkoutLogs as its representation.
 * One log holds my rep goals for the month and the other holds the reps I've
 * actually done, so I can see how close I am to each goal.
 *
 * @author Alok Patel
 */
public final class RepGoalTracker {

    /**
     * Rep goal for each exercise.
     */
    private WorkoutLog goals;

    /**
     * Reps actually done for each exercise.
     */
    private WorkoutLog done;

    /**
     * Makes a tracker with no goals and nothing logged.
     */
    public RepGoalTracker() {
        this.goals = new WorkoutLog1L();
        this.done = new WorkoutLog1L();
    }

    /**
     * Sets a rep goal for an exercise. If there's already a goal, it gets
     * replaced.
     *
     * @param exercise
     *            name of the exercise
     * @param reps
     *            the rep goal
     * @requires reps > 0
     */
    public void setGoal(String exercise, int reps) {
        if (this.goals.hasExercise(exercise)) {
            this.goals.removeExercise(exercise);
        }
        this.goals.logSet(exercise, reps);
    }

    /**
     * Logs a set I actually did.
     *
     * @param exercise
     *            name of the exercise
     * @param reps
     *            number of reps
     * @requires reps > 0
     */
    public void logSet(String exercise, int reps) {
        this.done.logSet(exercise, reps);
    }

    /**
     * Reports how many reps I've done for an exercise.
     *
     * @param exercise
     *            name of the exercise
     * @return reps done (0 if I haven't done it yet)
     */
    public int repsDone(String exercise) {
        int reps = 0;
        if (this.done.hasExercise(exercise)) {
            reps = this.done.repsFor(exercise);
        }
        return reps;
    }

    /**
     * Reports how many reps are left to hit the goal for an exercise.
     *
     * @param exercise
     *            name of the exercise
     * @return reps left (0 if the goal is already met)
     * @requires exercise has a goal
     */
    public int repsLeft(String exercise) {
        int left = this.goals.repsFor(exercise) - this.repsDone(exercise);
        return Math.max(left, 0);
    }

    /**
     * Reports how far along I am toward a goal, as a percent (capped at 100).
     *
     * @param exercise
     *            name of the exercise
     * @return percent of the goal finished
     * @requires exercise has a goal
     */
    public int percentDone(String exercise) {
        int goal = this.goals.repsFor(exercise);
        int percent = this.repsDone(exercise) * 100 / goal;
        return Math.min(percent, 100);
    }

    /**
     * Prints a progress bar for every goal.
     *
     * @param out
     *            the output stream
     * @updates out.content
     * @requires out.is_open
     */
    public void printProgress(SimpleWriter out) {
        WorkoutLog temp = this.goals.newInstance();
        int goalsMet = 0;
        int totalGoals = this.goals.size();

        while (this.goals.size() > 0) {
            String exercise = this.goals.anyExercise();
            int goal = this.goals.removeExercise(exercise);
            temp.logSet(exercise, goal);

            int percent = this.percentDoneFor(exercise, goal);
            out.println(progressBar(percent) + " " + exercise + ": "
                    + this.repsDone(exercise) + "/" + goal);
            if (percent == 100) {
                goalsMet++;
            }
        }
        this.goals.transferFrom(temp);

        out.println("Goals met: " + goalsMet + "/" + totalGoals);
    }

    /**
     * Same as percentDone, but with the goal passed in (used while the goal is
     * temporarily taken out of this.goals).
     *
     * @param exercise
     *            name of the exercise
     * @param goal
     *            the rep goal
     * @return percent of the goal finished
     */
    private int percentDoneFor(String exercise, int goal) {
        int percent = this.repsDone(exercise) * 100 / goal;
        return Math.min(percent, 100);
    }

    /**
     * Makes a 10-character progress bar like [######----].
     *
     * @param percent
     *            how full the bar is, from 0 to 100
     * @return the progress bar
     */
    private static String progressBar(int percent) {
        final int width = 10;
        int filled = percent * width / 100;
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < width; i++) {
            if (i < filled) {
                bar.append("#");
            } else {
                bar.append("-");
            }
        }
        bar.append("]");
        return bar.toString();
    }

    /**
     * Main method: sets some monthly goals and checks progress after a couple
     * of weeks.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        RepGoalTracker november = new RepGoalTracker();

        november.setGoal("pull up", 200);
        november.setGoal("push up", 500);
        november.setGoal("squat", 150);

        // Two weeks of training
        for (int day = 0; day < 10; day++) {
            november.logSet("pull up", 12);
            november.logSet("push up", 25);
        }
        for (int day = 0; day < 6; day++) {
            november.logSet("squat", 25);
        }

        out.println("=== November Rep Goals ===");
        november.printProgress(out);
        out.println();
        out.println("Pull ups left: " + november.repsLeft("pull up"));
        out.println("Push ups: " + november.percentDone("push up") + "% done");

        // Bumping the squat goal after hitting it
        november.setGoal("squat", 300);
        out.println();
        out.println("New squat goal! " + november.repsLeft("squat")
                + " reps left.");

        out.close();
    }

}
