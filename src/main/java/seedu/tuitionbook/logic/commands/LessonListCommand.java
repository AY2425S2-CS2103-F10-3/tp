package seedu.tuitionbook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.tuitionbook.commons.core.index.Index;
import seedu.tuitionbook.commons.util.ToStringBuilder;
import seedu.tuitionbook.logic.Messages;
import seedu.tuitionbook.logic.commands.exceptions.CommandException;
import seedu.tuitionbook.model.Model;
import seedu.tuitionbook.model.person.Person;

/**
 * Lists all lessons for the Person specified by the given index.
 */
public class LessonListCommand extends Command {

    public static final String COMMAND_WORD = "lesson-list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all lessons of a specified person.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String LIST_LESSONS_MESSAGE = "Listing all lessons for ";

    public static final String NO_LESSONS_TO_DISPLAY = "No lessons found";

    private final Index targetIndex;

    public LessonListCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        return new CommandResult(LIST_LESSONS_MESSAGE + person.getName(),
                person.getName() + "'s Lessons:\n\n" + person.getLessons().stream()
                        .map(lesson -> lesson.toString())
                        .reduce((a, b) -> a + "\n" + b)
                        .orElse(NO_LESSONS_TO_DISPLAY));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonListCommand)) {
            return false;
        }

        LessonListCommand otherLessonListCommand = (LessonListCommand) other;
        return targetIndex.equals(otherLessonListCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
