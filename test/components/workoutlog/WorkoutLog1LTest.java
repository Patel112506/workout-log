package components.workoutlog;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * JUnit tests for the kernel and Standard methods of {@code WorkoutLog1L}.
 *
 * @author Alok Patel
 */
public class WorkoutLog1LTest {

    // Constructor

    @Test
    public void testConstructorIsEmpty() {
        WorkoutLog log = new WorkoutLog1L();

        assertEquals(0, log.size());
    }

    // logSet

    @Test
    public void testLogSetNewExerciseOnEmpty() {
        WorkoutLog log = new WorkoutLog1L();

        log.logSet("squat", 5);

        assertEquals(1, log.size());
        assertEquals(true, log.hasExercise("squat"));
        assertEquals(5, log.removeExercise("squat"));
    }

    @Test
    public void testLogSetExistingExerciseAddsReps() {
        WorkoutLog log = new WorkoutLog1L();
        log.logSet("squat", 5);

        log.logSet("squat", 8);

        assertEquals(1, log.size());
        assertEquals(13, log.removeExercise("squat"));
    }

    @Test
    public void testLogSetNewExerciseOnNonEmpty() {
        WorkoutLog log = new WorkoutLog1L();
        log.logSet("squat", 5);

        log.logSet("bench press", 10);

        assertEquals(2, log.size());
        assertEquals(5, log.removeExercise("squat"));
        assertEquals(10, log.removeExercise("bench press"));
    }

    @Test
    public void testLogSetOneRep() {
        WorkoutLog log = new WorkoutLog1L();

        log.logSet("deadlift", 1);

        assertEquals(1, log.removeExercise("deadlift"));
    }

    // removeExercise

    @Test
    public void testRemoveExerciseOnlyOne() {
        WorkoutLog log = new WorkoutLog1L();
        log.logSet("squat", 5);

        int reps = log.removeExercise("squat");

        assertEquals(5, reps);
        assertEquals(0, log.size());
        assertEquals(false, log.hasExercise("squat"));
    }

    @Test
    public void testRemoveExerciseLeavesOthers() {
        WorkoutLog log = new WorkoutLog1L();
        log.logSet("squat", 5);
        log.logSet("bench press", 8);
        log.logSet("pull up", 10);
        WorkoutLog expected = new WorkoutLog1L();
        expected.logSet("squat", 5);
        expected.logSet("pull up", 10);

        int reps = log.removeExercise("bench press");

        assertEquals(8, reps);
        assertEquals(expected, log);
    }

    // anyExercise

    @Test
    public void testAnyExerciseOnlyOne() {
        WorkoutLog log = new WorkoutLog1L();
        log.logSet("squat", 5);
        WorkoutLog expected = new WorkoutLog1L();
        expected.logSet("squat", 5);

        String exercise = log.anyExercise();

        assertEquals("squat", exercise);
        assertEquals(expected, log);
    }

    @Test
    public void testAnyExerciseManyIsInLogAndRestores() {
        WorkoutLog log = new WorkoutLog1L();
        log.logSet("squat", 5);
        log.logSet("bench press", 8);
        log.logSet("pull up", 10);
        WorkoutLog expected = new WorkoutLog1L();
        expected.logSet("squat", 5);
        expected.logSet("bench press", 8);
        expected.logSet("pull up", 10);

        String exercise = log.anyExercise();

        assertEquals(true, log.hasExercise(exercise));
        assertEquals(expected, log);
    }

    // hasExercise

    @Test
    public void testHasExerciseEmpty() {
        WorkoutLog log = new WorkoutLog1L();

        assertEquals(false, log.hasExercise("squat"));
        assertEquals(0, log.size());
    }

    @Test
    public void testHasExerciseTrue() {
        WorkoutLog log = new WorkoutLog1L();
        log.logSet("squat", 5);
        WorkoutLog expected = new WorkoutLog1L();
        expected.logSet("squat", 5);

        assertEquals(true, log.hasExercise("squat"));
        assertEquals(expected, log);
    }

    @Test
    public void testHasExerciseFalseNonEmpty() {
        WorkoutLog log = new WorkoutLog1L();
        log.logSet("squat", 5);
        WorkoutLog expected = new WorkoutLog1L();
        expected.logSet("squat", 5);

        assertEquals(false, log.hasExercise("Squat"));
        assertEquals(expected, log);
    }

    // size

    @Test
    public void testSizeEmpty() {
        WorkoutLog log = new WorkoutLog1L();

        assertEquals(0, log.size());
    }

    @Test
    public void testSizeCountsExercisesNotSets() {
        WorkoutLog log = new WorkoutLog1L();
        log.logSet("squat", 5);
        log.logSet("squat", 5);
        log.logSet("squat", 5);
        log.logSet("bench press", 8);

        assertEquals(2, log.size());
    }

    // Standard methods: clear, newInstance, transferFrom ----------------------

    @Test
    public void testClearEmpty() {
        WorkoutLog log = new WorkoutLog1L();

        log.clear();

        assertEquals(0, log.size());
    }

    @Test
    public void testClearNonEmpty() {
        WorkoutLog log = new WorkoutLog1L();
        log.logSet("squat", 5);
        log.logSet("bench press", 8);

        log.clear();

        assertEquals(0, log.size());
        assertEquals(false, log.hasExercise("squat"));
    }

    @Test
    public void testNewInstanceIsEmptyAndSameType() {
        WorkoutLog log = new WorkoutLog1L();
        log.logSet("squat", 5);
        WorkoutLog expected = new WorkoutLog1L();
        expected.logSet("squat", 5);

        WorkoutLog fresh = log.newInstance();

        assertEquals(0, fresh.size());
        assertEquals(WorkoutLog1L.class, fresh.getClass());
        assertEquals(expected, log);
    }

    @Test
    public void testTransferFromNonEmpty() {
        WorkoutLog log = new WorkoutLog1L();
        WorkoutLog source = new WorkoutLog1L();
        source.logSet("squat", 5);
        source.logSet("bench press", 8);
        WorkoutLog expected = new WorkoutLog1L();
        expected.logSet("squat", 5);
        expected.logSet("bench press", 8);

        log.transferFrom(source);

        assertEquals(expected, log);
        assertEquals(0, source.size());
    }

    @Test
    public void testTransferFromReplacesOldContents() {
        WorkoutLog log = new WorkoutLog1L();
        log.logSet("deadlift", 3);
        WorkoutLog source = new WorkoutLog1L();
        source.logSet("squat", 5);
        WorkoutLog expected = new WorkoutLog1L();
        expected.logSet("squat", 5);

        log.transferFrom(source);

        assertEquals(expected, log);
        assertEquals(false, log.hasExercise("deadlift"));
        assertEquals(0, source.size());
    }

    @Test
    public void testTransferFromEmpty() {
        WorkoutLog log = new WorkoutLog1L();
        log.logSet("squat", 5);
        WorkoutLog source = new WorkoutLog1L();

        log.transferFrom(source);

        assertEquals(0, log.size());
        assertEquals(0, source.size());
    }

}
