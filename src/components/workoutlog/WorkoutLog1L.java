package components.workoutlog;

import components.map.Map;
import components.map.Map1L;

/**
 * {@code WorkoutLog} represented as a {@code Map} from exercise name to total
 * reps, with implementations of the kernel and Standard methods.
 *
 * @convention <pre>
 * every value in $this.rep is > 0
 * </pre>
 * @correspondence <pre>
 * this = the set of (exercise, reps) pairs in $this.rep
 * (each key is an exercise and its value is the total reps for it)
 * </pre>
 *
 * @author Alok
 */
public class WorkoutLog1L extends WorkoutLogSecondary {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Representation of {@code this}: exercise name -> total reps.
     */
    private Map<String, Integer> rep;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.rep = new Map1L<>();
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor. Makes an empty workout log.
     */
    public WorkoutLog1L() {
        this.createNewRep();
    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @Override
    public final WorkoutLog newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(WorkoutLog source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof WorkoutLog1L : ""
                + "Violation of: source is of dynamic type WorkoutLog1L";

        WorkoutLog1L localSource = (WorkoutLog1L) source;
        this.rep = localSource.rep;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void logSet(String exercise, int reps) {
        assert exercise != null : "Violation of: exercise is not null";
        assert reps > 0 : "Violation of: reps > 0";

        if (this.rep.hasKey(exercise)) {
            int current = this.rep.value(exercise);
            this.rep.replaceValue(exercise, current + reps);
        } else {
            this.rep.add(exercise, reps);
        }
    }

    @Override
    public final int removeExercise(String exercise) {
        assert exercise != null : "Violation of: exercise is not null";
        assert this.rep.hasKey(exercise) : "Violation of: exercise is in this";

        Map.Pair<String, Integer> removed = this.rep.remove(exercise);
        return removed.value();
    }

    @Override
    public final String anyExercise() {
        assert this.rep.size() > 0 : "Violation of: |this| > 0";

        // take one out to see its name, then put it right back
        Map.Pair<String, Integer> pair = this.rep.removeAny();
        this.rep.add(pair.key(), pair.value());
        return pair.key();
    }

    @Override
    public final boolean hasExercise(String exercise) {
        assert exercise != null : "Violation of: exercise is not null";

        return this.rep.hasKey(exercise);
    }

    @Override
    public final int size() {
        return this.rep.size();
    }

}
