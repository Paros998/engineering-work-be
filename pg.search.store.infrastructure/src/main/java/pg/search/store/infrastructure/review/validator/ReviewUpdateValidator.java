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
public class ReviewUpdateValidator implements Validator<Review> {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public void validate(final Review review) throws NestedRuntimeException {
        if (!reviewRepository.existsById(review.getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review with id: %s not exists".formatted(review.getId()));

        if (!userRepository.existsById(review.getUserId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Review Update | User with id: %s not exists".formatted(review.getUserId()));

        if (!productRepository.existsById(review.getProductId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Review Update | Product with id: %s not exists".formatted(review.getProductId()));

    }
}