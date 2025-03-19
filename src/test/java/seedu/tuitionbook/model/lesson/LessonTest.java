package seedu.tuitionbook.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tuitionbook.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class LessonTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Lesson(null, null));
    }

    @Test
    public void isValidModuleName() {
        // null address
        assertThrows(NullPointerException.class, () -> Lesson.isValidModuleName(null));

        // invalid addresses
        assertFalse(Lesson.isValidModuleName("")); // empty string
        assertFalse(Lesson.isValidModuleName(" ")); // spaces only
        assertFalse(Lesson.isValidModuleName("Bad-Module")); // bad chars included

        // valid addresses
        assertTrue(Lesson.isValidModuleName("Maths")); // single words
        assertTrue(Lesson.isValidModuleName("Elementary Mathematics")); // with spaces
    }

    @Test
    public void isValidLessonDate() {
        // null string
        assertThrows(NullPointerException.class, () -> Lesson.isValidLessonDate(null));

        // invalid date string
        assertFalse(Lesson.isValidLessonDate(""));
        assertFalse(Lesson.isValidLessonDate(" "));
        assertFalse(Lesson.isValidLessonDate("not a date"));
        assertFalse(Lesson.isValidLessonDate("2024-12-12 12:00:00"));

        // valid date string
        assertTrue(Lesson.isValidLessonDate("2024-12-12T12:00:00"));
    }

    @Test
    public void equals() {
        LocalDateTime validDateTime = LocalDateTime.of(2025, 3, 12, 18, 0);
        Lesson lesson = new Lesson("Valid Module", validDateTime);

        // same values -> returns true
        assertTrue(lesson.equals(new Lesson("Valid Module", validDateTime)));

        // same object -> returns true
        assertTrue(lesson.equals(lesson));

        // null -> returns false
        assertFalse(lesson.equals(null));

        // different types -> returns false
        assertFalse(lesson.equals(5.0f));

        // different values -> returns false
        assertFalse(lesson.equals(new Lesson("Different Module", validDateTime)));
    }

    @Test
    public void toStringTest() {
        LocalDateTime validDateTime = LocalDateTime.of(2025, 3, 12, 18, 0);
        Lesson lesson = new Lesson("Valid Module", validDateTime);

        assertTrue(lesson.toString().equals("Valid Module | 12 Mar 2025, 18:00:00"));

        assertFalse(lesson.toString().equals("Valid Module | 2025-03-12T18:00:00"));
    }
}
