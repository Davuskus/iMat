package imat.ui.views.modal.payment;

import imat.enums.NavigationTarget;
import imat.interfaces.INavigationListener;
import imat.interfaces.IShoppingListener;
import imat.model.FXMLController;
import imat.utils.MathUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PaymentController extends FXMLController implements Initializable, INavigationListener, IShoppingListener {

    @FXML
    Button clientInfoDoneButton;

    @FXML
    Button creditCardDoneButton;

    @FXML
    Button creditCardBackButton;

    @FXML
    Button changeCreditCardInfoButton;

    @FXML
    Button changeClientInfoButton;

    @FXML
    AnchorPane conclusionPane;

    @FXML
    AnchorPane customerInfoPane;

    @FXML
    AnchorPane creditCardInfoPane;

    @FXML
    VBox firstSidebarVBox;

    @FXML
    VBox secondSidebarVBox;

    @FXML
    VBox thirdSidebarVBox;

    @FXML
    TextField firstNameField;

    @FXML
    TextField lastNameField;

    @FXML
    TextField addressField;

    @FXML
    TextField postcodeField;

    @FXML
    TextField phoneNumberField;

    //@FXML
    // TextField creditCardNumberField;

    @FXML
    TextField validMonthField;

    @FXML
    TextField validYearField;

    @FXML
    TextField cvcField;

    @FXML
    TextArea clientSummeryArea;
    @FXML
    TextArea creditCardSummeryArea;

    @FXML
    CheckBox saveUserInfoCheckBox;

    @FXML
    private Label clientErrorLable;

    @FXML
    private AnchorPane confirmationPane;

    @FXML
    private Label creditCardErrorLable;

    @FXML
    private SplitPane splitPane;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField c1;
    @FXML
    private TextField c2;
    @FXML
    private TextField c3;
    @FXML
    private TextField c4;

    @FXML
    private Label cardError;

    @FXML
    private Label cvcError;

    @FXML
    private Button PayButton;

    @FXML
    private Button summaryBackButton;

    @FXML
    private Label sumLabel;

    @FXML
    protected StackPane stackPane;

    @FXML
    private GridPane gridDate1;

    private DatePicker datePicker;

    @FXML
    private Label OrderErrorLable;

    @FXML
    private Button orderDateBackButton;

    @FXML
    private Button orderdateNextButton;

    @FXML
    private TextField hourTextField;

    @FXML
    private TextField minuteTextField;

    @FXML
    private AnchorPane orderDatePane;

    @FXML
    private VBox orderDateSidePane;

    @FXML
    private Label summaryDateLabel;

    @FXML
    private Label summaryTimelabel;

    private final IMatDataHandler iMatDataHandler;

    private final Customer customer;

    private final CreditCard creditCard;

    private boolean resize;

    public PaymentController() {
        iMatDataHandler = IMatDataHandler.getInstance();
        customer = iMatDataHandler.getCustomer();
        creditCard = iMatDataHandler.getCreditCard();

        datePicker = new DatePicker(LocalDate.now());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillUserData();
        updateTextArea();
        setSize(splitPane);
        resize = true;

        DatePickerSkin datePickerSkin = new DatePickerSkin(datePicker);
        Node node = datePickerSkin.getPopupContent();
        gridDate1.add(node, 2, 0);

        confirmationPane.setDisable(true);
        splitPane.setDisable(true);

        model.addNavigationListener(this);

        hourTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    hourTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        minuteTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    minuteTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        c1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    c1.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        c2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    c2.setText(newValue.replaceAll("[^\\d.-]", ""));
                }
            }
        });

        c3.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    c3.setText(newValue.replaceAll("[^\\d.-]", ""));
                }
            }
        });

        c4.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    c4.setText(newValue.replaceAll("[^\\d.-]", ""));
                }
            }
        });

        validMonthField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    validMonthField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        validYearField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    validYearField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        cvcField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    cvcField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        postcodeField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    postcodeField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        phoneNumberField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    phoneNumberField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        firstNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            ClientInfoDone();
        });

        lastNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            ClientInfoDone();
        });

        addressField.textProperty().addListener((observable, oldValue, newValue) -> {
            ClientInfoDone();
        });

        postcodeField.textProperty().addListener((observable, oldValue, newValue) -> {
            ClientInfoDone();
            if (postcodeField.getText().trim().chars().count() > 5) {
                String t = postcodeField.getText().trim().substring(0, 5);
                postcodeField.clear();
                postcodeField.appendText(t);
            }
        });

        phoneNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            ClientInfoDone();
            if (phoneNumberField.getText().trim().chars().count() > 10) {
                String t = phoneNumberField.getText().trim().substring(0, 10);
                phoneNumberField.clear();
                phoneNumberField.appendText(t);
            }
        });

        validMonthField.textProperty().addListener((observable, oldValue, newValue) -> {
            CreditCardInfoDone();
            if (validMonthField.getText().trim().chars().count() > 2) {
                String t = validMonthField.getText().trim().substring(0, 2);
                validMonthField.clear();
                validMonthField.appendText(t);
            }
        });

        validYearField.textProperty().addListener((observable, oldValue, newValue) -> {
            CreditCardInfoDone();
            if (validYearField.getText().trim().chars().count() > 4) {
                String t = validYearField.getText().trim().substring(0, 4);
                validYearField.clear();
                validYearField.appendText(t);
            }
        });

        cvcField.textProperty().addListener((observable, oldValue, newValue) -> {
            CreditCardInfoDone();
            if (cvcField.getText().trim().chars().count() > 3) {
                String t = cvcField.getText().trim().substring(0, 3);
                cvcField.clear();
                cvcField.appendText(t);
            }
        });

        c1.textProperty().addListener((observable, oldValue, newValue) -> {
            CreditCardInfoDone();
            if (c1.getText().chars().count() == 4) {
                c2.requestFocus();
            }
            if (c1.getText().trim().chars().count() > 4) {
                String t = c1.getText().trim().substring(0, 4);
                c1.clear();
                c1.appendText(t);
            }
        });

        c2.textProperty().addListener((observable, oldValue, newValue) -> {
            CreditCardInfoDone();
            if (c2.getText().chars().count() == 4) {
                c3.requestFocus();
            }
            if (c2.getText().trim().chars().count() > 4) {
                String t = c2.getText().trim().substring(0, 4);
                c2.clear();
                c2.appendText(t);
            }
        });

        c3.textProperty().addListener((observable, oldValue, newValue) -> {
            CreditCardInfoDone();
            if (c3.getText().chars().count() == 4) {
                c4.requestFocus();
            }
            if (c3.getText().trim().chars().count() > 4) {
                String t = c3.getText().trim().substring(0, 4);
                c3.clear();
                c3.appendText(t);
            }
        });

        c4.textProperty().addListener((observable, oldValue, newValue) -> {
            CreditCardInfoDone();

            if (c4.getText().trim().chars().count() > 4) {
                String t = c4.getText().trim().substring(0, 4);
                c4.clear();
                c4.appendText(t);
            }
        });

        hourTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (hourTextField.getText().trim().chars().count() == 2) {
                minuteTextField.requestFocus();
            }

            if (hourTextField.getText().trim().chars().count() > 2) {
                String t = hourTextField.getText().trim().substring(0, 2);
                hourTextField.clear();
                hourTextField.appendText(t);
            }
            orderDateDone();
        });

        minuteTextField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (minuteTextField.getText().trim().chars().count() > 2) {
                String t = minuteTextField.getText().trim().substring(0, 2);
                minuteTextField.clear();
                minuteTextField.appendText(t);
            }
            orderDateDone();
        });

    }

    private void fillUserData() {
        firstNameField.appendText(customer.getFirstName());
        lastNameField.appendText(customer.getLastName());
        addressField.appendText(customer.getAddress());
        postcodeField.appendText(customer.getPostCode());
        phoneNumberField.appendText(customer.getPhoneNumber());

        fillCreditCard();

        validMonthField.appendText(removeDefult(creditCard.getValidMonth()));
        validYearField.appendText(removeDefult(creditCard.getValidYear()));
        cvcField.appendText(removeDefult(creditCard.getVerificationCode()));
    }

    private String removeDefult(int str) {
        if (str == 0) {
            return "";
        }
        return "" + str;
    }


    private void fillCreditCard() {
        String c = creditCard.getCardNumber();
        for (int i = 0; i < c.length(); i++) {
            if (i < 4) {
                c1.appendText("" + c.charAt(i));
            } else if (i > 3 && i < 8) {
                c2.appendText("" + c.charAt(i));
            } else if (i > 7 && i < 12) {
                c3.appendText("" + c.charAt(i));
            } else {
                c4.appendText("" + c.charAt(i));
            }
        }
    }

    private void saveUserInfo() {
        if (saveUserInfoCheckBox.isSelected()) {
            customer.setFirstName(firstNameField.getText());
            customer.setLastName(lastNameField.getText());
            customer.setAddress(addressField.getText());
            customer.setPostCode(postcodeField.getText());
            customer.setPhoneNumber(phoneNumberField.getText());

            creditCard.setCardNumber(c1.getText() + c2.getText() + c3.getText() + c4.getText());
            creditCard.setValidMonth(Integer.parseInt(validMonthField.getText()));
            creditCard.setValidYear(Integer.parseInt(validYearField.getText()));
            creditCard.setVerificationCode(Integer.parseInt(cvcField.getText()));
        } else {
            customer.setFirstName("");
            customer.setLastName("");
            customer.setAddress("");
            customer.setPostCode("");
            customer.setPhoneNumber("");

            creditCard.setCardNumber("");
            creditCard.setValidMonth(0);
            creditCard.setValidYear(0);
            creditCard.setVerificationCode(0);

            firstNameField.clear();
            lastNameField.clear();
            addressField.clear();
            postcodeField.clear();
            phoneNumberField.clear();
            c1.clear();
            c2.clear();
            c3.clear();
            c4.clear();
            validMonthField.clear();
            validYearField.clear();
            cvcField.clear();
            saveUserInfoCheckBox.setSelected(true);
        }
    }

    @FXML
    private void payAndOrder() {
        saveUserInfo();

        resize = false;
        setSize(confirmationPane);
        resize = true;

        splitPane.setDisable(true);
        confirmationPane.setDisable(false);

        model.placeOrder();
        clientViewToFront();

        confirmationPane.toFront();
    }

    private void setSize(AnchorPane anchorPane) {
        rootPane.setMaxSize(anchorPane.getPrefWidth(), anchorPane.getPrefHeight());
        rootPane.setMinSize(anchorPane.getPrefWidth(), anchorPane.getPrefHeight());
        rootPane.setPrefSize(anchorPane.getPrefWidth(), anchorPane.getPrefHeight());
        resizeParent();
    }

    private void setSize(SplitPane anchorPane) {
        rootPane.setMaxSize(anchorPane.getPrefWidth(), anchorPane.getPrefHeight());
        rootPane.setMinSize(anchorPane.getPrefWidth(), anchorPane.getPrefHeight());
        rootPane.setPrefSize(anchorPane.getPrefWidth(), anchorPane.getPrefHeight());
        // resizeParent();
    }

    private void updateTextArea() {
        updateCreditCardTextArea();
        updateClientTextArea();
        updateOrderText();
    }

    private void updateOrderText() {
        sumLabel.setText(MathUtils.asPriceTag(model.getCartPrice() + 35));
        summaryDateLabel.setText("" + datePicker.getValue());
        summaryTimelabel.setText("" + hourTextField.getText() + ":" + minuteTextField.getText());
    }

    private void updateCreditCardTextArea() {
        creditCardSummeryArea.clear();
        creditCardSummeryArea.appendText(creditCardToString());
    }

    private String creditCardToString() {
        StringBuilder strB = new StringBuilder();
        // strB.append(creditCardNumberField.getText() + "\n");
        strB.append(c1.getText() + "-" + c2.getText() + "-" + c3.getText() + "-" + c4.getText() + "\n");

        strB.append(validMonthField.getText() + " / ");
        strB.append(validYearField.getText() + "\n");
        strB.append(cvcField.getText());
        return strB.toString();
    }

    private void updateClientTextArea() {
        clientSummeryArea.clear();
        clientSummeryArea.appendText(clientToString());
    }

    private String clientToString() {

        StringBuilder strB = new StringBuilder();
        strB.append(firstNameField.getText() + " ");
        strB.append(lastNameField.getText() + "\n");
        strB.append(addressField.getText() + "\n");
        strB.append(postcodeField.getText() + "\n");
        strB.append(phoneNumberField.getText());
        return strB.toString();
    }

    @FXML
    private void orderDateToFront() {
        switchView(orderDatePane);
        orderDateSidePane.toFront();
    }

    @FXML
    private void creditCardViewToFront() {
        switchView(creditCardInfoPane);
        secondSidebarVBox.toFront();
    }

    @FXML
    private void summaryViewToFront() {
        updateTextArea();
        switchView(conclusionPane);
        thirdSidebarVBox.toFront();
    }

    @FXML
    private void clientViewToFront() {
        firstSidebarVBox.toFront();
        switchView(customerInfoPane);
    }


    @FXML
    private void moveBack() {
        splitPane.setDisable(true);
        model.navigate(NavigationTarget.CHECKOUT);
    }

    private boolean isTextFieldFieldIn(TextField t) {
        return !(t.getText().trim().length() == 0);
    }

    private boolean isFieldIn(TextField t, boolean displayError) {

        if (!isTextFieldFieldIn(t)) {
            if (displayError) {
                displayError(t);
            }
            return false;
        }

        if (t.getStyleClass().contains("error")) {
            t.getStyleClass().removeAll("error");
        }
        return true;
    }

    private boolean clientInfoFildIn(boolean displayError) {
        boolean fildIn = true;
        fildIn = isFieldIn(firstNameField, displayError) && fildIn;

        fildIn = isFieldIn(lastNameField, displayError) && fildIn;

        fildIn = isFieldIn(addressField, displayError) && fildIn;

        fildIn = isFieldIn(postcodeField, displayError) && fildIn;

        fildIn = isFieldIn(phoneNumberField, displayError) && fildIn;

        return fildIn;
    }

    @FXML
    private void clientToCreditCardInfo() {
        if (clientInfoFildIn(true)) {
            clientErrorLable.setVisible(false);
            creditCardViewToFront();
            return;
        }
        clientInfoDoneButton.setDisable(true);
        clientErrorLable.setVisible(true);
    }

    private void ClientInfoDone() {
        if (clientInfoFildIn(false || clientErrorLable.isVisible())) {
            clientErrorLable.setVisible(false);
            clientInfoDoneButton.setDisable(false);
        }
    }

    private boolean CreditCardInfoFildIn(boolean displayError) {
        boolean fildIn = true;

        fildIn = isFieldIn(validMonthField, displayError) && fildIn;

        fildIn = isFieldIn(validYearField, displayError) && fildIn;

        return fildIn;
    }

    private boolean isComplete(boolean dispalyError) {
        boolean b = true;
        b = isLength(c1, 4, dispalyError) && b;

        b = isLength(c2, 4, dispalyError) && b;

        b = isLength(c3, 4, dispalyError) && b;

        b = isLength(c4, 4, dispalyError) && b;

        if (c1.getStyleClass().contains("error") || c2.getStyleClass().contains("error") || c3.getStyleClass().contains("error") || c4.getStyleClass().contains("error")) {
            cardError.setVisible(true);
        } else {
            cardError.setVisible(false);
        }

        b = isLength(cvcField, 3, dispalyError) && b;
        if (cvcField.getStyleClass().contains("error")) {
            cvcError.setVisible(true);
        } else {
            cvcError.setVisible(false);
        }

        return b;
    }

    private boolean isLength(TextField textField, int length, boolean displayError) {
        boolean b = textField.getLength() == length;
        if (!b) {
            if (displayError) {
                //  textField.getStyleClass().add("error");
                displayError(textField);
            }
            return false;
        }
        if (textField.getStyleClass().contains("error")) {
            textField.getStyleClass().removeAll("error");
        }
        return true;
    }

    private void displayError(TextField textField) {
        if (!textField.getStyleClass().contains("error")) {
            textField.getStyleClass().add("error");
        }
    }


    //TODO byt namn  creditCard to OrderDate
    @FXML
    private void CreditCardToOrderDate() {

        boolean creditCard = CreditCardInfoFildIn(true);
        boolean complete = isComplete(true);

        if (creditCard && complete) {
            creditCardErrorLable.setVisible(false);
            orderDateToFront();
            return;
        }
        creditCardDoneButton.setDisable(true);
        creditCardErrorLable.setVisible(true);
    }

    private void CreditCardInfoDone() {

        boolean creditCard = CreditCardInfoFildIn(false || creditCardErrorLable.isVisible());
        boolean complete = isComplete(false || creditCardErrorLable.isVisible());

        if (creditCard && complete) {
            creditCardErrorLable.setVisible(false);
            creditCardDoneButton.setDisable(false);
        }
    }

    //Todo byt namn till clientToOrderDate
    @FXML
    private void clientToOrderDate() {

        boolean client = clientInfoFildIn(false);
        boolean creditcar = CreditCardInfoFildIn(false);
        boolean complete = isComplete(false);

        if (client && creditcar && complete) {
            orderDateToFront();
            return;
        }

        if (client && (!creditcar || !complete)) {
            creditCardViewToFront();
            CreditCardToOrderDate();
            return;
        }

        clientToCreditCardInfo();
    }

    @Override
    public void navigateTo(NavigationTarget navigationTarget) {
        if (navigationTarget == NavigationTarget.PAYMENT) {
            if (resize) {
                confirmationPane.toBack();
                confirmationPane.setDisable(true);
                splitPane.setDisable(false);

                switchView(stackPane.getChildren().get(stackPane.getChildren().size() - 1));

                setSize(splitPane);
            }
        } else {
            confirmationPane.setDisable(true);
            splitPane.setDisable(true);
        }

    }

    private void resizeParent() {
        model.navigate(NavigationTarget.CHECKOUT);
        model.navigateBack();
    }

    private void switchView(Node node) {
        stackPane.getChildren().forEach(child -> {
            child.setDisable(true);
        });
        node.setDisable(false);
        node.toFront();
    }

    @Override
    public void onProductAdded(Product product, Double amount) {
        updateOrderText();
    }

    @Override
    public void onProductRemoved(Product product, Double oldAmount) {
        updateOrderText();
    }

    @Override
    public void onProductUpdate(Product product, Double newAmount) {
        updateOrderText();
    }

    private void orderDateDone() {
        if (orderDateCompleted(false || OrderErrorLable.isVisible())) {
            OrderErrorLable.setVisible(false);
            orderdateNextButton.setDisable(false);
        }
    }

    private boolean isHour(TextField t, Boolean displayError) {
        if (t.getText().trim().equals("")) {

            if (displayError) {
                displayError(t);
            }
            return false;
        }
        double h = Double.parseDouble(t.getText().trim());

        if (!(h >= 0 && h < 24)) {
            if (displayError) {
                displayError(t);
            }
            return false;
        }

        if (t.getStyleClass().contains("error")) {
            t.getStyleClass().removeAll("error");
        }
        return true;
    }

    private boolean isMinute(TextField t, Boolean displayError) {
        if (t.getText().trim().equals("")) {

            if (displayError) {
                displayError(t);
            }
            return false;
        }
        double m = Double.parseDouble(t.getText());
        if (!(m >= 0 && m < 60)) {
            if (displayError) {
                displayError(t);
            }
            return false;
        }

        if (t.getStyleClass().contains("error")) {
            t.getStyleClass().removeAll("error");
        }
        return true;
    }

    private boolean orderDateCompleted(boolean dispalyError) {
        boolean b = true;
        b = (isHour(hourTextField, dispalyError)) && b;

        b = (isMinute(minuteTextField, dispalyError)) && b;

      /*
        if ( hourTextField.getStyleClass().contains("error") || minuteTextField.getStyleClass().contains("error")) {
          //  cardError.setVisible(true);
        } else {
       //     cardError.setVisible(false);
        }
        */
        return b;
    }

    @FXML
    private void orderDateToSummary() {
        if (orderDateCompleted(true)) {
            OrderErrorLable.setVisible(false);
            summaryViewToFront();
            return;
        }
        OrderErrorLable.setVisible(true);
        orderdateNextButton.setDisable(true);
    }

    @FXML
    private void clientToSummary() {
        clientToOrderDate();

        boolean creditCard = CreditCardInfoFildIn(false);
        boolean complete = isComplete(false);

        if ((creditCard && complete) && (clientInfoFildIn(false))) {
            orderDateToSummary();
        }

    }

    @FXML
    private void CreditCardToSummary() {
        CreditCardToOrderDate();

        boolean creditCard = CreditCardInfoFildIn(false);
        boolean complete = isComplete(false);

        if (creditCard && complete) {
            orderDateToSummary();
        }
    }

}