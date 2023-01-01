package pg.search.store.infrastructure.notification;

import lombok.experimental.UtilityClass;

import pg.search.store.domain.notification.NotificationData;
import pg.search.store.domain.notification.NotificationType;
import pg.search.store.infrastructure.common.CommonData;
import pg.search.store.infrastructure.product.ProductEntity;
import pg.search.store.infrastructure.user.UserEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class NotificationMapper {
    public NotificationData toNotificationData(final NotificationEntity entity) {
        ProductEntity product = entity.getProduct();
        return NotificationData.builder()
                .notificationId(entity.getNotificationId())
                .notificationTime(entity.getNotificationTime().format(DateTimeFormatter.ofPattern(CommonData.DATE_FORMAT)))
                .productId(product.getProductId())
                .productType(product.getProductType().toString())
                .isRead(entity.getIsRead())
                .message(entity.getMessage())
                .type(entity.getType().name())
                .build();
    }

    public NotificationEntity toNewProductNotification(final UserEntity userEntity, final ProductEntity productEntity) {
        return NotificationEntity.builder()
                .notificationTime(LocalDateTime.now())
                .isRead(false)
                .message(String.format("Product %s:%s has been added to the store", productEntity.getTitle(),
                        productEntity.getProductType()))
                .product(productEntity)
                .type(NotificationType.NEW_PRODUCT)
                .user(userEntity)
                .build();
    }

    public NotificationEntity toNewReviewNotification(final UserEntity userEntity, final ProductEntity productEntity,
                                                      final NotificationType newReviewType) {
        return NotificationEntity.builder()
                .notificationTime(LocalDateTime.now())
                .isRead(false)
                .message(String.format("Product %s:%s that you %s has new review", productEntity.getTitle(),
                        productEntity.getProductType(), newReviewType.equals(NotificationType.FOLLOWED_NEW_REVIEW) ? "follow" : "marked"))
                .product(productEntity)
                .type(newReviewType)
                .user(userEntity)
                .build();
    }

    public NotificationEntity toProductBecomeAvailableNotification(final UserEntity userEntity, final ProductEntity productEntity,
                                                                   final NotificationType newReviewType) {
        return NotificationEntity.builder()
                .notificationTime(LocalDateTime.now())
                .isRead(false)
                .message(String.format("Product %s:%s that you %s has acquired an offer and become available", productEntity.getTitle(),
                        productEntity.getProductType(), newReviewType.equals(NotificationType.FOLLOWED_NEW_REVIEW) ? "follow" : "marked"))
                .product(productEntity)
                .type(newReviewType)
                .user(userEntity)
                .build();
    }
}
