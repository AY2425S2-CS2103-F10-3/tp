package seedu.tuitionbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tuitionbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tuitionbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tuitionbook.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.tuitionbook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tuitionbook.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.tuitionbook.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.tuitionbook.commons.core.index.Index;
import seedu.tuitionbook.logic.Messages;
import seedu.tuitionbook.model.Model;
import seedu.tuitionbook.model.ModelManager;
import seedu.tuitionbook.model.UserPrefs;
import seedu.tuitionbook.model.lesson.Lesson;
import seedu.tuitionbook.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code LessonDeleteCommand}.
 */
public class LessonDeleteCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allLessonsToDeleteUnfilteredList_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        List<Lesson> lessonsToDelete = personToEdit.getLessons();
        LessonDeleteCommand lessonDeleteCommand = new LessonDeleteCommand(INDEX_FIRST_PERSON, lessonsToDelete);

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getTags(), List.of());

        String expectedMessage = String.format(LessonDeleteCommand.MESSAGE_DELETE_LESSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(
                expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                editedPerson);

        assertCommandSuccess(lessonDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_firstLessonsToDeleteUnfilteredList_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        List<Lesson> lessonsToDelete = List.of(personToEdit.getLessons().get(0));
        LessonDeleteCommand lessonDeleteCommand = new LessonDeleteCommand(INDEX_FIRST_PERSON, lessonsToDelete);

        List<Lesson> updatedLessons = personToEdit.getLessons()
                .stream()
                .dropWhile(lessonsToDelete::contains)
                .toList();
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getTags(), updatedLessons);

        String expectedMessage = String.format(LessonDeleteCommand.MESSAGE_DELETE_LESSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(
                expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                editedPerson);

        assertCommandSuccess(lessonDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noLessonsToDeleteUnfilteredList_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        List<Lesson> lessonsToDelete = List.of();
        LessonDeleteCommand lessonDeleteCommand = new LessonDeleteCommand(INDEX_FIRST_PERSON, lessonsToDelete);

        List<Lesson> updatedLessons = personToEdit.getLessons();
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getTags(), updatedLessons);

        String expectedMessage = String.format(LessonDeleteCommand.MESSAGE_DELETE_LESSON_SUCCESS,
                Messages.format(editedPerson));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(
                expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                editedPerson);

        assertCommandSuccess(lessonDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        List<Lesson> lessonsToDelete = List.of(
                new Lesson("Non Existent Lesson", LocalDateTime.of(2028, 12, 12, 12, 0, 0)));
        LessonDeleteCommand lessonDeleteCommand = new LessonDeleteCommand(INDEX_FIRST_PERSON, lessonsToDelete);

        List<Lesson> updatedLessons = personToEdit.getLessons();
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getTags(), updatedLessons);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(
                expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                editedPerson);

        assertCommandFailure(lessonDeleteCommand, model, LessonDeleteCommand.MESSAGE_LESSON_DOES_NOT_EXIST_IN_PERSON);
    }

    @Test
    public void execute_nonExistentLessonsFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        List<Lesson> lessons = List.of();

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        LessonDeleteCommand lessonDeleteCommand = new LessonDeleteCommand(outOfBoundIndex, lessons);

        assertCommandFailure(lessonDeleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        List<Lesson> emptyLessonsToDelete = List.of();

        LessonDeleteCommand deleteFirstCommand = new LessonDeleteCommand(INDEX_FIRST_PERSON, emptyLessonsToDelete);
        LessonDeleteCommand deleteSecondCommand = new LessonDeleteCommand(INDEX_SECOND_PERSON, emptyLessonsToDelete);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        LessonDeleteCommand deleteFirstCommandCopy = new LessonDeleteCommand(INDEX_FIRST_PERSON, emptyLessonsToDelete);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        List<Lesson> lessons = List.of();
        LessonDeleteCommand deleteCommand = new LessonDeleteCommand(targetIndex, lessons);
        String expected = LessonDeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex
                + ", lessonsToDelete=" + lessons + "}";
        assertEquals(expected, deleteCommand.toString());
    }
}
