package pg.search.store.infrastructure.history;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.search.store.domain.history.Action;
import pg.search.store.domain.history.HistoryData;
import pg.search.store.infrastructure.product.ProductService;
import pg.search.store.infrastructure.user.UserService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class HistoryMapperImpl implements HistoryMapper {
    private final UserService userService;
    private final ProductService productService;

    public HistoryData toHistoryData(final HistoryEntity history) {
        return HistoryData.builder()
                .historyId(history.getHistoryId())
                .action(history.getAction().getActionName())
                .content(history.getContent())
                .dateTime(history.getDateTime().toString())
                .productId(history.getProduct().getProductId())
                .build();
    }

    public HistoryEntity toHistoryEntity(final HistoryData data, final UUID userId) {
        return HistoryEntity.builder()
                .user(userService.getUser(userId))
                .action(Action.fromValue(data.getAction()))
                .content(data.getContent())
                .dateTime(LocalDateTime.now())
                .product(productService.getEntityById(data.getProductId()))
                .build();
    }
}
