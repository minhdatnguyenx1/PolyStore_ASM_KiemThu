package com.polystore.controllers;

import com.polystore.entities.Order;
import com.polystore.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/order")
public class OrderAController {

    @Autowired
    OrderRepository orderRepo;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("item", new Order());
        model.addAttribute("items", orderRepo.findAll());
        return "admin/order";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("item", orderRepo.findById(id).orElse(new Order()));
        model.addAttribute("items", orderRepo.findAll());
        return "admin/order";
    }

    @PostMapping("/update")
    public String update(Order item) {
        orderRepo.save(item);
        return "redirect:/admin/order/edit/" + item.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        orderRepo.deleteById(id);
        return "redirect:/admin/order/index";
    }
}