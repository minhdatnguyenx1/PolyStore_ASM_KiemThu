package com.polystore.controllers;

import com.polystore.entities.Category;
import com.polystore.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/category")
public class CategoryAController {

    @Autowired
    CategoryRepository categoryRepo;

    // Hiển thị form trống và danh sách
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("item", new Category());
        model.addAttribute("items", categoryRepo.findAll());
        return "admin/category"; 
    }

    // Đổ dữ liệu của category cần sửa lên form
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") String id) {
        model.addAttribute("item", categoryRepo.findById(id).orElse(new Category()));
        model.addAttribute("items", categoryRepo.findAll());
        return "admin/category";
    }

    // Thêm mới
    @PostMapping("/create")
    public String create(Category item) {
        categoryRepo.save(item);
        return "redirect:/admin/category/index";
    }

    // Cập nhật
    @PostMapping("/update")
    public String update(Category item) {
        categoryRepo.save(item);
        return "redirect:/admin/category/edit/" + item.getId();
    }

    // Xóa
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        categoryRepo.deleteById(id);
        return "redirect:/admin/category/index";
    }
}