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

    /**
     * Initializes the spinner.
     */
    public ProductSpinner() {
        FXMLLoader.loadFXMLFromRootPackage("product_spinner.fxml", this, this);

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
