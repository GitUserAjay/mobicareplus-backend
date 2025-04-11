package com.MobiCarePlus.in.MobiCarePlus.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MobiCarePlus.in.MobiCarePlus.entity.ShopUser;
import com.MobiCarePlus.in.MobiCarePlus.request.ReviewDTO;
import com.MobiCarePlus.in.MobiCarePlus.service.IReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private IReviewService shopService;

    @GetMapping("/{shopId}")
    public ResponseEntity<ShopUser> getShopById(@PathVariable Long shopId) {
        Optional<ShopUser> shop = shopService.getShopById(shopId);
        return shop.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{shopId}/reviews")
    public ResponseEntity<List<ReviewDTO>> getReviewsByShopId(@PathVariable Long shopId) {
        List<ReviewDTO> reviews = shopService.getReviewsByShopId(shopId);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/{shopId}/reviews")
    public ResponseEntity<ReviewDTO> addReview(@PathVariable Long shopId, @RequestBody ReviewDTO reviewDTO) {
        reviewDTO.setShopUserId(shopId);
        ReviewDTO savedReview = shopService.addReview(reviewDTO);
        return ResponseEntity.status(201).body(savedReview);
    }
}
