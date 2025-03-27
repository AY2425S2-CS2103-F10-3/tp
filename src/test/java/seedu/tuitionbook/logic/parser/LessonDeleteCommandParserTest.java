package seedu.tuitionbook.logic.parser;

import static seedu.tuitionbook.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tuitionbook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tuitionbook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tuitionbook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.tuitionbook.logic.commands.LessonDeleteCommand;
import seedu.tuitionbook.model.lesson.Lesson;

public class LessonDeleteCommandParserTest {
    private LessonDeleteCommandParser parser = new LessonDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsLessonDeleteCommand() {
        String oneLessonDeleteCommand = "1 l/Elementary Mathematics;2025-12-12T12:00:00";
        String twoLessonDeleteCommand = "1 l/Elementary Mathematics;2025-12-12T12:00:00 l/Biology;2025-05-03T17:30:00";
        Lesson lessonOne = new Lesson("Elementary Mathematics",
                LocalDateTime.from(Lesson.LESSON_DATETIME_FORMAT.parse("2025-12-12T12:00:00")));
        Lesson lessonTwo = new Lesson("Biology",
                LocalDateTime.from(Lesson.LESSON_DATETIME_FORMAT.parse("2025-05-03T17:30:00")));

        assertParseSuccess(parser, oneLessonDeleteCommand,
                new LessonDeleteCommand(INDEX_FIRST_PERSON, List.of(lessonOne)));

        assertParseSuccess(parser, twoLessonDeleteCommand,
                new LessonDeleteCommand(INDEX_FIRST_PERSON, List.of(lessonOne, lessonTwo)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "1 invalid", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, LessonDeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "l/Elementary Mathematics;2025-12-12T12:00:00",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LessonDeleteCommand.MESSAGE_USAGE));
    }
}
