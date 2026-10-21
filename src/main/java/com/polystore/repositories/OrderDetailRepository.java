package com.polystore.repositories;

import com.polystore.entities.OrderDetail;
import com.polystore.models.ReportCategory;
import com.polystore.models.ReportVIP;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    
    // Lấy chi tiết các sản phẩm trong 1 đơn hàng (Đã có từ trước)
    @Query("SELECT d FROM OrderDetail d WHERE d.order.id = ?1")
    List<OrderDetail> findByOrderId(Long orderId);

    // 1. Thống kê doanh thu theo loại hàng[cite: 2]
    @Query("SELECT new com.polystore.models.ReportCategory(d.product.category.name, sum(d.price * d.quantity), sum(cast(d.quantity as long)), max(d.price), min(d.price), avg(d.price)) " +
           "FROM OrderDetail d GROUP BY d.product.category.name")
    List<ReportCategory> getRevenueByCategory();

    // 2. Thống kê khách hàng VIP sắp xếp theo tổng tiền giảm dần[cite: 3]
    @Query("SELECT new com.polystore.models.ReportVIP(d.order.account.fullname, sum(d.price * d.quantity), min(d.order.createDate), max(d.order.createDate)) " +
           "FROM OrderDetail d GROUP BY d.order.account.fullname ORDER BY sum(d.price * d.quantity) DESC")
    Page<ReportVIP> getVIPCustomers(Pageable pageable);
}