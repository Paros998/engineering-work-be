package pg.search.store.domain.notification;

public enum NotificationType {
    NEW_PRODUCT,

    FOLLOWED_AVAILABLE,
    //TODO fix bug with marked and followed available
    MARKED_AVAILABLE,

    FOLLOWED_NEW_REVIEW,
    MARKED_NEW_REVIEW,

    FOLLOWED_LOWER_PRICE,
    MARKED_LOWER_PRICE,
}
