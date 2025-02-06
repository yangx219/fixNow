package com.pc3ryangzhang.fixnow.mapper;

import com.pc3ryangzhang.fixnow.entity.Review;
import java.util.List;

public interface ReviewMapper {
    Review getReviewById(Integer reviewId);
    List<Review> getReviewsByRequestId(Integer requestId);
    List<Review> getReviewsByReviewerId(Integer reviewerId);
    List<Review> getReviewsByRevieweeId(Integer revieweeId);
    int insertReview(Review review);
    int updateReview(Review review);
    int deleteReview(Integer reviewId);
}
