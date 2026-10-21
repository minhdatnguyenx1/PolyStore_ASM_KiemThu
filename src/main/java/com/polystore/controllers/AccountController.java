package com.polystore.controllers;

import com.polystore.entities.Account;
import com.polystore.repositories.AccountRepository;
import com.polystore.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountRepository accountRepo;
    
    @Autowired
    SessionService session;

    // Đăng ký tài khoản (@/account/sign-up => sign-up.html)
    @GetMapping("/sign-up")
    public String signUpForm() {
        return "account/sign-up"; 
    }

    @PostMapping("/sign-up")
    public String signUp(Model model, Account account) {
        accountRepo.save(account);
        model.addAttribute("message", "Đăng ký thành công!");
        return "account/sign-up";
    }

    // Cập nhật thông tin tài khoản (@/account/edit-profile => edit-profile.html)
    @GetMapping("/edit-profile")
    public String editProfileForm(Model model) {
        Account user = session.get("user");
        model.addAttribute("item", user);
        return "account/edit-profile"; 
    }

    @PostMapping("/edit-profile")
    public String editProfile(Model model, Account account) {
        accountRepo.save(account);
        session.set("user", account); // Cập nhật lại session
        model.addAttribute("message", "Cập nhật thành công!");
        return "account/edit-profile";
    }

    // Quên mật khẩu (@/account/forgot-password => forgot-password.html)
    @GetMapping("/forgot-password")
    public String forgotPasswordForm() {
        return "account/forgot-password"; 
    }

    // Đổi mật khẩu (@/account/change-password => change-password.html)
    @GetMapping("/change-password")
    public String changePasswordForm() {
        return "account/change-password"; 
    }
}