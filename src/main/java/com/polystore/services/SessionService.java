package com.polystore.services;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {
    
    @Autowired
    private HttpSession session;

    // Đọc giá trị của attribute trong session
    @SuppressWarnings("unchecked")
    public <T> T get(String name) {
        return (T) session.getAttribute(name);
    }

    // Đọc giá trị của attribute trong session, nếu không có thì trả về giá trị mặc định
    public <T> T get(String name, T defaultValue) {
        T value = get(name);
        return value != null ? value : defaultValue;
    }

    // Thay đổi hoặc tạo mới attribute trong session
    public void set(String name, Object value) {
        session.setAttribute(name, value);
    }

    // Xóa attribute trong session
    public void remove(String name) {
        session.removeAttribute(name);
    }
}