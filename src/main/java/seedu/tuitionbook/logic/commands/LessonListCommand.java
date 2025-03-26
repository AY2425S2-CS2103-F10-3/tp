package seedu.tuitionbook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.tuitionbook.commons.core.index.Index;
import seedu.tuitionbook.logic.Messages;
import seedu.tuitionbook.logic.commands.exceptions.CommandException;
import seedu.tuitionbook.model.Model;
import seedu.tuitionbook.model.person.Person;

/**
 * Format full help instructions for every command for display.
 */
public class LessonListCommand extends Command {

    public static final String COMMAND_WORD = "lesson-list";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all lessons of a specified person.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String LIST_LESSONS_MESSAGE = "Listing all lessons for ";

    private static final String NO_LESSONS_TO_DISPLAY = "No lessons found";

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
                person.getName() + "'s Lessons:\n"
                + person.getLessons().stream()
                .map(lesson -> lesson.toString())
                .reduce((a, b) -> a + "\n" + b)
                .orElse(NO_LESSONS_TO_DISPLAY));
    }
}
