package components.workoutlog;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * JUnit tests for the secondary methods and common methods (toString, equals,
 * hashCode) in {@code WorkoutLogSecondary}.
 *
 * @author Alok Patel
 */
public class WorkoutLogTest {

    /**
     * Makes a log with squat = 15, bench press = 26, and pull up = 18.
     *
     * @return a sample workout log
     */
    private static WorkoutLog sampleLog() {
        WorkoutLog log = new WorkoutLog1L();
        log.logSet("squat", 15);
        log.logSet("bench press", 26);
        log.logSet("pull up", 18);
        return log;
    }

    // repsFor

    @Test
    public void testRepsForOnlyExercise() {
        WorkoutLog log = new WorkoutLog1L();
        log.logSet("squat", 5);
        WorkoutLog expected = new WorkoutLog1L();
        expected.logSet("squat", 5);

        assertEquals(5, log.repsFor("squat"));
        assertEquals(expected, log);
    }

    @Test
    public void testRepsForManyExercises() {
        WorkoutLog log = sampleLog();

        assertEquals(26, log.repsFor("bench press"));
        assertEquals(sampleLog(), log);
    }

    // totalReps

    @Test
    public void testTotalRepsEmpty() {
        WorkoutLog log = new WorkoutLog1L();

        assertEquals(0, log.totalReps());
        assertEquals(0, log.size());
    }

    @Test
    public void testTotalRepsOneExercise() {
        WorkoutLog log = new WorkoutLog1L();
        log.logSet("squat", 5);
        log.logSet("squat", 5);

        assertEquals(10, log.totalReps());
        assertEquals(1, log.size());
    }

    @Test
    public void testTotalRepsManyExercises() {
        WorkoutLog log = sampleLog();

        assertEquals(59, log.totalReps());
        assertEquals(sampleLog(), log);
    }

    // mostTrainedExercise

    @Test
    public void testMostTrainedOnlyExercise() {
        WorkoutLog log = new WorkoutLog1L();
        log.logSet("squat", 5);

        assertEquals("squat", log.mostTrainedExercise());
        assertEquals(1, log.size());
    }

    @Test
    public void testMostTrainedManyExercises() {
        WorkoutLog log = sampleLog();

        assertEquals("bench press", log.mostTrainedExercise());
        assertEquals(sampleLog(), log);
    }

    @Test
    public void testMostTrainedTieReturnsOneOfThem() {
        WorkoutLog log = new WorkoutLog1L();
        log.logSet("squat", 10);
        log.logSet("bench press", 10);
        log.logSet("curl", 4);

        String most = log.mostTrainedExercise();

        assertEquals(true, most.equals("squat") || most.equals("bench press"));
        assertEquals(3, log.size());
    }

    // combine

    @Test
    public void testCombineNoOverlap() {
        WorkoutLog log = new WorkoutLog1L();
        log.logSet("squat", 15);
        WorkoutLog other = new WorkoutLog1L();
        other.logSet("deadlift", 5);
        WorkoutLog expected = new WorkoutLog1L();
        expected.logSet("squat", 15);
        expected.logSet("deadlift", 5);
        WorkoutLog otherCopy = new WorkoutLog1L();
        otherCopy.logSet("deadlift", 5);

        log.combine(other);

        assertEquals(expected, log);
        assertEquals(otherCopy, other);
    }

    @Test
    public void testCombineOverlapAddsReps() {
        WorkoutLog log = new WorkoutLog1L();
        log.logSet("bench press", 16);
        log.logSet("squat", 15);
        WorkoutLog other = new WorkoutLog1L();
        other.logSet("bench press", 10);
        WorkoutLog expected = new WorkoutLog1L();
        expected.logSet("bench press", 26);
        expected.logSet("squat", 15);

        log.combine(other);

        assertEquals(expected, log);
        assertEquals(10, other.repsFor("bench press"));
        assertEquals(1, other.size());
    }

