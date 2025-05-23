package seedu.tuitionbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tuitionbook.logic.commands.LessonAddCommand.MESSAGE_DUPLICATE_LESSON;
import static seedu.tuitionbook.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.tuitionbook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tuitionbook.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.tuitionbook.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tuitionbook.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.tuitionbook.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.tuitionbook.commons.util.ToStringBuilder;
import seedu.tuitionbook.logic.Messages;
import seedu.tuitionbook.logic.commands.exceptions.CommandException;
import seedu.tuitionbook.model.Model;
import seedu.tuitionbook.model.lesson.Lesson;
import seedu.tuitionbook.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]..."
            + "[" + PREFIX_LESSON + "LESSON]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_LESSON + "Elementary Math;2025-03-12T18:00:00";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        List<Lesson> lessonsToCheck = toAdd.getLessons();
        if (model.hasLesson(lessonsToCheck)) {
            throw new CommandException(MESSAGE_DUPLICATE_LESSON);
        }

        Set<String> seenTimings = new HashSet<>();
        for (Lesson lesson : lessonsToCheck) {
            String datetime = lesson.getDatetimeAsString();
            if (!seenTimings.add(datetime)) {
                throw new CommandException(MESSAGE_DUPLICATE_LESSON);
            }
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
