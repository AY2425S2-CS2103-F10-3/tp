package seedu.tuitionbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tuitionbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tuitionbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tuitionbook.logic.commands.LessonListCommand.NO_LESSONS_TO_DISPLAY;
import static seedu.tuitionbook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tuitionbook.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.tuitionbook.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.tuitionbook.commons.core.index.Index;
import seedu.tuitionbook.logic.Messages;
import seedu.tuitionbook.model.Model;
import seedu.tuitionbook.model.ModelManager;
import seedu.tuitionbook.model.UserPrefs;
import seedu.tuitionbook.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code LessonListCommand}.
 */
public class LessonListCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        LessonListCommand lessonListCommand = new LessonListCommand(INDEX_FIRST_PERSON);

        String expectedMessage = LessonListCommand.LIST_LESSONS_MESSAGE + personToList.getName();
        String expectedLessons = personToList.getName() + "'s Lessons:\n\n"
                + personToList.getLessons().stream()
                .map(lesson -> lesson.toString())
                .reduce((a, b) -> a + "\n" + b)
                .orElse(NO_LESSONS_TO_DISPLAY);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(lessonListCommand, model, expectedMessage, expectedModel, expectedLessons);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        LessonListCommand lessonListCommand = new LessonListCommand(outOfBoundIndex);

        assertCommandFailure(lessonListCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        LessonListCommand lessonListFirstCommand = new LessonListCommand(INDEX_FIRST_PERSON);
        LessonListCommand lessonListSecondCommand = new LessonListCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(lessonListFirstCommand.equals(lessonListFirstCommand));

        // same values -> returns true
        LessonListCommand lessonListFirstCommandCopy = new LessonListCommand(INDEX_FIRST_PERSON);
        assertTrue(lessonListFirstCommand.equals(lessonListFirstCommandCopy));

        // different types -> returns false
        assertFalse(lessonListFirstCommand.equals(1));

        // null -> returns false
        assertFalse(lessonListFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(lessonListFirstCommand.equals(lessonListSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        LessonListCommand lessonListCommand = new LessonListCommand(targetIndex);
        String expected = LessonListCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, lessonListCommand.toString());
    }
}
