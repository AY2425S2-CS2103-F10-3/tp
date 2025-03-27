package seedu.tuitionbook.logic.parser;

import static seedu.tuitionbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tuitionbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tuitionbook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tuitionbook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.tuitionbook.logic.commands.LessonListCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the LessonListCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the LessonListCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class LessonListCommandParserTest {

    private LessonListCommandParser parser = new LessonListCommandParser();

    @Test
    public void parse_validArgs_returnsLessonListCommand() {
        assertParseSuccess(parser, "1", new LessonListCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LessonListCommand.MESSAGE_USAGE));
    }
}
