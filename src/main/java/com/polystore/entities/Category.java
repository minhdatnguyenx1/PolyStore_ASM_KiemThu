package com.polystore.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Categories")
public class Category implements Serializable {
    @Id
    @Column(length = 4)
    private String id;
    
    @Column(nullable = false, columnDefinition = "nvarchar(50)")
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}