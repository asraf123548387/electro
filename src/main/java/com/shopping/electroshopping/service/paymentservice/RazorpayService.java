package com.shopping.electroshopping.service.paymentservice;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Order;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RazorpayService {
    @Autowired
    private RazorpayClient razorpayClient;

    public Order createOrder(Double total, String currency, String description) throws RazorpayException {
        total = total * 100; // Razorpay works with currency in paise
        JSONObject options = new JSONObject();
        options.put("amount", total);
        options.put("currency", currency);
        options.put("receipt", "order_" + System.currentTimeMillis());
        options.put("payment_capture", 1);

        JSONObject notes = new JSONObject();
        notes.put("order_description", "Payment for: " + description);

        options.put("notes", notes);

        return razorpayClient.orders.create(options);
    }

    // Add methods for payment execution and handling callbacks as needed
}
