package com.polystore.controllers;

import com.polystore.entities.Account;
import com.polystore.entities.Order;
import com.polystore.entities.OrderDetail;
import com.polystore.models.Item;
import com.polystore.repositories.OrderDetailRepository;
import com.polystore.repositories.OrderRepository;
import com.polystore.repositories.ProductRepository;
import com.polystore.services.CartService;
import com.polystore.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    
    @Autowired
    OrderRepository orderRepo;
    
    @Autowired
    OrderDetailRepository orderDetailRepo;
    
    @Autowired
    ProductRepository productRepo;
    
    @Autowired
    CartService cartService;
    
    @Autowired
    SessionService session;

    // Hiển thị form thanh toán (@/order/checkout => check-out.html)
    @GetMapping("/checkout")
    public String checkoutForm(Model model) {
        model.addAttribute("cart", cartService);
        return "order/check-out"; 
    }

    // Xử lý đặt hàng
    @PostMapping("/checkout")
    public String checkout(@RequestParam("address") String address, Model model) {
        Account user = session.get("user");
        if (user == null) {
            return "redirect:/auth/login"; // Bắt buộc đăng nhập khi đặt hàng
        }

        // 1. Lưu hóa đơn (Order)
        Order order = new Order();
        order.setAccount(user);
        order.setAddress(address);
        order.setCreateDate(new Date());
        orderRepo.save(order);

        // 2. Lưu chi tiết hóa đơn (OrderDetail)
        for (Item item : cartService.getItems()) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(productRepo.findById(item.getId()).get());
            detail.setPrice(item.getPrice());
            detail.setQuantity(item.getQty());
            orderDetailRepo.save(detail);
        }

        // 3. Đặt xong thì dọn sạch giỏ hàng
        cartService.clear();
        return "redirect:/order/list";
    }

    // Liệt kê các đơn hàng đã đặt (@/order/list => order-list.html)
    @GetMapping("/list")
    public String list(Model model) {
        Account user = session.get("user");
        List<Order> orders = orderRepo.findByUsername(user.getUsername());
        model.addAttribute("orders", orders);
        return "order/order-list"; 
    }

    // Xem chi tiết đơn hàng (@/order/detail/{id} => order-detail.html)
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        Order order = orderRepo.findById(id).orElse(null);
        model.addAttribute("order", order);
        return "order/order-detail"; 
    }
    
    // Liệt kê mặt hàng đã mua (@/order/my-product-list => my-product-list.html)
    @GetMapping("/my-product-list")
    public String myProductList() {
        return "order/my-product-list"; 
    }
}