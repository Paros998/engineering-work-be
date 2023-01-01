package pg.search.store.application.cqrs.product.command.laptop;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.application.cqrs.common.RequestMapper;
import pg.search.store.domain.notification.NotificationType;
import pg.search.store.domain.product.laptop.LaptopData;
import pg.search.store.infrastructure.notification.NotificationService;
import pg.search.store.infrastructure.product.ProductMapper;
import pg.search.store.infrastructure.product.card.CardService;
import pg.search.store.infrastructure.product.cpu.CpuService;
import pg.search.store.infrastructure.product.laptop.LaptopService;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AddLaptopCommandHandler implements CommandHandler<AddLaptopCommand, UUID> {
    private final LaptopService laptopService;
    private final CardService cardService;
    private final CpuService cpuService;
    private final ProductMapper productMapper;
    private final NotificationService notificationService;

    @Override
    public UUID handle(final AddLaptopCommand command) {
        final var gpu = productMapper.toGpuData(cardService.getCardById(command.getGpuCardId()));
        final var cpu = productMapper.toProcessorData(cpuService.getCpuById(command.getProcessorId()));

        final LaptopData data = RequestMapper.toLaptopData(command, gpu, cpu);

        final var entity = laptopService.addLaptop(data);

        notificationService.fireNotifications(NotificationType.NEW_PRODUCT, entity.getProductId().toString());

        return entity.getProductId();
    }
}
