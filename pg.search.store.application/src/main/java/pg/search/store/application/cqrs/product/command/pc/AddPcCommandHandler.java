package pg.search.store.application.cqrs.product.command.pc;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.cqrs.command.CommandHandler;

import pg.search.store.application.cqrs.common.RequestMapper;
import pg.search.store.domain.notification.NotificationType;
import pg.search.store.domain.product.pc.PcData;
import pg.search.store.infrastructure.notification.NotificationService;
import pg.search.store.infrastructure.product.ProductMapper;
import pg.search.store.infrastructure.product.card.CardService;
import pg.search.store.infrastructure.product.cpu.CpuService;
import pg.search.store.infrastructure.product.pc.PcService;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AddPcCommandHandler implements CommandHandler<AddPcCommand, UUID> {
    private final PcService pcService;
    private final CardService cardService;
    private final CpuService cpuService;
    private final ProductMapper productMapper;
    private final NotificationService notificationService;

    @Override
    public UUID handle(final AddPcCommand command) {
        final var gpu = productMapper.toGpuData(cardService.getCardById(command.getGpuCardId()));
        final var cpu = productMapper.toProcessorData(cpuService.getCpuById(command.getProcessorId()));

        final PcData data = RequestMapper.toPcData(command, gpu, cpu);

        final var entity = pcService.addPc(data);

        notificationService.fireNotifications(NotificationType.NEW_PRODUCT, entity.getProductId().toString());

        return entity.getProductId();
    }
}
