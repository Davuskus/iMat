package imat.controls.product.spinner;

import imat.interfaces.ChangeListener;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Works as a regular Spinner but has its value changing buttons on the left and right of the text field.
 */
public class ProductCountSpinner implements Initializable {

    @FXML
    private Button subtractButton;

    @FXML
    private Button addButton;

    @FXML
    private TextField valueTextField;

    private final List<ChangeListener<Integer>> changeListeners;

    public ProductCountSpinner() {
        super();
        changeListeners = new ArrayList<>(1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Forces the text field to only accept positive integers as input
        valueTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                valueTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    private void addButtonOnAction(Event event) {
        changeValue(1);
    }

    @FXML
    private void subtractButtonOnAction(Event event) {
        changeValue(-1);
    }

    private void changeValue(int value) {
        int oldValue = getValue();
        int newValue = oldValue + value;
        if (newValue >= 0) {
            valueTextField.setText(String.valueOf(newValue));
            notifyAllChangeListeners(oldValue, newValue);
        }
    }

    /**
     * Returns the value currently displayed in the text field.
     *
     * @return The text field's text as an integer.
     */
    public int getValue() {
        return Integer.valueOf(valueTextField.getText());
    }

    /**
     * Adds a a listener that will get notified when the spinner's value changes.
     *
     * @param listener The listener to add.
     */
    public void addChangeListener(ChangeListener<Integer> listener) {
        changeListeners.add(listener);
    }

    private void notifyAllChangeListeners(int oldValue, int newValue) {
        for (ChangeListener<Integer> listener : changeListeners) {
            listener.onChange(oldValue, newValue);
        }
    }

}
