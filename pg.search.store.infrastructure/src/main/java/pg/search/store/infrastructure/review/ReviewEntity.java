package pg.search.store.infrastructure.review;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import pg.search.store.infrastructure.product.ProductEntity;
import pg.search.store.infrastructure.user.UserEntity;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewEntity implements Serializable {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(
            nullable = false,
            updatable = false
    )
    private UUID reviewId;

    private String opinion;

    private Boolean isCensored;

    private Integer score;

    private LocalDateTime reviewDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private ProductEntity product;
}