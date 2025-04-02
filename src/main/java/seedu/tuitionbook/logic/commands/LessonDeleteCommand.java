package seedu.tuitionbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tuitionbook.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tuitionbook.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.tuitionbook.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.tuitionbook.commons.core.index.Index;
import seedu.tuitionbook.commons.util.ToStringBuilder;
import seedu.tuitionbook.logic.Messages;
import seedu.tuitionbook.logic.commands.exceptions.CommandException;
import seedu.tuitionbook.model.Model;
import seedu.tuitionbook.model.lesson.Lesson;
import seedu.tuitionbook.model.person.Address;
import seedu.tuitionbook.model.person.Email;
import seedu.tuitionbook.model.person.Name;
import seedu.tuitionbook.model.person.Person;
import seedu.tuitionbook.model.person.Phone;
import seedu.tuitionbook.model.tag.Tag;


/**
 * Deletes a lesson of a person identified using the lesson's name and datetime.
 */
public class LessonDeleteCommand extends Command {
    public static final String COMMAND_WORD = "lesson-delete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the lesson of the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_LESSON + "LESSON\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_LESSON + "Emath;2025-12-01T12:00:00";

    public static final String MESSAGE_DELETE_LESSON_SUCCESS = "Deleted Lessons: \n%1$s";
    public static final String MESSAGE_LESSON_DOES_NOT_EXIST_IN_PERSON = "The lesson(s) provided does not exist "
            + "in the selected person!";
    private final Index targetIndex;
    private final List<Lesson> lessonsToDelete;

    /**
     * @param index of the person in the filtered person list to edit
     * @param lessonsToDelete list of lessons that will be removed from person
     */
    public LessonDeleteCommand(Index index, List<Lesson> lessonsToDelete) {
        requireAllNonNull(index, lessonsToDelete);
        this.targetIndex = index;
        this.lessonsToDelete = lessonsToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());
        if (lessonsToDelete.stream()
                .map(personToEdit.getLessons()::contains)
                .anyMatch(contained -> !contained)) {
            throw new CommandException(MESSAGE_LESSON_DOES_NOT_EXIST_IN_PERSON);
        }

        List<Lesson> updatedLessons = personToEdit.getLessons()
                .stream()
                .filter(lesson -> !lessonsToDelete.contains(lesson))
                .toList();
        Person editedPerson = createEditedPerson(personToEdit, updatedLessons);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_DELETE_LESSON_SUCCESS, lessonsToDelete.stream()
                .map(Lesson::toString)
                .reduce("", (x,y) -> y + "\n" + x )));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     */
    private static Person createEditedPerson(Person personToEdit, List<Lesson> updatedLessons) {
        assert personToEdit != null;
        assert updatedLessons != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Set<Tag> updatedTags = personToEdit.getTags();
        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags, updatedLessons);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonDeleteCommand otherDeleteCommand)) {
            return false;
        }

        return targetIndex.equals(otherDeleteCommand.targetIndex)
                && lessonsToDelete.equals(otherDeleteCommand.lessonsToDelete);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("lessonsToDelete", lessonsToDelete)
                .toString();
    }

}
