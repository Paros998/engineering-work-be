package pg.search.store.infrastructure.product.suggested;

import lombok.*;

import pg.search.store.domain.product.ProductType;

import javax.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "suggested_products")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SuggestedProductEntity {
    @Enumerated(EnumType.STRING)
    protected ProductType productType;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            nullable = false,
            updatable = false
    )
    private UUID id;
    private UUID productId;
}