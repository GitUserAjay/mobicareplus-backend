package com.MobiCarePlus.in.MobiCarePlus.service;

import java.util.List;
import java.util.Optional;

import com.MobiCarePlus.in.MobiCarePlus.entity.ShopUser;
import com.MobiCarePlus.in.MobiCarePlus.request.ReviewDTO;

public interface IReviewService {

	Optional<ShopUser> getShopById(Long shopId);

	List<ReviewDTO> getReviewsByShopId(Long shopId);

	ReviewDTO addReview(ReviewDTO reviewDTO);

}
