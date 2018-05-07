package imat.ui.controls.history.article;

import imat.model.Model;
import imat.utils.FXMLLoader;
import imat.utils.IMatUtils;
import imat.utils.ImageUtils;
import imat.utils.MathUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.ResourceBundle;

public class ArticleHistoryItem extends AnchorPane implements Initializable {

    @FXML
    private ImageView productImageView;

    @FXML
    private Label productNameLabel;

    @FXML
    private Label ecoLabel;

    @FXML
    private Label countLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private VBox articleInfoVBox;

    private ShoppingItem shoppingItem;

    private Model model;

    public ArticleHistoryItem(Model model) {
        super();
        this.model = model;
        FXMLLoader.loadFXMLFromRootPackage("article_history_item.fxml", this, this);
    }

    @FXML
    private void copyToCartButtonOnAction(Event event) {
        model.addToShoppingCart(shoppingItem.getProduct(), shoppingItem.getAmount());
    }

    public ShoppingItem getShoppingItem() {
        return IMatUtils.cloneShoppingItem(shoppingItem);
    }

    public void setShoppingItem(ShoppingItem shoppingItem) {
        this.shoppingItem = IMatUtils.cloneShoppingItem(shoppingItem);
        Product product = this.shoppingItem.getProduct();

        productImageView.setImage(ImageUtils.getSquareImage(new Image("/imat/resources/images/products/" + product.getImageName())));

        productNameLabel.setText(product.getName());

        if (!product.isEcological()) {
            articleInfoVBox.getChildren().remove(ecoLabel);
        }

        priceLabel.setText(String.valueOf(Math.round(this.shoppingItem.getTotal())) + " kr");

        boolean suffixIsKg = shoppingItem.getProduct().getUnitSuffix().equals("kg");
        boolean suffixIsL = shoppingItem.getProduct().getUnitSuffix().equals("l");
        if (suffixIsKg || suffixIsL) {
            countLabel.setText(String.valueOf(MathUtils.round(shoppingItem.getAmount(), 1)) + " " + product.getUnitSuffix());
        } else {
            countLabel.setText(String.valueOf((int) this.shoppingItem.getAmount()) + " " + product.getUnitSuffix());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}