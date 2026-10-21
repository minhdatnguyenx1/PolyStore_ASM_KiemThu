package com.polystore.controllers;

import com.polystore.entities.Category;
import com.polystore.entities.Product;
import com.polystore.repositories.CategoryRepository;
import com.polystore.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/product")
public class ProductAController {

    @Autowired
    ProductRepository productRepo;
    
    @Autowired
    CategoryRepository categoryRepo;

    // Tự động cung cấp danh sách Category cho các view của Controller này
    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryRepo.findAll();
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("item", new Product());
        model.addAttribute("items", productRepo.findAll());
        return "admin/product";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("item", productRepo.findById(id).orElse(new Product()));
        model.addAttribute("items", productRepo.findAll());
        return "admin/product";
    }

    @PostMapping("/create")
    public String create(Product item) {
        productRepo.save(item);
        return "redirect:/admin/product/index";
    }

    @PostMapping("/update")
    public String update(Product item) {
        productRepo.save(item);
        return "redirect:/admin/product/edit/" + item.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        productRepo.deleteById(id);
        return "redirect:/admin/product/index";
    }
}