package seedu.tuitionbook.storage;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.tuitionbook.commons.exceptions.IllegalValueException;
import seedu.tuitionbook.model.lesson.Lesson;
import seedu.tuitionbook.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedLesson {
    private final String module;
    private final String dateTime;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedLesson(
            @JsonProperty("module") String module,
            @JsonProperty("date") String dateTime) {
        this.module = module;
        this.dateTime = dateTime;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        this.module = source.getModule();
        this.dateTime = source.getDatetimeAsString();
    }

    @JsonProperty("module")
    public String getModule() {
        return this.module;
    }

    @JsonProperty("date")
    public String getDateTime() {
        return this.dateTime;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Lesson toModelType() throws IllegalValueException {
        if (!Lesson.isValidModuleName(this.module)) {
            throw new IllegalValueException(Lesson.MODULE_MESSAGE_CONSTRAINT);
        }

        if (!Lesson.isValidLessonDate(this.dateTime)) {
            throw new IllegalValueException(Lesson.LESSON_DATETIME_MESSAGE_CONSTRAINT);
        }

        LocalDateTime lessonDatetime = LocalDateTime.from(Lesson.LESSON_DATETIME_FORMAT.parse(this.dateTime));
        return new Lesson(this.module, lessonDatetime);
    }

}
