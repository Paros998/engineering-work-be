package pg.search.store.infrastructure.notification;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import pg.search.store.domain.notification.NotificationType;
import pg.search.store.infrastructure.product.ProductEntity;
import pg.search.store.infrastructure.product.ProductRepository;
import pg.search.store.infrastructure.user.UserEntity;
import pg.search.store.infrastructure.user.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executor;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private static final String INVALID_NOTIFICATION = "Notification Type {} is invalid in this usecase";
    private final NotificationRepository notificationRepository;
    private final Executor executor;
    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private static UUID convertToProductId(final String data) {
        try {
            return UUID.fromString(data);
        } catch (Exception e) {
            log.error("Invalid product Id: {}", data);
            return null;
        }
    }

    public void fireNotifications(final NotificationType type, final String data) {
        switch (type) {
            case NEW_PRODUCT -> executor.execute(() -> fireNewProductNotifications(data));

            case FOLLOWED_NEW_REVIEW -> executor.execute(() -> fireNewReview(NotificationType.FOLLOWED_NEW_REVIEW, data));
            case MARKED_NEW_REVIEW -> executor.execute(() -> fireNewReview(NotificationType.MARKED_NEW_REVIEW, data));

            case FOLLOWED_AVAILABLE -> executor.execute(() -> fireProductBecomeAvailable(NotificationType.FOLLOWED_AVAILABLE, data));
            case MARKED_AVAILABLE -> executor.execute(() -> fireProductBecomeAvailable(NotificationType.MARKED_AVAILABLE, data));

            case FOLLOWED_LOWER_PRICE -> executor.execute(() -> fireProductHasLowerPrice(NotificationType.FOLLOWED_LOWER_PRICE, data));
            case MARKED_LOWER_PRICE -> executor.execute(() -> fireProductHasLowerPrice(NotificationType.MARKED_LOWER_PRICE, data));

            default -> log.error("NotificationType {} not supported", type);
        }
    }

    public void readNotification(final UUID notificationId) {
        notificationRepository.setIsReadTrue(notificationId);
    }


    private List<UserEntity> fetchApplicableUsers(final NotificationType notificationType, final UUID productId) {
        switch (notificationType) {
            case NEW_PRODUCT -> {
                return userRepository.findActiveUsersWithNewProductSubscription();
            }

            case FOLLOWED_NEW_REVIEW -> {
                return userRepository.findActiveUsersWithFollowedProductNewReviewSubscriptionThatFollow(productId);
            }
            case MARKED_NEW_REVIEW -> {
                return userRepository.findActiveUsersWithMarkedProductNewReviewSubscriptionThatMarked(productId);
            }

            case FOLLOWED_AVAILABLE -> {
                return userRepository.findActiveUsersWithFollowedProductAvailableSubscriptionThatFollow(productId);
            }
            case MARKED_AVAILABLE -> {
                return userRepository.findActiveUsersWithMarkedProductAvailableSubscriptionThatMarked(productId);
            }

            case FOLLOWED_LOWER_PRICE -> {
                return userRepository.findActiveUsersWithFollowedProductLowerPriceOfferSubscriptionThatFollow(productId);
            }
            case MARKED_LOWER_PRICE -> {
                return userRepository.findActiveUsersWithMarkedProductLowerPriceOfferSubscriptionThatMarked(productId);
            }

            default -> {
                log.error("NotificationType {} not supported", notificationType);
                return Collections.emptyList();
            }
        }
    }

    private void fireNewProductNotifications(final String data) {
        UUID productId = convertToProductId(data);
        if (productId == null) return;

        Optional<ProductEntity> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            log.warn("Tried to fire notifications for new product on product that not exists : {}", productId);
            return;
        }

        final var users = fetchApplicableUsers(NotificationType.NEW_PRODUCT, null);

        users.forEach(userEntity -> notificationRepository.save(NotificationMapper.toNewProductNotification(userEntity, product.get())));
    }

    private void fireNewReview(final NotificationType notificationType, final String data) {
        UUID productId = convertToProductId(data);
        if (productId == null) return;

        Optional<ProductEntity> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            log.warn("Tried to fire notifications for new review on product that not exists : {}", productId);
            return;
        }

        if (!(notificationType.equals(NotificationType.FOLLOWED_NEW_REVIEW) || notificationType.equals(NotificationType.MARKED_NEW_REVIEW))) {
            log.error(INVALID_NOTIFICATION, notificationType);
            return;
        }

        List<UserEntity> users = fetchApplicableUsers(notificationType, productId);

        if (users.isEmpty()) {
            log.info("There are no users that {} product with id {} , so no notifications were fired",
                    notificationType.equals(NotificationType.FOLLOWED_NEW_REVIEW) ? "follow" : "have marked",
                    productId);
            return;
        }

        users.forEach(userEntity -> notificationRepository.save(NotificationMapper.toNewReviewNotification(userEntity, product.get(), notificationType)));
    }

    private void fireProductBecomeAvailable(final NotificationType notificationType, final String data) {
        UUID productId = convertToProductId(data);
        if (productId == null) return;

        Optional<ProductEntity> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            log.warn("Tried to fire notifications for product become available on product that not exists : {}", productId);
            return;
        }

        if (!(notificationType.equals(NotificationType.FOLLOWED_AVAILABLE) || notificationType.equals(NotificationType.MARKED_AVAILABLE))) {
            log.error(INVALID_NOTIFICATION, notificationType);
            return;
        }

        List<UserEntity> users = fetchApplicableUsers(notificationType, productId);

        if (users.isEmpty()) {
            log.info("There are no users that {} product with id {} , so no notifications were fired",
                    notificationType.equals(NotificationType.FOLLOWED_AVAILABLE) ? "follow" : "have marked",
                    productId);
            return;
        }

        users.forEach(userEntity -> notificationRepository.save(NotificationMapper.toProductBecomeAvailableNotification(userEntity,
                product.get(), notificationType)));
    }

    private void fireProductHasLowerPrice(final NotificationType notificationType, final String data) {
        UUID productId = convertToProductId(data);
        if (productId == null) return;

        Optional<ProductEntity> product = productRepository.findById(productId);

        if (product.isEmpty()) {
            log.warn("Tried to fire notifications for product has lower price offer on product that not exists : {}", productId);
            return;
        }

        if (!(notificationType.equals(NotificationType.FOLLOWED_LOWER_PRICE) || notificationType.equals(NotificationType.MARKED_LOWER_PRICE))) {
            log.error(INVALID_NOTIFICATION, notificationType);
            return;
        }

        List<UserEntity> users = fetchApplicableUsers(notificationType, productId);

        if (users.isEmpty()) {
            log.info("There are no users that {} product with id {} , so no notifications were fired",
                    notificationType.equals(NotificationType.FOLLOWED_LOWER_PRICE) ? "follow" : "have marked",
                    productId);
            return;
        }

        users.forEach(userEntity -> notificationRepository.save(NotificationMapper.toProductBecomeAvailableNotification(userEntity,
                product.get(), notificationType)));
    }

}