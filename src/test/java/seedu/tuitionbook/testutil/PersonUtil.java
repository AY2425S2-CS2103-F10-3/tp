package seedu.tuitionbook.testutil;

import static seedu.tuitionbook.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.tuitionbook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tuitionbook.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.tuitionbook.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tuitionbook.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.tuitionbook.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.tuitionbook.model.lesson.Lesson.LESSON_DATETIME_FORMAT;
import static seedu.tuitionbook.model.lesson.Lesson.DISPLAY_DATETIME_FORMAT;

import java.time.LocalDateTime;
import java.util.Set;

import seedu.tuitionbook.logic.commands.AddCommand;
import seedu.tuitionbook.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.tuitionbook.model.person.Person;
import seedu.tuitionbook.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Converts the displayed lesson format to the input command format.
     */
    public static String convertLessonFormat(String lesson) {
        String[] parts = lesson.split(" \\| ");
        String moduleName = parts[0].trim();
        String dateTimeStr = parts[1].trim();
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, DISPLAY_DATETIME_FORMAT);
        return moduleName + ";" + dateTime.format(LESSON_DATETIME_FORMAT) + " ";
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        person.getLessons().stream().forEach(
            s -> sb.append(PREFIX_LESSON + convertLessonFormat(s.toString()))
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
