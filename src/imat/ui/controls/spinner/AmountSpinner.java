package imat.ui.controls.spinner;

import imat.model.FXMLController;
import imat.interfaces.IShoppingListener;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * Works as a regular Spinner but has its value changing buttons on the left and right of the text field.
 */
public class AmountSpinner extends FXMLController implements IShoppingListener {

    @FXML
    private Button subtractButton;

    @FXML
    private Button addButton;

    @FXML
    private TextField valueTextField;

    private double oldValue;

    private boolean isAcceptingDoubles;

    private final TextFormatter doubleFormatter;
    private final TextFormatter intFormatter;

    private Product product;

    public AmountSpinner(Product product) {
        super();
        this.product = product;

        Pattern doublePattern = Pattern.compile("\\d*|\\d+\\.\\d*");

        switch (product.getUnitSuffix()) {
            case "l":
            case "kg":
                isAcceptingDoubles = true;
                break;
            default:
                isAcceptingDoubles = false;
                break;
        }

        doubleFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return doublePattern.matcher(change.getControlNewText()).matches() ? change : null;
        });

        Pattern intPattern = Pattern.compile("\\d*");

        intFormatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return intPattern.matcher(change.getControlNewText()).matches() ? change : null;
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        valueTextField.setTextFormatter(intFormatter);
        model.addShoppingListener(this);
        setAmount(model.getProductAmount(product));
    }

    public void setAcceptDoubles(boolean acceptDoubles) {
        isAcceptingDoubles = acceptDoubles;
    }

    @FXML
    private void addButtonOnAction(Event event) {
        changeValue(+1);
    }

    @FXML
    private void subtractButtonOnAction(Event event) {
        changeValue(-1);
    }

    private void changeValue(double value) {
        double newValue = oldValue + value;
        if (newValue >= 0) {
            setAmount(newValue);
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
        if(oldValue == amount || model.isThrowingCartInTrash()) return;
        oldValue = amount;
        if (isAcceptingDoubles) {
            valueTextField.setTextFormatter(doubleFormatter);
            valueTextField.setText(String.valueOf(amount));
        } else {
            valueTextField.setTextFormatter(intFormatter);
            valueTextField.setText(String.valueOf((int) amount));
        }
        model.updateShoppingCart(product, amount);
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
        double oldValue2 = getAmount();
        setAmount(Double.valueOf(valueTextField.getText()));
        if (!model.isThrowingCartInTrash()) {
            oldValue = oldValue2;
        }
    }


    @Override
    public void onProductAdded(Product product, Double amount) {

    }

    @Override
    public void onProductRemoved(Product product, Double oldAmount) {

    }


    @Override
    public void onProductUpdate(Product product, Double newAmount) {
        if(product != this.product) return;
        setAmount(newAmount);
    }
}