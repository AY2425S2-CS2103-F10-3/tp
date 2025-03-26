package seedu.tuitionbook.logic.commands;

import seedu.tuitionbook.commons.core.index.Index;
import seedu.tuitionbook.model.Model;
import seedu.tuitionbook.model.person.Person;

/**
 * Format full help instructions for every command for display.
 */
public class LessonListCommand extends Command {

    public static final String COMMAND_WORD = "lesson-list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all lessons of a specified person.\n"
            + "Example: " + COMMAND_WORD + " [INDEX]";

    public static final String LIST_LESSONS_MESSAGE = "Listing all lessons for ";

    private static final String NO_LESSONS_TO_DISPLAY = "No lessons found";

    private final Index targetIndex;

    public LessonListCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) {
        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());

        return new CommandResult(LIST_LESSONS_MESSAGE + person.getName() + ":\n"
                + person.getLessons().stream()
                .map(lesson -> lesson.toString())
                .reduce((a, b) -> a + "\n" + b)
                .orElse(NO_LESSONS_TO_DISPLAY));
    }
}
