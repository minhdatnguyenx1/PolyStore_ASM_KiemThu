package com.polystore.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Username")
    private Account account;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreateDate")
    private Date createDate = new Date();

    @Column(nullable = false, columnDefinition = "nvarchar(255)")
    private String address;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
}