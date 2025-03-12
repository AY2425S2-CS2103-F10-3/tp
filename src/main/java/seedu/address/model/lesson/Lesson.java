package seedu.address.model.lesson;

import java.time.LocalDateTime;

public class Lesson {
    private final String module;
    private final LocalDateTime date;

    public Lesson(String module, LocalDateTime date) {
        this.module = module;
        this.date = date;
    }

    public String toString() {
        return String.format("%s [%s]", this.module, this.date);
    }
}
