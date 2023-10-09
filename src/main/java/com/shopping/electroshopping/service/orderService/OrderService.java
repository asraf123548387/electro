package com.shopping.electroshopping.service.orderService;

import com.shopping.electroshopping.model.order.Order;


public interface OrderService {
    public void editstatus(Long orderId, Order orderdto);

    void deleteProductByid(Long id);

    void cancelOrder(Long id);
}
