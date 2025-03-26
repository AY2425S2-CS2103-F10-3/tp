package seedu.tuitionbook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.tuitionbook.commons.util.ToStringBuilder;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    private final String lessonString;

    /** Help information should be shown to the user. */
    private final boolean showLesson;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.lessonString = "";
        this.showLesson = false;
    }

    /**
     * Constructs a {@code CommandResult} with all the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit,
                         String lessonString, boolean showLesson) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.lessonString = lessonString;
        this.showLesson = showLesson;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and {@code lessonsString},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser, String lessonsString) {
        this(feedbackToUser, false, false, lessonsString, true);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public String getLessonString() {
        return lessonString;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isShowLesson() {
        return showLesson;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && lessonString.equals(otherCommandResult.lessonString)
                && showLesson == otherCommandResult.showLesson;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, lessonString, showLesson);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("lessonString", lessonString)
                .add("showLesson", showLesson)
                .toString();
    }

}
