package com.polystore.repositories;

import com.polystore.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Lấy danh sách đơn hàng của một người dùng cụ thể
    @Query("SELECT o FROM Order o WHERE o.account.username = ?1")
    List<Order> findByUsername(String username);
}