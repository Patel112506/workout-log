package components.workoutlog;

import components.standard.Standard;

/**
 * Kernel interface for WorkoutLog. A workout log keeps track of the total
 * reps done for each exercise.
 *
 * @mathsubtypes <pre>
 * EXERCISE_ENTRY is (
 *   exercise: string of character,
 *   reps: integer
 *  )
 *  exemplar (e, r)
 *  constraint r > 0
 * </pre>
 * @mathmodel type WorkoutLogKernel is modeled by finite set of EXERCISE_ENTRY
 * @constraint <pre>
 * for all e1, e2: EXERCISE_ENTRY
 *     where (e1 is in this and e2 is in this and e1.exercise = e2.exercise)
 *   (e1 = e2)
 * </pre>
 * @initially <pre>
 * ():
 *  ensures
 *   this = {}
 * </pre>
 */
public interface WorkoutLogKernel extends Standard<WorkoutLog> {

    /**
     * Adds reps to an exercise. If the exercise isn't in the log yet, it gets
     * added with that many reps.
     *
     * @param exercise
     *            name of the exercise
     * @param reps
     *            number of reps to add
     * @updates this
     * @requires reps > 0
     * @ensures <pre>
     * if exercise is in DOMAIN(#this) then
     *   this = (#this \ {(exercise, #reps for exercise)})
     *          union {(exercise, #reps for exercise + reps)}
     * else
     *   this = #this union {(exercise, reps)}
     * </pre>
     */
    void logSet(String exercise, int reps);

    /**
     * Removes an exercise from the log and reports how many reps it had.
     *
     * @param exercise
     *            name of the exercise to remove
     * @return the total reps the exercise had
     * @updates this
     * @requires exercise is in DOMAIN(this)
     * @ensures <pre>
     * (exercise, removeExercise) is in #this  and
     * this = #this \ {(exercise, removeExercise)}
     * </pre>
     */
    int removeExercise(String exercise);

    /**
     * Reports the name of some exercise in the log without removing it.
     *
     * @return some exercise in this
     * @requires |this| > 0
     * @ensures anyExercise is in DOMAIN(this)
     */
    String anyExercise();

    /**
     * Reports whether an exercise is in the log.
     *
     * @param exercise
     *            name of the exercise
     * @return true if exercise is in the log
     * @ensures hasExercise = (exercise is in DOMAIN(this))
     */
    boolean hasExercise(String exercise);

    /**
     * Reports how many different exercises are in the log.
     *
     * @return the number of exercises
     * @ensures size = |this|
     */
    int size();

}
