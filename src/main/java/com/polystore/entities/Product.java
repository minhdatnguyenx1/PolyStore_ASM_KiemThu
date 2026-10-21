package com.polystore.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, columnDefinition = "nvarchar(100)")
    private String name;
    
    private String image = "product.png";
    
    @Column(nullable = false)
    private Double price;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "CreateDate")
    private Date createDate = new Date();
    
    private Boolean available = true;

    @ManyToOne
    @JoinColumn(name = "CategoryId")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;
}