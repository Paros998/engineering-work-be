package pg.search.store.infrastructure.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import pg.search.store.domain.product.ProductType;

import javax.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            nullable = false,
            updatable = false
    )
    protected UUID productId;

    @Enumerated(EnumType.STRING)
    protected ProductType productType;
}