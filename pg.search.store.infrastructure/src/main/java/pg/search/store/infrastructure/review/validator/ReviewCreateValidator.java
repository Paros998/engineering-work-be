package pg.search.store.infrastructure.review.validator;

import lombok.AllArgsConstructor;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import pg.search.store.domain.review.Review;
import pg.search.store.infrastructure.common.validation.Validator;
import pg.search.store.infrastructure.product.ProductRepository;
import pg.search.store.infrastructure.review.ReviewRepository;
import pg.search.store.infrastructure.user.UserRepository;

@Service
@AllArgsConstructor
public class ReviewCreateValidator implements Validator<Review> {
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public void validate(final Review review) throws NestedRuntimeException {
        if (!userRepository.existsById(review.getUserId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Review Create | User with id: %s not exists".formatted(review.getUserId()));

        if (!productRepository.existsById(review.getProductId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Review Create | Product with id: %s not exists".formatted(review.getProductId()));

        if (reviewRepository.existsByUserIdAndProductId(review.getUserId(), review.getProductId()))
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Review Create | Review with productId: %s and userId: %s already exists".formatted(review.getUserId(),
                            review.getProductId())
            );

    }
}