package imat.ui.controls.history.order;

import imat.enums.NavigationTarget;
import imat.model.FXMLController;
import imat.ui.views.browse.centerviews.history.OrderHistoryPane;
import imat.utils.DateUtils;
import imat.utils.IMatUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrderHistoryItem extends FXMLController {

    @FXML
    private Label dateLabel;

    private OrderHistoryPane orderHistoryPane;

    private Order order;

    private final String dateFormat;

    public OrderHistoryItem() {
        super();
        dateFormat = "yyyy/MM/dd - HH:mm";
    }

    /**
     * Returns the orders shopping items in a list.
     *
     * @return The order's shopping items.
     */
    public List<ShoppingItem> getShoppingItems() {
        return IMatUtils.cloneShoppingItemList(order.getItems());
    }

    /**
     * Returns the total number of shopping items (articles).
     *
     * @return The total number of shopping items (articles).
     */
    public int getNumShoppingItems() {
        return order.getItems().size();
    }

    /**
     * Returns the date format used in this class.
     *
     * @return The date format String used in this class.
     */
    public String getDateFormat() {
        return dateFormat;
    }

    public Order getOrder() {
        return IMatUtils.cloneOrder(order);
    }

    @FXML
    private void onAction(Event Event) {
        model.selectOrder(order);
        model.navigate(NavigationTarget.ORDER_HISTORY_ARTICLE);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setOrderHistoryPane(OrderHistoryPane orderHistoryPane) {
        this.orderHistoryPane = orderHistoryPane;
    }

    public void setOrder(Order order) {
        this.order = order;
        dateLabel.setText(DateUtils.getFormattedDate(order.getDate(), dateFormat));
    }

}
