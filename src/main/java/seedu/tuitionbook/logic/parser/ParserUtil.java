package seedu.tuitionbook.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.tuitionbook.commons.core.index.Index;
import seedu.tuitionbook.commons.util.StringUtil;
import seedu.tuitionbook.logic.parser.exceptions.ParseException;
import seedu.tuitionbook.model.lesson.Lesson;
import seedu.tuitionbook.model.person.Address;
import seedu.tuitionbook.model.person.Email;
import seedu.tuitionbook.model.person.Name;
import seedu.tuitionbook.model.person.Phone;
import seedu.tuitionbook.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_MISSING_ARGUMENTS = "Function is missing arguments with separator.";
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final int LESSON_MODULE = 0;
    public static final int LESSON_DATE = 1;
    public static final int LESSON_ARGUMENTS = 2;

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String details} into a {@code Lesson}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code details} is invalid.
     */
    public static Lesson parseLesson(String details) throws ParseException {
        requireNonNull(details);
        String[] detailsArr = details.trim().split(";");
        if (detailsArr.length != LESSON_ARGUMENTS) {
            throw new ParseException(MESSAGE_MISSING_ARGUMENTS);
        }
        if (!Lesson.isValidModuleName(detailsArr[LESSON_MODULE])) {
            throw new ParseException(Lesson.MODULE_MESSAGE_CONSTRAINT);
        }
        if (!Lesson.isValidLessonDate(detailsArr[LESSON_DATE])) {
            throw new ParseException(Lesson.LESSON_DATETIME_MESSAGE_CONSTRAINT);
        }
        return new Lesson(detailsArr[LESSON_MODULE],
                LocalDateTime.from(Lesson.LESSON_DATETIME_FORMAT.parse(detailsArr[LESSON_DATE])));
    }

    /**
     * Parses {@code Collection<String> details} into a {@code ArrayList<Lesson>}.
     */
    public static ArrayList<Lesson> parseLessons(Collection<String> detailsList) throws ParseException {
        requireNonNull(detailsList);
        final ArrayList<Lesson> lessons = new ArrayList<>();
        for (String details : detailsList) {
            lessons.add(parseLesson(details));
        }
        return lessons;
    }
}
