package imat.controls.spinner;

import imat.interfaces.ChangeListener;
import imat.utils.FXMLLoader;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * Works as a regular Spinner but has its value changing buttons on the left and right of the text field.
 */
public class AmountSpinner extends AnchorPane implements Initializable {

    @FXML
    private Button subtractButton;

    @FXML
    private Button addButton;

    @FXML
    private TextField valueTextField;

    private double oldValue;

    private final List<ChangeListener<Double>> changeListeners;

    private boolean isAcceptingDoubles;

    public AmountSpinner() {
        super();
        FXMLLoader.loadFXMLFromRootPackage("amount_spinner.fxml", this, this);
        changeListeners = new ArrayList<>(1);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Pattern pattern = Pattern.compile("\\d*|\\d+\\.\\d*");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        valueTextField.setTextFormatter(formatter);
    }

    public void setAcceptDoubles(boolean acceptDoubles) {
        isAcceptingDoubles = acceptDoubles;
    }

    @FXML
    private void addButtonOnAction(Event event) {
        changeValue(1);
    }

    @FXML
    private void subtractButtonOnAction(Event event) {
        changeValue(-1);
    }

    private void changeValue(double value) {
        oldValue = getAmount();
        double newValue = oldValue + value;
        if (newValue >= 0) {
            setAmount(newValue);
            notifyAllChangeListeners(oldValue, newValue);
        }
    }

    /**
     * Returns the value currently displayed in the text field.
     *
     * @return The text field's text as an integer.
     */
    public double getAmount() {
        return Double.valueOf(valueTextField.getText());
    }

    /**
     * Sets the amount, removes any decimals if not accepting doubles.
     *
     * @param amount The amount.
     */
    public void setAmount(double amount) {
        if (isAcceptingDoubles) {
            valueTextField.setText(String.valueOf(amount));
        } else {
            valueTextField.setText(String.valueOf((int) amount));
        }
    }

    /**
     * Adds a a listener that will get notified when the spinner's value changes.
     *
     * @param listener The listener to add.
     */
    public void addChangeListener(ChangeListener<Double> listener) {
        changeListeners.add(listener);
    }

    private void notifyAllChangeListeners(double oldValue, double newValue) {
        for (ChangeListener<Double> listener : changeListeners) {
            listener.onChange(oldValue, newValue);
        }
    }

    @FXML
    private void onEnterPressed(Event event) {
        notifyAllChangeListeners(oldValue, getAmount());
        oldValue = getAmount();
    }

}
