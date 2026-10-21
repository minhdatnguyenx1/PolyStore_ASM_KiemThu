package com.polystore.controllers;

import com.polystore.entities.Product;
import com.polystore.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    ProductRepository productRepo;

    // Trang chủ: Trưng bày hàng hóa (Map với @/home/index)
    @RequestMapping({"/", "/home/index"})
    public String home(Model model) {
        List<Product> items = productRepo.findAll();
        model.addAttribute("items", items);
        return "product/home"; // Sẽ trả về file templates/product/home.html
    }

    // Hiển thị chi tiết và hàng cùng loại
    @GetMapping("/product/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Product item = productRepo.findById(id).orElse(null);
        model.addAttribute("item", item);
        return "product/product-detail"; // Sẽ trả về file templates/product/product-detail.html
    }

    // Tìm kiếm theo loại (Lọc theo danh mục)
    @GetMapping("/product/list-by-category/{id}")
    public String listByCategory(Model model, @PathVariable("id") String id) {
        List<Product> items = productRepo.findByCategoryId(id);
        model.addAttribute("items", items);
        return "product/product-list"; // Sẽ trả về file templates/product/product-list.html
    }
}