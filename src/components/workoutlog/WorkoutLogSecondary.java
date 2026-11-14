package components.workoutlog;

/**
 * Layered implementations of the secondary methods for {@code WorkoutLog},
 * plus the common methods (toString, equals, hashCode). Everything here only
 * uses the kernel methods.
 *
 * @author Alok Patel
 */
public abstract class WorkoutLogSecondary implements WorkoutLog {

    /*
     * Common methods (from Object) ---------------------------------------
     */

    @Override
    public final String toString() {
        WorkoutLog temp = this.newInstance();
        StringBuilder result = new StringBuilder("{");

        while (this.size() > 0) {
            String exercise = this.anyExercise();
            int reps = this.removeExercise(exercise);
            result.append(exercise).append("=").append(reps);
            if (this.size() > 0) {
                result.append(", ");
            }
            temp.logSet(exercise, reps);
        }
        result.append("}");

        // put everything back
        this.transferFrom(temp);
        return result.toString();
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof WorkoutLog)) {
            return false;
        }
        WorkoutLog other = (WorkoutLog) obj;
        if (this.size() != other.size()) {
            return false;
        }

        WorkoutLog temp = this.newInstance();
        boolean same = true;

        while (this.size() > 0) {
            String exercise = this.anyExercise();
            int reps = this.removeExercise(exercise);
            if (!other.hasExercise(exercise)
                    || other.repsFor(exercise) != reps) {
                same = false;
            }
            temp.logSet(exercise, reps);
        }

        this.transferFrom(temp);
        return same;
    }

    @Override
    public final int hashCode() {
        WorkoutLog temp = this.newInstance();
        int hash = 0;

        while (this.size() > 0) {
            String exercise = this.anyExercise();
            int reps = this.removeExercise(exercise);
            // adding keeps the hash the same no matter what order we go in
            hash = hash + exercise.hashCode() * 31 + reps;
            temp.logSet(exercise, reps);
        }

        this.transferFrom(temp);
        return hash;
    }

    /*
     * Secondary methods ---------------------------------------------------
     */

    @Override
    public final int repsFor(String exercise) {
        assert this.hasExercise(exercise) : "Violation of: exercise is in this";

        int reps = this.removeExercise(exercise);
        this.logSet(exercise, reps);
        return reps;
    }

    @Override
    public final int totalReps() {
        WorkoutLog temp = this.newInstance();
        int total = 0;

        while (this.size() > 0) {
            String exercise = this.anyExercise();
            int reps = this.removeExercise(exercise);
            total = total + reps;
            temp.logSet(exercise, reps);
        }

        this.transferFrom(temp);
        return total;
    }

    @Override
    public final String mostTrainedExercise() {
        assert this.size() > 0 : "Violation of: |this| > 0";

        WorkoutLog temp = this.newInstance();
        String best = "";
        int bestReps = 0;

        while (this.size() > 0) {
            String exercise = this.anyExercise();
            int reps = this.removeExercise(exercise);
            if (reps > bestReps) {
                best = exercise;
                bestReps = reps;
            }
            temp.logSet(exercise, reps);
        }

        this.transferFrom(temp);
        return best;
    }

    @Override
    public final void combine(WorkoutLog other) {
        assert other != null : "Violation of: other is not null";

        // move everything out of other first, so this still works if
        // someone calls log.combine(log)
        WorkoutLog temp = other.newInstance();
        while (other.size() > 0) {
            String exercise = other.anyExercise();
            temp.logSet(exercise, other.removeExercise(exercise));
        }

        // put other back together and add each exercise into this
        while (temp.size() > 0) {
            String exercise = temp.anyExercise();
            int reps = temp.removeExercise(exercise);
            other.logSet(exercise, reps);
            this.logSet(exercise, reps);
        }
    }

    @Override
    public final boolean isEmpty() {
        return this.size() == 0;
    }

}
