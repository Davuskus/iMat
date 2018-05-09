package imat.ui.views.modal.payment;

import imat.interfaces.INavigationListener;
import imat.model.FXMLController;
import imat.enums.NavigationTarget;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.CreditCard;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.net.URL;
import java.util.ResourceBundle;

public class PaymentController extends FXMLController implements Initializable,INavigationListener{

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

    @FXML
    private Label clientErrorLable;

    @FXML
    private AnchorPane confirmationPane;

    @FXML
    private Label creditCardErrorLable;

    @FXML
    private  SplitPane splitPane;

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

        model.addNavigationListener(this);

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

        postcodeField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    postcodeField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });



        firstNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            ClientInfoDone ();
        });
        lastNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            ClientInfoDone ();
        });
        addressField.textProperty().addListener((observable, oldValue, newValue) -> {
            ClientInfoDone ();
        });
        postcodeField.textProperty().addListener((observable, oldValue, newValue) -> {
            ClientInfoDone ();
        });
        phoneNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            ClientInfoDone ();
        });




        creditCardNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            CreditCardInfoDone ();
        });
        validMonthField.textProperty().addListener((observable, oldValue, newValue) -> {
            CreditCardInfoDone ();
        });
        validYearField.textProperty().addListener((observable, oldValue, newValue) -> {
            CreditCardInfoDone ();
        });
        cvcField.textProperty().addListener((observable, oldValue, newValue) -> {
            CreditCardInfoDone ();
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


    private void saveUserInfo() {
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

    @FXML
    private void payAndOrder(){
        saveUserInfo();
        model.placeOrder();
        clientViewToFront();

        confirmationPane.toFront();

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
    private void creditCardViewToFront() {

        customerInfoPane.setFocusTraversable(false);
        conclusionPane.setFocusTraversable(false);
        creditCardInfoPane.setFocusTraversable(false);

        creditCardNumberField.requestFocus();

        creditCardInfoPane.toFront();
        secondSidebarVBox.toFront();
    }

    @FXML
    private void summaryViewToFront() {
        customerInfoPane.setFocusTraversable(false);
        conclusionPane.setFocusTraversable(true);
        creditCardInfoPane.setFocusTraversable(false);

        changeClientInfoButton.requestFocus();

        updateTextArea();
        conclusionPane.toFront();
        thirdSidebarVBox.toFront();
    }

    @FXML
    private void clientViewToFront() {

        customerInfoPane.setFocusTraversable(true);
        conclusionPane.setFocusTraversable(false);
        creditCardInfoPane.setFocusTraversable(false);

        firstNameField.requestFocus();

        customerInfoPane.toFront();
        firstSidebarVBox.toFront();
    }



    @FXML
    private void moveBack() {
        //SaveUserInfo();

        confirmationPane.setFocusTraversable(false);
        splitPane.setFocusTraversable(false);
        model.navigate(NavigationTarget.CHECKOUT);
    }


    private boolean isTextFieldFieldIn(TextField t){
        return !(t.getText().trim().length()==0);
    }


    private boolean isFieldIn(TextField t,boolean displayError){

        if(!isTextFieldFieldIn(t)){
            if(displayError) {
                t.getStyleClass().add("error");
            }
            return false;
        }

        if(t.getStyleClass().contains("error")){
            t.getStyleClass().removeAll("error");
        }
        return true;
    }



    private boolean clientInfoFildIn( boolean displayError){
       boolean fildIn=true;
        fildIn=isFieldIn( firstNameField,displayError) && fildIn;

        fildIn=isFieldIn( lastNameField,displayError)&& fildIn;

        fildIn=isFieldIn( addressField,displayError)&& fildIn;

        fildIn=isFieldIn( postcodeField,displayError)&& fildIn;

        fildIn=isFieldIn( phoneNumberField,displayError)&& fildIn;

        return fildIn;
    }

    @FXML
    private void clientToCreditCardInfo(){
        if(clientInfoFildIn(true)){
            clientErrorLable.setVisible(false);
            creditCardViewToFront();
            return;
        }
        clientInfoDoneButton.setDisable(true);
        clientErrorLable.setVisible(true);
    }

    private void ClientInfoDone(){
        if(clientInfoFildIn(false)) {
            clientErrorLable.setVisible(false);
            clientInfoDoneButton.setDisable(false);
        }
    }


    private boolean CreditCardInfoFildIn( boolean displayError){
        boolean fildIn=true;
        fildIn=isFieldIn( creditCardNumberField,displayError) && fildIn;

        fildIn=isFieldIn( validMonthField,displayError)&& fildIn;

        fildIn=isFieldIn( validYearField,displayError)&& fildIn;

        fildIn=isFieldIn( cvcField,displayError)&& fildIn;


        return fildIn;
    }

    @FXML
    private void CreditCardToSummary(){
        if(CreditCardInfoFildIn(true)){
            creditCardErrorLable.setVisible(false);
            summaryViewToFront();
            return;
        }
        creditCardDoneButton.setDisable(true);
        creditCardErrorLable.setVisible(true);
    }

    private void CreditCardInfoDone(){
        if(CreditCardInfoFildIn(false)) {
            creditCardErrorLable.setVisible(false);
            creditCardDoneButton.setDisable(false);
        }
    }


    @FXML
    private void clientToSummary(){
        if(clientInfoFildIn(false) && CreditCardInfoFildIn(false)){
            summaryViewToFront();
            return;
        }

        if(clientInfoFildIn(false) && !CreditCardInfoFildIn(false)){
            creditCardViewToFront();
            return;
        }

        clientToCreditCardInfo();
    }


    @Override
    public void navigateTo(NavigationTarget navigationTarget) {
        if(navigationTarget==NavigationTarget.PAYMENT){
            confirmationPane.toBack();
            confirmationPane.setFocusTraversable(false);
            splitPane.setFocusTraversable(true);
        }


    }
}