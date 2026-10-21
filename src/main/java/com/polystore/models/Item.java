package com.polystore.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private Integer id;
    private String name;
    private String image;
    private Double price;
    private int qty = 1; // Số lượng mặc định là 1 khi thêm vào giỏ
}