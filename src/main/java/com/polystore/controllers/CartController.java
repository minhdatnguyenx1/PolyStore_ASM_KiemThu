package com.polystore.controllers;

import com.polystore.entities.Product;
import com.polystore.models.Item;
import com.polystore.repositories.ProductRepository;
import com.polystore.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    ProductRepository productRepo;

    // Hiển thị các mặt hàng trong giỏ
    @GetMapping("/view")
    public String view(Model model) {
        model.addAttribute("cart", cartService);
        return "cart/cart"; // Sẽ trả về file templates/cart/cart.html
    }

    // Thêm hàng vào giỏ
    @GetMapping("/add/{id}")
    public String add(@PathVariable("id") Integer id) {
        Product p = productRepo.findById(id).orElse(null);
        if (p != null) {
            Item item = new Item(p.getId(), p.getName(), p.getImage(), p.getPrice(), 1);
            cartService.add(item);
        }
        return "redirect:/cart/view"; // Thêm xong tự động chuyển về trang giỏ hàng
    }

    // Xóa bớt hàng khỏi giỏ
    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id) {
        cartService.remove(id);
        return "redirect:/cart/view";
    }

    // Cập nhật số lượng
    @RequestMapping("/update")
    public String update(@RequestParam("id") Integer id, @RequestParam("qty") int qty) {
        cartService.update(id, qty);
        return "redirect:/cart/view";
    }

    // Xóa sạch giỏ hàng
    @GetMapping("/clear")
    public String clear() {
        cartService.clear();
        return "redirect:/cart/view";
    }
}