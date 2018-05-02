package imat.controls.history.article;

import imat.interfaces.ShoppingListener;
import imat.utils.FXMLLoader;
import imat.utils.IMatUtils;
import imat.utils.ImageUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.util.ArrayList;
import java.util.List;

public class ArticleHistoryItem extends AnchorPane {

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

    private final ShoppingItem shoppingItem;

    private final List<ShoppingListener> shoppingListeners;

    public ArticleHistoryItem(ShoppingItem shoppingItem) {
        this.shoppingItem = shoppingItem;
        shoppingListeners = new ArrayList<>(1);
        FXMLLoader.loadFXMLFromRootPackage("article_history_item.fxml", this, this);

        Product product = shoppingItem.getProduct();

        product.getUnitSuffix();

        productImageView.setImage(ImageUtils.getSquareImage(new Image("/imat/resources/images/products/" + product.getImageName())));

        productNameLabel.setText(product.getName());

        if (!product.isEcological()) {
            articleInfoVBox.getChildren().remove(ecoLabel);
        }

        priceLabel.setText(String.valueOf(shoppingItem.getTotal()) + " kr");
        countLabel.setText(String.valueOf((int) shoppingItem.getAmount()) + " " + product.getUnitSuffix());

    }

    public void addShoppingListener(ShoppingListener shoppingListener) {
        shoppingListeners.add(shoppingListener);
    }

    public void addShoppingListeners(List<ShoppingListener> shoppingListeners) {
        this.shoppingListeners.addAll(shoppingListeners);
    }

    @FXML
    private void copyToCartButtonOnAction(Event event) {
        for (ShoppingListener shoppingListener : shoppingListeners) {
            shoppingListener.onAddShoppingItem(getShoppingItem());
        }
    }

    public ShoppingItem getShoppingItem() {
        return IMatUtils.cloneShoppingItem(shoppingItem);
    }

}
