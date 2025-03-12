package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

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
}
