package seedu.tuitionbook.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.tuitionbook.model.AddressBook;
import seedu.tuitionbook.model.ReadOnlyAddressBook;
import seedu.tuitionbook.model.lesson.Lesson;
import seedu.tuitionbook.model.person.Address;
import seedu.tuitionbook.model.person.Email;
import seedu.tuitionbook.model.person.Name;
import seedu.tuitionbook.model.person.Person;
import seedu.tuitionbook.model.person.Phone;
import seedu.tuitionbook.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final LocalDateTime SAMPLE_DATE_TIME_1 = LocalDateTime.of(2025, 5, 12, 12, 0);
    public static final LocalDateTime SAMPLE_DATE_TIME_2 = LocalDateTime.of(2025, 5, 12, 13, 0);
    public static final LocalDateTime SAMPLE_DATE_TIME_3 = LocalDateTime.of(2025, 5, 24, 22, 0);
    public static final LocalDateTime SAMPLE_DATE_TIME_4 = LocalDateTime.of(2025, 5, 26, 10, 0);
    public static final LocalDateTime SAMPLE_DATE_TIME_5 = LocalDateTime.of(2025, 5, 28, 8, 0);
    public static final LocalDateTime SAMPLE_DATE_TIME_6 = LocalDateTime.of(2025, 5, 30, 12, 0);
    public static final LocalDateTime SAMPLE_DATE_TIME_7 = LocalDateTime.of(2025, 5, 30, 12, 30);
    public static final LocalDateTime SAMPLE_DATE_TIME_8 = LocalDateTime.of(2025, 5, 30, 13, 30);

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), getTagSet("student"),
                    List.of(new Lesson("Chemistry", SAMPLE_DATE_TIME_1))),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), getTagSet("parent"),
                List.of(new Lesson("Elementary Mathematics", SAMPLE_DATE_TIME_2),
                        new Lesson("Advanced Mathematics", SAMPLE_DATE_TIME_3))),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("Parent"), List.of(new Lesson("Biology", SAMPLE_DATE_TIME_4),
                        new Lesson("Physics", SAMPLE_DATE_TIME_5))),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), getTagSet("Student"),
                List.of(new Lesson("Chemistry", SAMPLE_DATE_TIME_6))),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), getTagSet("Student", "Sec4"),
                List.of(new Lesson("Chemistry", SAMPLE_DATE_TIME_7))),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("parent"), List.of(new Lesson("Chemistry", SAMPLE_DATE_TIME_8))),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
