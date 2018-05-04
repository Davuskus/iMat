package imat.views.payment;

import imat.FXMLController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class PaymentController extends FXMLController implements Initializable {

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

    @FXML
    TextField creditCardNumberField;

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

    private final IMatDataHandler iMatDataHandler;

    private final Customer customer;

    private final CreditCard creditCard;

    public PaymentController() {
        iMatDataHandler = IMatDataHandler.getInstance();
        customer = iMatDataHandler.getCustomer();
        creditCard = iMatDataHandler.getCreditCard();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillUserData();
        updateTextArea();

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
        creditCardNumberField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    creditCardNumberField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

    }

    private void fillUserData() {
        firstNameField.appendText(customer.getFirstName());
        lastNameField.appendText(customer.getLastName());
        addressField.appendText(customer.getAddress());
        postcodeField.appendText(customer.getPostCode());
        phoneNumberField.appendText(customer.getPhoneNumber());

        creditCardNumberField.appendText(creditCard.getCardNumber());
        validMonthField.appendText(Integer.toString(creditCard.getValidMonth()));
        validYearField.appendText(Integer.toString(creditCard.getValidYear()));
        cvcField.appendText(Integer.toString(creditCard.getVerificationCode()));
    }

    @FXML
    private void SaveUserInfo() {
        if (saveUserInfoCheckBox.isSelected()) {
            customer.setFirstName(firstNameField.getText());
            customer.setLastName(lastNameField.getText());
            customer.setAddress(addressField.getText());
            customer.setPostCode(postcodeField.getText());
            customer.setPhoneNumber(phoneNumberField.getText());

            creditCard.setCardNumber(creditCardNumberField.getText());
            creditCard.setValidMonth(Integer.parseInt(validMonthField.getText()));
            creditCard.setValidYear(Integer.parseInt(validYearField.getText()));
            creditCard.setVerificationCode(Integer.parseInt(cvcField.getText()));
        }
    }


    private void updateTextArea() {
        updateCreditCardTextArea();
        updateClientTextArea();
    }

    private void updateCreditCardTextArea() {
        creditCardSummeryArea.clear();
        creditCardSummeryArea.appendText(creditCardToString());
    }

    private String creditCardToString() {

        StringBuilder strB = new StringBuilder();
        strB.append(creditCardNumberField.getText() + "\n");
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
    private void credidcardViewToFront() {
        creditCardInfoPane.toFront();
        secondSidebarVBox.toFront();
    }

    @FXML
    private void summaryViewToFront() {
        updateTextArea();
        conclusionPane.toFront();
        thirdSidebarVBox.toFront();
    }

    @FXML
    private void clientViewToFront() {
        customerInfoPane.toFront();
        firstSidebarVBox.toFront();
    }


    @FXML
    public void consumeEvent(Event event) {
        event.consume();
    }


    @FXML
    private void moveBack() {
        //SaveUserInfo();
        model.navigate("pay");
    }

}