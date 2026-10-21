package com.polystore.controllers;

import com.polystore.repositories.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/report")
public class ReportAController {

    @Autowired
    OrderDetailRepository orderDetailRepo;

    // Hiển thị báo cáo doanh thu theo loại hàng (@/admin/report/revenue => revenue.html)[cite: 5]
    @GetMapping("/revenue")
    public String revenue(Model model) {
        model.addAttribute("items", orderDetailRepo.getRevenueByCategory());
        return "admin/revenue";
    }

    // Hiển thị top 10 khách hàng VIP (@/admin/report/vip => vip.html)[cite: 5]
    @GetMapping("/vip")
    public String vip(Model model) {
        // Cấu hình chỉ lấy trang số 0, số lượng 10 phần tử để ra TOP 10[cite: 3]
        Pageable topTen = PageRequest.of(0, 10); 
        model.addAttribute("items", orderDetailRepo.getVIPCustomers(topTen).getContent());
        return "admin/vip";
    }
}