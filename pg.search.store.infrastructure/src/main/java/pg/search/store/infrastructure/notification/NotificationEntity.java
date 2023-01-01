package pg.search.store.infrastructure.notification;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import pg.search.store.domain.notification.NotificationType;
import pg.search.store.infrastructure.product.ProductEntity;
import pg.search.store.infrastructure.user.UserEntity;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationEntity implements Serializable {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(
            nullable = false,
            updatable = false
    )
    private UUID notificationId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonManagedReference
    private ProductEntity product;

    private LocalDateTime notificationTime;

    private String message;

    private Boolean isRead;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;
}