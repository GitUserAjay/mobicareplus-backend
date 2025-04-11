package com.MobiCarePlus.in.MobiCarePlus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String category;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Lob
    private String image;

    @ManyToOne
    @JoinColumn(name = "shop_user_id", nullable = false)
    private ShopUser shopUser;

    @Column(nullable = false)
    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
