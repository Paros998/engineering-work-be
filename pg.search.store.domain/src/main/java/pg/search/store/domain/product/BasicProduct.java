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
    protected UUID productId;
    protected String title;
    protected ProductType productType;

    protected Boolean isFollowed;
    protected Boolean isMarked;

    protected Boolean available;
    protected Integer storesNumber;
    protected Double storesLowestPrice;

    protected String productPhoto;
}