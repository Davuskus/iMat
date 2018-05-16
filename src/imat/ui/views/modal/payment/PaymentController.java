package imat.ui.views.modal.payment;

import imat.enums.NavigationTarget;
import imat.interfaces.INavigationListener;
import imat.model.FXMLController;
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

import javax.xml.stream.events.EndElement;
import java.net.URL;
import java.util.ResourceBundle;

public class PaymentController extends FXMLController implements Initializable, INavigationListener {

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

    private final IMatDataHandler iMatDataHandler;

    private final Customer customer;

    private final CreditCard creditCard;

    private boolean resize;

    public PaymentController() {
        iMatDataHandler = IMatDataHandler.getInstance();
        customer = iMatDataHandler.getCustomer();
        creditCard = iMatDataHandler.getCreditCard();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillUserData();
        updateTextArea();
        setSize(splitPane);
        resize = true;

        confirmationPane.setDisable(true);
        splitPane.setDisable(true);

        model.addNavigationListener(this);


      /*  creditCardNumberField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    creditCardNumberField.setText(newValue.replaceAll("[^\\d.-]", ""));
                }
            }
        });
        */
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
        });
        phoneNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            ClientInfoDone();
        });

          /*
     creditCardNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            CreditCardInfoDone();



            if(creditCardNumberField.getCharacters().length()>0&&creditCardNumberField.getCharacters().charAt(creditCardNumberField.getCharacters().length()-1)=='-'){

             newValue=(""+creditCardNumberField.getCharacters().subSequence(0,creditCardNumberField.getCharacters().length()-1));
            }


    String str=creditCardNumberField.getText();
   // str.replaceAll("-", "");
    long num = str.trim().chars().count();

                if(num%5==0&& num<18&&num>3 && creditCardNumberField.getCharacters().charAt(creditCardNumberField.getCharacters().length()-1)!='-'){
                    newValue=(newValue.substring(0,(int)num-1)+"-"+creditCardNumberField.getCharacters().charAt(creditCardNumberField.getCharacters().length()-1));
                }

    // creditCardNumberField.clear();
    //  creditCardNumberField.appendText(newValue);

    if (num > 16) {
        String t = str.substring(0, 16);

        creditCardNumberField.clear();
        creditCardNumberField.appendText(t);
    }

    StringBuilder b = new StringBuilder();
    for (int i = 0; i < str.length(); i++) {
        b.append(str.charAt(i));
        if (i % 4 == 0 && i > 0 && i < 14) {
            b.append("-");
        }
    }
    str = b.toString();

    System.out.println(str);
            if(!str.equals(creditCardNumberField.getText())) {
                creditCardNumberField.clear();
                 creditCardNumberField.appendText(str);
            }

        });
    */
        validMonthField.textProperty().addListener((observable, oldValue, newValue) -> {
            CreditCardInfoDone();
            if(validMonthField.getText().trim().chars().count()>2){
                String t=validMonthField.getText().trim().substring(0,2);
                validMonthField.clear();
                validMonthField.appendText(t);
            }
        });
        validYearField.textProperty().addListener((observable, oldValue, newValue) -> {
            CreditCardInfoDone();
            if(validYearField.getText().trim().chars().count()>2){
                String t=validYearField.getText().trim().substring(0,2);
                validYearField.clear();
                validYearField.appendText(t);
            }
        });
        cvcField.textProperty().addListener((observable, oldValue, newValue) -> {
            CreditCardInfoDone();
            if(cvcField.getText().trim().chars().count()>3){
                String t=cvcField.getText().trim().substring(0,3);
                cvcField.clear();
                cvcField.appendText(t);
            }
        });

        c1.textProperty().addListener((observable, oldValue, newValue) -> {
            CreditCardInfoDone();
             if(c1.getText().chars().count()==4){c2.requestFocus();}
            if(c1.getText().trim().chars().count()>4){
                String t=c1.getText().trim().substring(0,4);
                c1.clear();
                c1.appendText(t);
            }
        });
        c2.textProperty().addListener((observable, oldValue, newValue) -> {
            CreditCardInfoDone();
            if(c2.getText().chars().count()==4){c3.requestFocus();}
            if(c2.getText().trim().chars().count()>4){
                String t=c2.getText().trim().substring(0,4);
                c2.clear();
                c2.appendText(t);
            }
        });
        c3.textProperty().addListener((observable, oldValue, newValue) -> {
            CreditCardInfoDone();
            if(c3.getText().chars().count()==4){c4.requestFocus();}
            if(c3.getText().trim().chars().count()>4){
                String t=c3.getText().trim().substring(0,4);
                c3.clear();
                c3.appendText(t);
            }
        });
        c4.textProperty().addListener((observable, oldValue, newValue) -> {
            CreditCardInfoDone();

            if(c4.getText().trim().chars().count()>4){
                String t=c4.getText().trim().substring(0,4);
                c4.clear();
                c4.appendText(t);
            }
        });
    }

    private void fillUserData() {
        firstNameField.appendText(customer.getFirstName());
        lastNameField.appendText(customer.getLastName());
        addressField.appendText(customer.getAddress());
        postcodeField.appendText(customer.getPostCode());
        phoneNumberField.appendText(customer.getPhoneNumber());

      //  creditCardNumberField.appendText(creditCard.getCardNumber());
        fillcreditcard();

        validMonthField.appendText(Integer.toString(creditCard.getValidMonth()));
        validYearField.appendText(Integer.toString(creditCard.getValidYear()));
        cvcField.appendText(Integer.toString(creditCard.getVerificationCode()));
    }

    private void fillcreditcard(){
        String c=creditCard.getCardNumber();
        for (int i = 0; i <c.length() ; i++) {
            if(i<4){c1.appendText(""+c.charAt(i));}
            else if(i>3&&i<8){c2.appendText(""+c.charAt(i));}
            else if(i>7&&i<12){c3.appendText(""+c.charAt(i));}
            else{c4.appendText(""+c.charAt(i));}
        }
    }

    private void saveUserInfo() {
        if (saveUserInfoCheckBox.isSelected()) {
            customer.setFirstName(firstNameField.getText());
            customer.setLastName(lastNameField.getText());
            customer.setAddress(addressField.getText());
            customer.setPostCode(postcodeField.getText());
            customer.setPhoneNumber(phoneNumberField.getText());

          //  creditCard.setCardNumber(creditCardNumberField.getText());
            creditCard.setCardNumber(c1.getText()+c2.getText()+c3.getText()+c4.getText());
            creditCard.setValidMonth(Integer.parseInt(validMonthField.getText()));
            creditCard.setValidYear(Integer.parseInt(validYearField.getText()));
            creditCard.setVerificationCode(Integer.parseInt(cvcField.getText()));
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
    }

    private void updateCreditCardTextArea() {
        creditCardSummeryArea.clear();
        creditCardSummeryArea.appendText(creditCardToString());
    }

    private String creditCardToString() {

        StringBuilder strB = new StringBuilder();
       // strB.append(creditCardNumberField.getText() + "\n");
        strB.append(c1.getText()+"-"+c2.getText()+"-"+c3.getText()+"-"+c4.getText()+ "\n");

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

        customerInfoPane.setDisable(true);
        conclusionPane.setDisable(true);
        creditCardInfoPane.setDisable(false);

      //  creditCardNumberField.requestFocus();
        c1.requestFocus();

        creditCardInfoPane.toFront();
        secondSidebarVBox.toFront();
    }

    @FXML
    private void summaryViewToFront() {
        customerInfoPane.setDisable(true);
        conclusionPane.setDisable(false);
        creditCardInfoPane.setDisable(true);

        changeClientInfoButton.requestFocus();

        updateTextArea();
        conclusionPane.toFront();
        thirdSidebarVBox.toFront();
    }

    @FXML
    private void clientViewToFront() {

        customerInfoPane.setDisable(false);
        conclusionPane.setDisable(true);
        creditCardInfoPane.setDisable(true);

        firstNameField.requestFocus();

        customerInfoPane.toFront();
        firstSidebarVBox.toFront();
    }


    @FXML
    private void moveBack() {
        //SaveUserInfo();
        //  confirmationPane.setDisable(true);
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
        if (clientInfoFildIn(false)) {
            clientErrorLable.setVisible(false);
            clientInfoDoneButton.setDisable(false);
        }
    }

    private boolean CreditCardInfoFildIn(boolean displayError) {
        boolean fildIn = true;
      //  fildIn = isFieldIn(creditCardNumberField, displayError) && fildIn;

     /*   fildIn=isFieldIn(c1,displayError)&&fildIn;
        fildIn=isFieldIn(c2,displayError)&&fildIn;
        fildIn=isFieldIn(c3,displayError)&&fildIn;
        fildIn=isFieldIn(c4,displayError)&&fildIn;
*/

        fildIn = isFieldIn(validMonthField, displayError) && fildIn;

        fildIn = isFieldIn(validYearField, displayError) && fildIn;

    //    fildIn = isFieldIn(cvcField, displayError) && fildIn;



        return fildIn;
    }

    private boolean isComplete(boolean dispalyError){
        boolean b=true;
        b=isLength(c1,4,dispalyError)&&b;

        b=isLength(c2,4,dispalyError)&&b;

        b=isLength(c3,4,dispalyError)&&b;

        b=isLength(c4,4,dispalyError)&&b;

        b=isLength(cvcField,3,dispalyError)&&b;


        return b;
    }
    private boolean isLength(TextField textField,int length,boolean displayError){
        boolean b= textField.getLength()==length;
        if(!b){
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

    private void displayError(TextField textField){
        if(!textField.getStyleClass().contains("error")){
            textField.getStyleClass().add("error");
        }
    }

    @FXML
    private void CreditCardToSummary() {

        boolean creditcar=CreditCardInfoFildIn(true);
        boolean complete= isComplete(true);

        if (creditcar && complete) {
            creditCardErrorLable.setVisible(false);
            summaryViewToFront();
            return;
        }
        creditCardDoneButton.setDisable(true);
        creditCardErrorLable.setVisible(true);
    }

    private void CreditCardInfoDone() {

        boolean creditcar=CreditCardInfoFildIn(false);
        boolean complete= isComplete(false);

        if (creditcar && complete) {
            creditCardErrorLable.setVisible(false);
            creditCardDoneButton.setDisable(false);
        }
    }

    @FXML
    private void clientToSummary() {

        boolean client=clientInfoFildIn(false);
        boolean creditcar=CreditCardInfoFildIn(false);
        boolean complete= isComplete(false);

        if (client && creditcar && complete) {
            summaryViewToFront();
            return;
        }

        if (client && (!creditcar || !complete)) {


            creditCardViewToFront();
            CreditCardToSummary();

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

}