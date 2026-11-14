package components.workoutlog;

/**
 * {@code WorkoutLogKernel} enhanced with secondary methods.
 */
public interface WorkoutLog extends WorkoutLogKernel {

    /**
     * Reports the total reps for an exercise without changing the log.
     *
     * @param exercise
     *            name of the exercise
     * @return the total reps for exercise
     * @requires exercise is in DOMAIN(this)
     * @ensures (exercise, repsFor) is in this
     */
    int repsFor(String exercise);

    /**
     * Adds up the reps for every exercise in the log.
     *
     * @return total reps across all exercises
     * @ensures totalReps = sum of r for all (e, r) in this
     */
    int totalReps();

    /**
     * Reports the exercise with the most reps. If there's a tie, any of the
     * tied exercises can be reported.
     *
     * @return the most trained exercise
     * @requires |this| > 0
     * @ensures <pre>
     * mostTrainedExercise is in DOMAIN(this)  and
     * for all (e, r): EXERCISE_ENTRY where ((e, r) is in this)
     *   (r <= reps for mostTrainedExercise)
     * </pre>
     */
    String mostTrainedExercise();

    /**
     * Adds every exercise and its reps from other into this log. If an exercise
     * is in both, the reps are added together. other is not changed.
     *
     * @param other
     *            the log to combine into this one
     * @updates this
     * @ensures <pre>
     * DOMAIN(this) = DOMAIN(#this) union DOMAIN(other)  and
     * for all e: string of character where (e is in DOMAIN(this))
     *   (reps for e in this =
     *      reps for e in #this + reps for e in other)
     * </pre> (where an exercise that is missing from a log counts as 0 reps)
     */
    void combine(WorkoutLog other);

    /**
     * Reports whether the log has no exercises.
     *
     * @return true if the log is empty
     * @ensures isEmpty = (|this| = 0)
     */
    boolean isEmpty();

}
