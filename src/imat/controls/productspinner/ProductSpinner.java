package imat.controls.productspinner;

import imat.util.FXMLLoader;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Works as a regular Spinner but has its value changing buttons on the left and right of the text field.
 */
public class ProductSpinner extends AnchorPane {

    @FXML
    private Button subtractButton;

    @FXML
    private Button addButton;

    @FXML
    private TextField valueTextField;

    public ProductSpinner() {

        FXMLLoader.loadFXMLFromRootPackage("product_spinner.fxml", this, this);

        // TODO valueTextField should only accept integers greater than, or equal to zero.

        // Forces the text field to only accept numeric input
        valueTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                valueTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    private void addButtonOnClick(Event event) {
        changeValue(1);
    }

    @FXML
    private void subtractButtonOnClick(Event event) {
        changeValue(-1);
    }

    private void changeValue(int value) {
        int newValue = getValue() + value;
        if (newValue >= 0) {
            valueTextField.setText(String.valueOf(newValue));
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

}
