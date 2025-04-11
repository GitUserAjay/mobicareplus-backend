package com.MobiCarePlus.in.MobiCarePlus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MobiCarePlus.in.MobiCarePlus.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByShopUserId(Long shopUserId);
}