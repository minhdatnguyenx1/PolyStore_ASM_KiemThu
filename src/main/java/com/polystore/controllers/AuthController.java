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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    AccountRepository accountRepo;

    @Autowired
    SessionService session;

    // Hiển thị form đăng nhập (@/auth/login => login.html)
    @GetMapping("/login")
    public String loginForm() {
        return "account/login"; 
    }

    // Xử lý khi nhấn nút Đăng nhập
    @PostMapping("/login")
    public String login(Model model, @RequestParam("username") String username, @RequestParam("password") String password) {
        Account user = accountRepo.findById(username).orElse(null);
        if (user != null && user.getPassword().equals(password)) {
            session.set("user", user);
            
            // Lấy lại URL đã lưu trong Interceptor
            String securityUri = session.get("security-uri");
            if (securityUri != null) {
                session.remove("security-uri"); // Xóa đi sau khi xài xong
                return "redirect:" + securityUri; // Trở về trang bị chặn[cite: 3]
            }
            
            return "redirect:/home/index"; 
        }
        model.addAttribute("message", "Sai tên đăng nhập hoặc mật khẩu!");
        return "account/login";
    }

    // Đăng xuất
    @GetMapping("/logout")
    public String logout() {
        session.remove("user");
        return "redirect:/home/index";
    }
}