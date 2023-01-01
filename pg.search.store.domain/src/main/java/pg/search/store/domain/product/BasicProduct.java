package pg.search.store.domain.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BasicProduct {
    protected UUID productId;
    protected String title;
    protected ProductType productType;

    protected Boolean isFollowed;
    protected Boolean isMarked;

    protected Boolean available;
    protected Integer storesNumber;
    protected String storesLowestPrice;

    protected String productPhoto;

    public UUID getProductId() {
        return productId;
    }

    public BasicProduct setProductId(UUID productId) {
        this.productId = productId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BasicProduct setTitle(String title) {
        this.title = title;
        return this;
    }

    public ProductType getProductType() {
        return productType;
    }

    public BasicProduct setProductType(ProductType productType) {
        this.productType = productType;
        return this;
    }

    public Boolean getFollowed() {
        return isFollowed;
    }

    public BasicProduct setFollowed(Boolean followed) {
        isFollowed = followed;
        return this;
    }

    public Boolean getMarked() {
        return isMarked;
    }

    public BasicProduct setMarked(Boolean marked) {
        isMarked = marked;
        return this;
    }

    public Boolean getAvailable() {
        return available;
    }

    public BasicProduct setAvailable(Boolean available) {
        this.available = available;
        return this;
    }

    public Integer getStoresNumber() {
        return storesNumber;
    }

    public BasicProduct setStoresNumber(Integer storesNumber) {
        this.storesNumber = storesNumber;
        return this;
    }

    public String getStoresLowestPrice() {
        return storesLowestPrice;
    }

    public BasicProduct setStoresLowestPrice(String storesLowestPrice) {
        this.storesLowestPrice = storesLowestPrice;
        return this;
    }

    public String getProductPhoto() {
        return productPhoto;
    }

    public BasicProduct setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
        return this;
    }
}