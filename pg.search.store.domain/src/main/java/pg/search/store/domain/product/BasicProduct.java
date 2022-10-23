package pg.search.store.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BasicProduct {
    private UUID productId;
    private String title;
    private ProductType productType;

    private Boolean isFollowed;
    private Boolean isMarked;

    private Boolean available;
    private Integer storesNumber;
    private Double storesLowestPrice;

    private String productPhoto;
}