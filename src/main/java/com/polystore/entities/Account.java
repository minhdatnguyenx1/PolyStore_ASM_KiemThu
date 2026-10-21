package com.polystore.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Accounts")
public class Account implements Serializable {
    @Id
    @Column(length = 50)
    private String username;
    
    @Column(nullable = false, length = 100)
    private String password;
    
    @Column(nullable = false, columnDefinition = "nvarchar(100)")
    private String fullname;
    
    @Column(nullable = false, length = 100)
    private String email;
    
    private String photo = "default.png";
    private Boolean activated = true;
    private Boolean admin = false;

    @OneToMany(mappedBy = "account")
    private List<Order> orders;
}