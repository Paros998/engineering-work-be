package pg.search.store.infrastructure.store;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import pg.lib.awsfiles.entity.FileEntity;

import pg.search.store.infrastructure.store.offer.StoreOfferEntity;

import javax.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "stores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(
            nullable = false,
            updatable = false
    )

    private UUID storeId;

    private String name;

    private String phone;

    private String website;

    @OneToOne
    @JoinColumn(name = "file_id")
    @JsonIgnore
    private FileEntity storePhoto;

    private Integer ratingCount;

    private Float ratingScore;

    @OneToMany(mappedBy = "store")
    @JsonManagedReference
    private List<StoreOfferEntity> storeOfferList;

}