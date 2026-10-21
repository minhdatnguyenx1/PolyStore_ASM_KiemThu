package com.polystore.controllers;

import com.polystore.entities.Account;
import com.polystore.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/account")
public class AccountAController {

    @Autowired
    AccountRepository accountRepo;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("item", new Account());
        model.addAttribute("items", accountRepo.findAll());
        return "admin/account";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") String username) {
        model.addAttribute("item", accountRepo.findById(username).orElse(new Account()));
        model.addAttribute("items", accountRepo.findAll());
        return "admin/account";
    }

    @PostMapping("/create")
    public String create(Account item) {
        accountRepo.save(item);
        return "redirect:/admin/account/index";
    }

    @PostMapping("/update")
    public String update(Account item) {
        accountRepo.save(item);
        return "redirect:/admin/account/edit/" + item.getUsername();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String username) {
        accountRepo.deleteById(username);
        return "redirect:/admin/account/index";
    }
}