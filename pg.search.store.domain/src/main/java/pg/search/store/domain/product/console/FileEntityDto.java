package pg.search.store.domain.product.console;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.UUID;

/**
 * A DTO for the {@link pg.lib.awsfiles.entity.FileEntity} entity
 */
@AllArgsConstructor
@Getter
public class FileEntityDto implements Serializable {
    private final UUID fileId;
}