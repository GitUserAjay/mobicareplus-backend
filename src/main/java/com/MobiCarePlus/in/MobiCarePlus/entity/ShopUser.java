package com.MobiCarePlus.in.MobiCarePlus.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "shop_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String shopName;
    private String ownerName;
    private String businessType;
    private String gstNumber;
    private String panNumber;
    private boolean isVerified;
    private boolean isActive=false;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Lob
    private String image; 
    private String websiteUrl;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo userInfo;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
        this.isVerified = false;
        this.isActive = false;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

