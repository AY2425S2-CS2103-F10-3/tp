package seedu.tuitionbook.logic.parser;

import static seedu.tuitionbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.tuitionbook.commons.core.index.Index;
import seedu.tuitionbook.logic.commands.LessonListCommand;
import seedu.tuitionbook.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LessonListCommand object
 */
public class LessonListCommandParser implements Parser<LessonListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LessonListCommand
     * and returns a LessonListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LessonListCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new LessonListCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LessonListCommand.MESSAGE_USAGE), pe);
        }
    }
}
