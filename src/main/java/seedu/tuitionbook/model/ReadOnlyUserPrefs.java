package seedu.tuitionbook.model;

import java.nio.file.Path;

import seedu.tuitionbook.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getAddressBookFilePath();

}
