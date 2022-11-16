package pg.search.store.infrastructure.review;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.search.store.domain.review.Review;
import pg.search.store.infrastructure.product.ProductService;
import pg.search.store.infrastructure.user.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class ReviewMapper {
    private final ProductService productService;
    private final UserService userService;

    public Review toReview(final ReviewEntity review) {
        return Review.builder()
                .id(review.getReviewId())
                .productId(review.getProduct().getProductId())
                .opinion(review.getOpinion())
                .score(review.getScore())
                .username(review.getUser().getUsername())
                .userId(review.getUser().getUserId())
                .reviewDate(review.getReviewDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .isCensored(review.getIsCensored())
                .build();
    }

    public ReviewEntity createReviewEntity(final Review review) {
        return ReviewEntity.builder()
                .product(productService.getEntityById(review.getProductId()))
                .opinion(review.getOpinion())
                .score(review.getScore())
                .user(userService.getUser(review.getUserId()))
                .reviewDate(LocalDateTime.now())
                .isCensored(false)
                .build();
    }

    public ReviewEntity toReviewEntity(final Review review) {
        return ReviewEntity.builder()
                .reviewId(review.getId())
                .product(productService.getEntityById(review.getProductId()))
                .opinion(review.getOpinion())
                .score(review.getScore())
                .user(userService.getUser(review.getUserId()))
                .reviewDate(LocalDateTime.parse(review.getReviewDate()))
                .isCensored(review.getIsCensored())
                .build();
    }
}