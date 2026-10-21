package com.polystore.interceptors;

import com.polystore.entities.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Account user = (Account) session.getAttribute("user");
        
        String uri = request.getRequestURI();

        // 1. Chặn người dùng chưa đăng nhập
        if (user == null) {
            // Lưu lại đường dẫn đang cố truy cập để lát đăng nhập xong quay lại
            session.setAttribute("security-uri", uri);
            // Bắt buộc chuyển hướng ra form đăng nhập[cite: 3]
            response.sendRedirect("/auth/login?error=Login-Required");
            return false;
        }

        // 2. Chặn người dùng thường cố tình vào khu vực Quản trị (Admin)[cite: 3]
        if (uri.startsWith("/admin") && !user.getAdmin()) {
            response.sendRedirect("/auth/login?error=Access-Denied");
            return false;
        }

        // Hợp lệ, cho phép đi tiếp vào Controller
        return true;
    }
}