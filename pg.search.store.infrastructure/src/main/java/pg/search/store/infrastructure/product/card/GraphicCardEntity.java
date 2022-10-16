package pg.search.store.infrastructure.product.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import pg.lib.awsfiles.entity.FileEntity;

import pg.search.store.domain.product.card.MemoryType;
import pg.search.store.domain.product.card.PciType;
import pg.search.store.domain.product.card.Technology;
import pg.search.store.infrastructure.product.ProductEntity;
import pg.search.store.infrastructure.review.ReviewEntity;
import pg.search.store.infrastructure.store.offer.StoreOfferEntity;

import javax.persistence.*;

import java.util.List;

@Entity
@SecondaryTable(name = "cards")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class GraphicCardEntity extends ProductEntity {
    private String producentCode;

    @Enumerated(EnumType.STRING)
    private Technology technology;

    @Column(
            length = 2000
    )
    private String producentSite;

    private Boolean rtxSupport;

    @ElementCollection(fetch = FetchType.LAZY, targetClass = String.class)
    private List<String> supportedLibraries;

    private Integer cudaCoresAmount;

    private Integer powerConsumption;

    private Integer recommendedPower;

    private String cooling;

    private String powerConnector;

    private String coreClock;

    private Integer memoryAmount;

    @Enumerated(EnumType.STRING)
    private MemoryType typeOfMemory;

    @Enumerated(EnumType.STRING)
    private PciType typeOfPciConnector;

    private Integer memoryBus;

    @ManyToOne
    @JoinColumn(name = "file_id")
    @JsonBackReference
    private FileEntity cardPhoto;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private List<StoreOfferEntity> onlineOfferList;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ReviewEntity> reviews;
}