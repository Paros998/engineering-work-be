package pg.search.store.infrastructure.history;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import pg.search.store.domain.history.Action;
import pg.search.store.infrastructure.product.ProductEntity;
import pg.search.store.infrastructure.user.UserEntity;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryEntity implements Serializable {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(
            nullable = false,
            updatable = false
    )
    private UUID historyId;

    @Enumerated(EnumType.STRING)
    private Action action;

    private String content;

    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private ProductEntity product;
}