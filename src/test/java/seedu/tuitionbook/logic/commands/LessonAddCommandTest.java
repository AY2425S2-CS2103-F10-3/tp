package seedu.tuitionbook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tuitionbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tuitionbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tuitionbook.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.tuitionbook.logic.commands.LessonAddCommand.MESSAGE_ADD_LESSON_SUCCESS;
import static seedu.tuitionbook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tuitionbook.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.tuitionbook.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import seedu.tuitionbook.commons.core.index.Index;
import seedu.tuitionbook.logic.Messages;
import seedu.tuitionbook.model.Model;
import seedu.tuitionbook.model.ModelManager;
import seedu.tuitionbook.model.UserPrefs;
import seedu.tuitionbook.model.lesson.Lesson;
import seedu.tuitionbook.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for LessonAddCommand.
 */
public class LessonAddCommandTest {
    private static final LocalDateTime VALID_LESSON_DATETIME = LocalDateTime.of(2000, 1, 1, 0, 0);
    private static final Lesson EXAMPLE_LESSON = new Lesson("Math", VALID_LESSON_DATETIME);

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addLesson_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        List<Lesson> lessonsToAdd = List.of(EXAMPLE_LESSON);
        LessonAddCommand lessonAddCommand = new LessonAddCommand(INDEX_FIRST_PERSON, lessonsToAdd);

        List<Lesson> updatedLessons = Stream.concat(personToEdit.getLessons().stream(),
                lessonsToAdd.stream())
                .toList();

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getTags(), updatedLessons);

        String expectedMessage = String.format(MESSAGE_ADD_LESSON_SUCCESS, editedPerson.getName(),
                lessonsToAdd.stream()
                .map(Lesson::toString)
                .reduce("", (s, s2) -> s + "\n" + s2));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                editedPerson);

        assertCommandSuccess(lessonAddCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        List<Lesson> lessonsToAdd = List.of(EXAMPLE_LESSON);
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        LessonAddCommand lessonAddCommand = new LessonAddCommand(outOfBoundIndex, lessonsToAdd);

        assertCommandFailure(lessonAddCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicateLesson_failure() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        List<Lesson> lessonsToAdd = List.of(EXAMPLE_LESSON);
        LessonAddCommand lessonAddCommand = new LessonAddCommand(INDEX_FIRST_PERSON, lessonsToAdd);

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getAddress(), personToEdit.getTags(), lessonsToAdd);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()),
                editedPerson);

        assertCommandFailure(lessonAddCommand, expectedModel, LessonAddCommand.MESSAGE_DUPLICATE_LESSON);
    }

    @Test
    public void equals() {
        List<Lesson> lessonsToAdd = List.of(EXAMPLE_LESSON);

        LessonAddCommand addFirstCommand = new LessonAddCommand(INDEX_FIRST_PERSON, lessonsToAdd);

        // same object -> returns true
        assertTrue(addFirstCommand.equals(addFirstCommand));

        // same values -> returns true
        LessonAddCommand addFirstCommandCopy = new LessonAddCommand(INDEX_FIRST_PERSON, lessonsToAdd);
        assertTrue(addFirstCommand.equals(addFirstCommandCopy));

        // null -> returns false
        assertFalse(addFirstCommand.equals(null));

        // different types -> returns false
        assertFalse(addFirstCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(addFirstCommand.equals(new LessonAddCommand(INDEX_SECOND_PERSON, lessonsToAdd)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        List<Lesson> lessonsToAdd = List.of(EXAMPLE_LESSON);
        LessonAddCommand lessonAddCommand = new LessonAddCommand(index, lessonsToAdd);
        String expected = LessonAddCommand.class.getCanonicalName() + "{index=" + index
                + ", lessonsToAdd="
                + lessonsToAdd + "}";
        assertEquals(expected, lessonAddCommand.toString());
    }

}
