package imat.interfaces;

import se.chalmers.cse.dat216.project.Order;

public interface IOrderHistoryRequestListener {

    Order onOlderOrderRequest(Order sourceOrder);

    Order onNewerOrderRequest(Order sourceOrder);

}
