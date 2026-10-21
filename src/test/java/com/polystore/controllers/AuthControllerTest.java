package com.polystore.controllers;

import com.polystore.entities.Account;
import com.polystore.repositories.AccountRepository;
import com.polystore.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class AuthControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private AuthController authController;

    // Đã xác nhận trên máy bạn @MockitoBean hoạt động tốt
    @MockitoBean
    private AccountRepository accountRepo;

    @MockitoBean
    private SessionService session;

    // Cài đặt thủ công để lách lỗi của Eclipse
    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testLoginSuccess() throws Exception {
        Account mockUser = new Account();
        mockUser.setUsername("datnm");
        mockUser.setPassword("123456");

        Mockito.when(accountRepo.findById("datnm")).thenReturn(Optional.of(mockUser));

        mockMvc.perform(post("/auth/login")
                .param("username", "datnm")
                .param("password", "123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home/index"));
    }

    @Test
    public void testLoginWrongPassword() throws Exception {
        Account mockUser = new Account();
        mockUser.setUsername("datnm");
        mockUser.setPassword("123456");

        Mockito.when(accountRepo.findById("datnm")).thenReturn(Optional.of(mockUser));

        mockMvc.perform(post("/auth/login")
                .param("username", "datnm")
                .param("password", "sai_pass"))
                .andExpect(status().isOk())
                .andExpect(view().name("account/login"))
                .andExpect(model().attributeExists("message"));
    }

    @Test
    public void testLoginUserNotFound() throws Exception {
        Mockito.when(accountRepo.findById("user_ao")).thenReturn(Optional.empty());

        mockMvc.perform(post("/auth/login")
                .param("username", "user_ao")
                .param("password", "123"))
                .andExpect(status().isOk())
                .andExpect(view().name("account/login"))
                .andExpect(model().attributeExists("message"));
    }
 // TC_LOGIN_04: Bỏ trống các trường (Submit chuỗi rỗng)
    @Test
    public void testLoginEmptyFields() throws Exception {
        // Giả lập backend không tìm thấy user nào có username là chuỗi rỗng
        Mockito.when(accountRepo.findById("")).thenReturn(Optional.empty());

        mockMvc.perform(post("/auth/login")
                .param("username", "")
                .param("password", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("account/login"))
                .andExpect(model().attributeExists("message"));
    }

    // TC_LOGIN_05: Đăng nhập thành công và quay lại trang bị chặn (Intercepter Security)
    @Test
    public void testLoginSuccessWithSecurityUri() throws Exception {
        Account mockUser = new Account();
        mockUser.setUsername("datnm");
        mockUser.setPassword("123456");

        Mockito.when(accountRepo.findById("datnm")).thenReturn(Optional.of(mockUser));
        
        // Giả lập Session đang lưu sẵn đường dẫn trang bị chặn trước đó (ví dụ: /order/checkout)
        Mockito.when(session.get("security-uri")).thenReturn("/order/checkout");

        mockMvc.perform(post("/auth/login")
                .param("username", "datnm")
                .param("password", "123456"))
                .andExpect(status().is3xxRedirection())
                // Kì vọng sẽ chuyển hướng về đúng trang bị chặn thay vì trang home
                .andExpect(redirectedUrl("/order/checkout")); 
    }
}