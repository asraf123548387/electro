package com.shopping.electroshopping.service.orderService;

import com.shopping.electroshopping.model.order.Order;
import com.shopping.electroshopping.model.order.OrderItems;
import com.shopping.electroshopping.model.wallet.Wallet;
import com.shopping.electroshopping.repository.OrderItemsRepository;
import com.shopping.electroshopping.repository.OrderRepository;
import com.shopping.electroshopping.repository.WalletRepository;
import com.shopping.electroshopping.service.wallet.WalletServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
@Autowired
    OrderRepository orderRepository;
@Autowired
    WalletServiceImpl walletService;
@Autowired
    WalletRepository walletRepository;
@Autowired
    OrderItemsRepository orderItemsRepository;

    @Override
    @Transactional
    public void editstatus(Long orderId, Order orderdto) {
        Order order=orderRepository.findById(orderId).orElse(null);
        order.setStatus(orderdto.getStatus());
        orderRepository.save(order);

    }

    @Override
    public void deleteProductByid(Long id) {
        this.orderRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null && !order.getStatus().equalsIgnoreCase("cancelled")) {
            order.setStatus("cancelled");

            // Refund the order amount to the user's wallet
            double refundAmount = order.getTotalAmount();
            Wallet userWallet = order.getUser().getWallets(); // Get the user's wallet
            if (userWallet != null) {
                userWallet.deposit(refundAmount);
                walletRepository.save(userWallet); // Save the updated wallet
            }

            orderRepository.save(order);
        }
    }


    public Double getTotalOrderAmount() {
        return orderRepository.getTotalOrderAmount();
    }
    public Double getTotalOrderAmountForToday() {
        LocalDate todayDate = LocalDate.now();
        return orderRepository.getTotalOrderAmountForToday(todayDate);
    }

    public Double getTotalOrderAmountForOctober() {
        return orderRepository.getTotalOrderAmountForOctober();
    }

    public Double getTotalOrderAmountForNovember() {
        Double totalAmount = orderRepository.getTotalOrderAmountForNovember();

        if (totalAmount == null) {
            totalAmount = 0.0;
        }
        return totalAmount;
    }
}
