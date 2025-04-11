package com.MobiCarePlus.in.MobiCarePlus.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name = "shop_services")
public class ShopService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String category;
    private String description;
    private Double price;
    private Integer discount;
    private Integer estimatedTime;
    private String contact;
    private String location;
    private boolean available;

    @Lob
    private String imageBase64;

    private String imageName;

    @ManyToOne
    @JoinColumn(name = "shop_user_id", nullable = false)
    private ShopUser shopUser;
}
