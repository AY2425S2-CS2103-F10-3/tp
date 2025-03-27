package seedu.tuitionbook.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import seedu.tuitionbook.commons.core.LogsCenter;

/**
 * Controller for a Lesson page
 */
public class LessonWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(LessonWindow.class);
    private static final String FXML = "LessonWindow.fxml";

    @FXML
    private Label lessonLabel;

    /**
     * Creates a new LessonWindow.
     *
     * @param root Stage to use as the root of the LessonWindow.
     */
    public LessonWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new LessonWindow.
     */
    public LessonWindow() {
        this(new Stage());
    }

    /**
     * Shows the lesson window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show(String lessonString) {
        logger.fine("Showing list of lessons for the specified user.");
        lessonLabel.setText(lessonString);
        getRoot().sizeToScene();
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the lesson window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the lesson window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the lesson window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
