package pg.search.store.application.cqrs.product.command.cpu;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.application.cqrs.common.RequestMapper;
import pg.search.store.domain.notification.NotificationType;
import pg.search.store.infrastructure.notification.NotificationService;
import pg.search.store.infrastructure.product.cpu.CpuService;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AddCpuCommandHandler implements CommandHandler<AddCpuCommand, UUID> {
    private final CpuService cpuService;
    private final NotificationService notificationService;

    @Override
    public UUID handle(final AddCpuCommand command) {
        final var entity = cpuService.addCpu(RequestMapper.toCpuData(command));

        notificationService.fireNotifications(NotificationType.NEW_PRODUCT, entity.getProductId().toString());

        return entity.getProductId();
    }
}
