package pg.search.store.domain.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StoreData implements Serializable {
    private UUID storeId;

    private String name;

    private String phone;

    private String website;

    private UUID storePhotoId;

    private Integer ratingCount;

    private Float ratingScore;

    private Collection<UUID> offersIds;
}