package com.MobiCarePlus.in.MobiCarePlus.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.MobiCarePlus.in.MobiCarePlus.entity.Review;
import com.MobiCarePlus.in.MobiCarePlus.entity.ShopUser;
import com.MobiCarePlus.in.MobiCarePlus.entity.UserInfo;
import com.MobiCarePlus.in.MobiCarePlus.repository.ReviewRepository;
import com.MobiCarePlus.in.MobiCarePlus.repository.ShopUserRepository;
import com.MobiCarePlus.in.MobiCarePlus.repository.UserInfoRepository;
import com.MobiCarePlus.in.MobiCarePlus.request.ReviewDTO;
import com.MobiCarePlus.in.MobiCarePlus.service.IReviewService;

@Service
public class ReviewServiceImpl implements IReviewService {
	@Autowired
	private ShopUserRepository shopUserRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	public Optional<ShopUser> getShopById(Long shopId) {
		return shopUserRepository.findById(shopId);
	}

	@Override
	public List<ReviewDTO> getReviewsByShopId(Long shopId) {
		return reviewRepository.findByShopUserId(shopId).stream().map(review -> {
			ReviewDTO dto = new ReviewDTO();
			dto.setId(review.getId());
			dto.setRating(review.getRating());
			dto.setComment(review.getComment());
			dto.setUser(review.getUser().getName());
			dto.setShopUserId(review.getShopUser().getId());
			dto.setUserId(review.getUser().getId());
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public ReviewDTO addReview(ReviewDTO reviewDTO) {
	    Optional<ShopUser> shopUserOpt = shopUserRepository.findById(reviewDTO.getShopUserId());
	    Optional<UserInfo> userInfoOpt = reviewDTO.getUserId() != null 
	        ? userInfoRepository.findById(reviewDTO.getUserId()) 
	        : Optional.empty();

	    if (shopUserOpt.isEmpty()) {
	        throw new RuntimeException("ShopUser not found");
	    }

	    Review review = new Review();
	    review.setRating(reviewDTO.getRating());
	    review.setComment(reviewDTO.getComment());
	    review.setShopUser(shopUserOpt.get());
	    review.setUser(userInfoOpt.orElse(null)); // Allow null for guest

	    Review savedReview = reviewRepository.save(review);

	    ReviewDTO savedDTO = new ReviewDTO();
	    savedDTO.setId(savedReview.getId());
	    savedDTO.setRating(savedReview.getRating());
	    savedDTO.setComment(savedReview.getComment());
	    savedDTO.setUser(savedReview.getUser() != null ? savedReview.getUser().getName() : "Guest");
	    savedDTO.setShopUserId(savedReview.getShopUser().getId());
	    savedDTO.setUserId(savedReview.getUser() != null ? savedReview.getUser().getId() : null);

	    return savedDTO;
	}
}
