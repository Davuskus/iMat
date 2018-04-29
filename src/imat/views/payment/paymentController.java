package imat.views.payment;

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
import se.chalmers.cse.dat216.project.User;

import java.net.URL;
import java.util.ResourceBundle;

public class paymentController implements  Initializable{

    @FXML
    Button ClientInfoDoneButton;

    @FXML
    Button creditcardDoneButton;

    @FXML
    Button creditcardBackButton;

    @FXML
    Button changeCreditcardInfoButton;

    @FXML
    Button changeClientInfoButton;

    @FXML
    AnchorPane sammanfattning;

    @FXML
    AnchorPane kunduppgifter;

    @FXML
    AnchorPane kreditkortsuppgifter;


    @FXML
    VBox firstSidebarVbox;

    @FXML
    VBox secondSidebarVbox;

    @FXML
    VBox thirdSidebarVbox;


    @FXML
    TextField fistnameField ;
    @FXML
    TextField lastnameField;
    @FXML
    TextField addressField;
    @FXML
    TextField postcodeField;
    @FXML
    TextField phonenumberField;
    @FXML
    TextField creditcardnumberField;
    @FXML
    TextField validMonthField;
    @FXML
    TextField validYearField;
    @FXML
    TextField cvcField;

    @FXML
    TextArea clientSummeryArea;
    @FXML
    TextArea creditcardSummeryArea;

    @FXML
    CheckBox SaveuserInfoCheckBox;


    IMatDataHandler imatH=IMatDataHandler.getInstance();

    Customer costomer=  imatH.getCustomer();

    CreditCard creditCard=  imatH.getCreditCard();




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
        creditcardnumberField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    creditcardnumberField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });


    }

    private void fillUserData() {
     fistnameField.appendText(costomer.getFirstName());
     lastnameField.appendText(costomer.getLastName());
     addressField.appendText(costomer.getAddress());
     postcodeField.appendText(costomer.getPostCode());
     phonenumberField.appendText(costomer.getPhoneNumber());


     creditcardnumberField.appendText(creditCard.getCardNumber());
     validMonthField.appendText(Integer.toString(creditCard.getValidMonth()));
     validYearField.appendText(Integer.toString(creditCard.getValidYear()));
     cvcField.appendText(Integer.toString(creditCard.getVerificationCode()));
    }

    @FXML
    private void SaveUserinfo(){
        if( SaveuserInfoCheckBox.isSelected()) {
            costomer.setFirstName(fistnameField.getText());
            costomer.setLastName(lastnameField.getText());
            costomer.setAddress(addressField.getText());
            costomer.setPostCode(postcodeField.getText());
            costomer.setPhoneNumber(phonenumberField.getText());

            creditCard.setCardNumber(creditcardnumberField.getText());
            creditCard.setValidMonth(Integer.parseInt(validMonthField.getText()));
            creditCard.setValidYear(Integer.parseInt(validYearField.getText()));
            creditCard.setVerificationCode(Integer.parseInt(cvcField.getText()));
        }
    }



    private void updateTextArea(){
        updatCreditcardTextArea();
        updateClientTextArea();
    }

    private void updatCreditcardTextArea() {
        creditcardSummeryArea.clear();
        creditcardSummeryArea.appendText(creditcardToString());
    }

    private String creditcardToString(){

        StringBuilder strB=new StringBuilder();
        strB.append(creditcardnumberField.getText()+"\n");
        strB.append(validMonthField.getText()+" / ");
        strB.append(validYearField.getText()+"\n");
        strB.append(cvcField.getText());
        return strB.toString();
    }

    private void updateClientTextArea() {
        clientSummeryArea.clear();
        clientSummeryArea.appendText(clientToString());
    }
    private String clientToString(){

        StringBuilder strB=new StringBuilder();
        strB.append(fistnameField.getText()+" ");
        strB.append(lastnameField.getText()+"\n");
        strB.append(addressField.getText()+"\n");
        strB.append(postcodeField.getText()+"\n");
        strB.append(phonenumberField.getText());
        return strB.toString();
    }

    @FXML
    private void credidcardViewToFront(){
        kreditkortsuppgifter.toFront();
        secondSidebarVbox.toFront();
    }
    @FXML
    private void summaryViewToFront(){
        updateTextArea();
        sammanfattning.toFront();
        thirdSidebarVbox.toFront();
    }
    @FXML
    private void clientViewToFront(){
        kunduppgifter.toFront();
        firstSidebarVbox.toFront();
    }



    @FXML
    public void consumeEvent(Event event) {
        event.consume();
    }




    @FXML
    private void moveToBack(){

    }



}