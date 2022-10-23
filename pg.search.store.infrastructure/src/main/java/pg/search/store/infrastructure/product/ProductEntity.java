package pg.search.store.infrastructure.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
@AllArgsConstructor
@Getter
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

    @Column(
            unique = true
    )
    protected String title;

    protected String producentCode;

    @ManyToOne
    @JoinColumn(name = "file_id")
    @JsonBackReference
    protected FileEntity productPhoto;

    @Column(
            length = 2000
    )
    protected String producentSite;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private List<StoreOfferEntity> onlineOfferList;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ReviewEntity> reviews;

    public ProductEntity setProductType(ProductType productType) {
        this.productType = productType;
        return this;
    }

    public ProductEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public ProductEntity setProducentCode(String producentCode) {
        this.producentCode = producentCode;
        return this;
    }

    public ProductEntity setProductPhoto(FileEntity productPhoto) {
        this.productPhoto = productPhoto;
        return this;
    }

    public ProductEntity setProducentSite(String producentSite) {
        this.producentSite = producentSite;
        return this;
    }
}