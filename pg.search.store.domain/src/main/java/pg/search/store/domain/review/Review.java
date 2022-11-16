package pg.search.store.domain.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Review {
    private UUID id;
    private UUID productId;
    private String opinion;
    private Integer score;
    private String username;
    private UUID userId;
    private String reviewDate;
    private Boolean isCensored;
}