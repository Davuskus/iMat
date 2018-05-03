package imat.views.modal.views.copyorder;

import imat.utils.FXMLLoader;
import imat.views.modal.Modal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class CopyOrderModalView extends AnchorPane {

    @FXML
    private HBox headerHBox;

    @FXML
    private Button closeButton;

    @FXML
    private ImageView closeButtonImageView;

    @FXML
    private Button addToButton;

    @FXML
    private Button replaceButton;

    @FXML
    private Button cancelButton;

    public CopyOrderModalView(Modal modal) {
        super();
        FXMLLoader.loadFXMLFromRootPackage("copy_order_modal_view.fxml", this, this);
    }

    @FXML
    private void addToButtonOnAction(ActionEvent event) {

    }

    @FXML
    private void cancelButtonOnAction(ActionEvent event) {

    }

    @FXML
    private void closeButtonOnAction(ActionEvent event) {

    }

    private void hideModal() {

    }

    @FXML
    private void consumeEvent(MouseEvent event) {

    }

    @FXML
    private void replaceButtonOnAction(ActionEvent event) {

    }

}
