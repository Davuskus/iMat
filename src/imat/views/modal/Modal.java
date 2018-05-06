package imat.views.modal;

import javafx.event.Event;
import javafx.fxml.FXML;

public class Modal {

    @FXML
    public void consumeEvent(Event event) {
        event.consume();
    }

    @FXML
    public void closeButtonOnAction(Event event) {

    }

}
