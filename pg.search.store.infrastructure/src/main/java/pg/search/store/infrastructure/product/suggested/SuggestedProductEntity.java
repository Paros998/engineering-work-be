package pg.search.store.infrastructure.product.suggested;
 
import pg.search.store.infrastructure.product.ProductEntity;

import javax.persistence.Entity;
import javax.persistence.SecondaryTable;

@Entity
@SecondaryTable(name = "suggested_products")
public class SuggestedProductEntity extends ProductEntity {
}