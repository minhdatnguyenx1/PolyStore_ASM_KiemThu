package com.polystore.repositories;

import com.polystore.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Tìm sản phẩm theo mã danh mục (dùng cho trang cửa hàng)
    @Query("SELECT p FROM Product p WHERE p.category.id = ?1")
    List<Product> findByCategoryId(String categoryId);

    // Dành cho phân trang
    @Query("SELECT p FROM Product p WHERE p.category.id = ?1")
    Page<Product> findByCategoryId(String categoryId, Pageable pageable);
}