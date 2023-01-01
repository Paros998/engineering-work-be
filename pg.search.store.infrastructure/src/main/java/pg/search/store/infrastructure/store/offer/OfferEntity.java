package pg.search.store.infrastructure.store.offer;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import pg.search.store.infrastructure.product.ProductEntity;
import pg.search.store.infrastructure.store.StoreEntity;

import javax.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "store_offers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferEntity implements Serializable {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(
            nullable = false,
            updatable = false
    )

    private UUID offerId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private ProductEntity product;

    private String offerWebsite;

    private Boolean hasFreeShipping;

    private Double price;

    private String currency;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonBackReference
    private StoreEntity store;
}
