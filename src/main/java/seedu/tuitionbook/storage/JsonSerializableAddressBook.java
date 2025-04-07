package seedu.tuitionbook.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.tuitionbook.commons.exceptions.IllegalValueException;
import seedu.tuitionbook.model.AddressBook;
import seedu.tuitionbook.model.ReadOnlyAddressBook;
import seedu.tuitionbook.model.lesson.Lesson;
import seedu.tuitionbook.model.person.Person;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_LESSON = "Persons list contains duplicate lesson(s) "
            + "and/or lessons with clashing times";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        ArrayList<String> existingLessons = new ArrayList<>();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }

            List<Lesson> personLessonsToCheck = person.getLessons();
            for (Lesson lesson : personLessonsToCheck) {
                String exisitingLessonTime = lesson.getDatetimeAsString();
                if (existingLessons.contains(exisitingLessonTime)) {
                    throw new IllegalValueException(MESSAGE_DUPLICATE_LESSON);
                }
                existingLessons.add(exisitingLessonTime);
            }

            addressBook.addPerson(person);
        }
        return addressBook;
    }

}
