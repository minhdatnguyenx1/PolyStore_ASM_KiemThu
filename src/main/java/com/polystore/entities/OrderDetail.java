package com.polystore.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OrderDetails")
public class OrderDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "OrderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "ProductId")
    private Product product;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;
}