package com.polystore.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportCategory implements Serializable {
    private String categoryName;
    private Double sumPrice;    // Tổng doanh thu
    private Long sumQty;        // Tổng số lượng
    private Double maxPrice;    // Giá cao nhất
    private Double minPrice;    // Giá thấp nhất
    private Double avgPrice;    // Giá trung bình
}