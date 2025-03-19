package seedu.tuitionbook.logic.commands;

import static seedu.tuitionbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tuitionbook.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.tuitionbook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tuitionbook.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.tuitionbook.model.Model;
import seedu.tuitionbook.model.ModelManager;
import seedu.tuitionbook.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
