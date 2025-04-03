package seedu.tuitionbook.logic.commands;


import static java.util.Objects.requireNonNull;
import static seedu.tuitionbook.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.tuitionbook.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

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
 * Adds lessons to an existing person in TuitionBook.
 */
public class LessonAddCommand extends Command {

    public static final String COMMAND_WORD = "lesson-add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds new lesson for the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_LESSON + "LESSON]...\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_LESSON + "Math;2025-12-31T12:00:00";

    public static final String MESSAGE_ADD_LESSON_SUCCESS = "Added Lesson For %1$s: \n%2$s";
    public static final String MESSAGE_DUPLICATE_LESSON =
            "Error: There is already a student with the same lesson timeslot";

    private final Index index;
    private final List<Lesson> lessonsToAdd;

    /**
     * @param index of the person in the filtered person list to add lessons to
     * @param lessonsToAdd lessons that the user wants to add to the person
     */
    public LessonAddCommand(Index index, List<Lesson> lessonsToAdd) {
        requireNonNull(index);
        requireNonNull(lessonsToAdd);
        assert !lessonsToAdd.isEmpty();

        this.index = index;
        this.lessonsToAdd = lessonsToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }


        Person personToEdit = lastShownList.get(index.getZeroBased());
        List<Lesson> updatedLessons = Stream.concat(personToEdit.getLessons().stream(),
                lessonsToAdd.stream())
                .toList();
        Person editedPerson = createEditedPerson(personToEdit, updatedLessons);

        if (model.hasLesson(lessonsToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_LESSON_SUCCESS, editedPerson.getName(),
                lessonsToAdd.stream()
                .map(Lesson::toString)
                .reduce("", (s, s2) -> s + "\n" + s2)));
        }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     */
    private static Person createEditedPerson(Person personToEdit, List<Lesson> updatedLessons) {
        assert personToEdit != null;

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
        if (!(other instanceof LessonAddCommand)) {
            return false;
        }

        LessonAddCommand otherLessonAddCommand = (LessonAddCommand) other;
        return index.equals(otherLessonAddCommand.index)
                && lessonsToAdd.equals(otherLessonAddCommand.lessonsToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("lessonsToAdd", lessonsToAdd)
                .toString();
    }
}
