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

    private final TextFormatter doubleFormatter;
    private final TextFormatter intFormatter;

    public AmountSpinner() {
        super();
        FXMLLoader.loadFXMLFromRootPackage("amount_spinner.fxml", this, this);
        changeListeners = new ArrayList<>(1);

        Pattern doublePattern = Pattern.compile("\\d*|\\d+\\.\\d*");

        doubleFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return doublePattern.matcher(change.getControlNewText()).matches() ? change : null;
        });

        Pattern intPattern = Pattern.compile("\\d*");

        intFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return intPattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
        valueTextField.setTextFormatter(intFormatter);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
            valueTextField.setTextFormatter(doubleFormatter);
            valueTextField.setText(String.valueOf(amount));
        } else {
            valueTextField.setTextFormatter(intFormatter);
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

        if (valueTextField.getText().length() == 0) {
            if (isAcceptingDoubles) {
                valueTextField.setText("0.0");
            } else {
                valueTextField.setText("0");
            }
        }

        notifyAllChangeListeners(oldValue, getAmount());
        oldValue = getAmount();
    }

}
