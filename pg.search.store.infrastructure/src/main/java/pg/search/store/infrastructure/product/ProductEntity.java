package pg.search.store.infrastructure.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import pg.lib.awsfiles.entity.FileEntity;

import pg.search.store.domain.product.ProductType;
import pg.search.store.infrastructure.review.ReviewEntity;
import pg.search.store.infrastructure.store.offer.StoreOfferEntity;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ProductEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            nullable = false,
            updatable = false
    )
    protected UUID productId;
    @Enumerated(EnumType.STRING)
    protected ProductType productType;
    protected String title;
    private String producentCode;
    @ManyToOne
    @JoinColumn(name = "file_id")
    @JsonBackReference
    private FileEntity productPhoto;

    @Column(
            length = 2000
    )
    private String producentSite;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private List<StoreOfferEntity> onlineOfferList;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ReviewEntity> reviews;
}