    @Test
    public void testCombineIntoEmpty() {
        WorkoutLog log = new WorkoutLog1L();
        WorkoutLog other = sampleLog();

        log.combine(other);

        assertEquals(sampleLog(), log);
        assertEquals(sampleLog(), other);
    }

    @Test
    public void testCombineWithEmpty() {
        WorkoutLog log = sampleLog();
        WorkoutLog other = new WorkoutLog1L();

        log.combine(other);

        assertEquals(sampleLog(), log);
        assertEquals(0, other.size());
    }

    @Test
    public void testCombineWithItselfDoublesReps() {
        WorkoutLog log = new WorkoutLog1L();
        log.logSet("squat", 5);
        log.logSet("bench press", 8);
        WorkoutLog expected = new WorkoutLog1L();
        expected.logSet("squat", 10);
        expected.logSet("bench press", 16);

        log.combine(log);

        assertEquals(expected, log);
    }

    // isEmpty

    @Test
    public void testIsEmptyTrue() {
        WorkoutLog log = new WorkoutLog1L();

        assertEquals(true, log.isEmpty());
    }

    @Test
    public void testIsEmptyFalse() {
        WorkoutLog log = sampleLog();

        assertEquals(false, log.isEmpty());
        assertEquals(sampleLog(), log);
    }

    // toString

    @Test
    public void testToStringEmpty() {
        WorkoutLog log = new WorkoutLog1L();

        assertEquals("{}", log.toString());
    }

    @Test
    public void testToStringOneExercise() {
        WorkoutLog log = new WorkoutLog1L();
        log.logSet("squat", 15);

        assertEquals("{squat=15}", log.toString());
        assertEquals(1, log.size());
    }

    @Test
    public void testToStringManyHasEveryExercise() {
        WorkoutLog log = sampleLog();

        // order isn't guaranteed, so just check each piece is there
        String result = log.toString();

        assertEquals(true, result.startsWith("{") && result.endsWith("}"));
        assertEquals(true, result.contains("squat=15"));
        assertEquals(true, result.contains("bench press=26"));
        assertEquals(true, result.contains("pull up=18"));
        assertEquals(sampleLog(), log);
    }

    // equals and hashCode

    @Test
    public void testEqualsBothEmpty() {
        WorkoutLog a = new WorkoutLog1L();
        WorkoutLog b = new WorkoutLog1L();

        assertEquals(true, a.equals(b));
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void testEqualsSameLoggedDifferently() {
        WorkoutLog a = new WorkoutLog1L();
        a.logSet("squat", 5);
        a.logSet("squat", 10);
        a.logSet("bench press", 8);
        WorkoutLog b = new WorkoutLog1L();
        b.logSet("bench press", 8);
        b.logSet("squat", 15);

        assertEquals(true, a.equals(b));
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void testEqualsItself() {
        WorkoutLog log = sampleLog();

        assertEquals(true, log.equals(log));
    }

    @Test
    public void testNotEqualsDifferentReps() {
        WorkoutLog a = new WorkoutLog1L();
        a.logSet("squat", 15);
        WorkoutLog b = new WorkoutLog1L();
        b.logSet("squat", 14);

        assertNotEquals(a, b);
    }

    @Test
    public void testNotEqualsDifferentExercise() {
        WorkoutLog a = new WorkoutLog1L();
        a.logSet("squat", 15);
        WorkoutLog b = new WorkoutLog1L();
        b.logSet("deadlift", 15);

        assertNotEquals(a, b);
    }

    @Test
    public void testNotEqualsDifferentSize() {
        WorkoutLog a = sampleLog();
        WorkoutLog b = sampleLog();
        b.logSet("curl", 12);

        assertNotEquals(a, b);
    }

    @Test
    public void testNotEqualsNullOrOtherType() {
        WorkoutLog log = sampleLog();

        assertEquals(false, log.equals(null));
        assertEquals(false, log.equals("squat=15"));
    }

}
