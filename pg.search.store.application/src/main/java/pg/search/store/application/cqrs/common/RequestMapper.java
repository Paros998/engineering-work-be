package pg.search.store.application.cqrs.common;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import pg.search.store.application.cqrs.product.command.card.AbstractCardCommand;
import pg.search.store.application.cqrs.user.command.CreateUserCommand;
import pg.search.store.domain.product.card.CardData;
import pg.search.store.domain.product.card.MemoryType;
import pg.search.store.domain.product.card.PciType;
import pg.search.store.domain.product.card.Technology;
import pg.search.store.domain.user.UserCredentialsData;

@UtilityClass
public class RequestMapper {
    public UserCredentialsData toUserCredentialsData(final @NonNull CreateUserCommand command) {
        return UserCredentialsData.builder()
                .email(command.getEmail())
                .password(command.getPassword())
                .role(command.getRole())
                .username(command.getUsername())
                .build();
    }

    public CardData toCardData(final @NonNull AbstractCardCommand command) {
        if (!Technology.isValidTechnology(command.getTechnology())) {
            command.setTechnology(Technology.NVIDIA.name());
        }

        if (!MemoryType.isValidType(command.getTypeOfMemory())) {
            command.setTypeOfMemory(MemoryType.GDDR5.name());
        }

        if (!PciType.isValidType(command.getTypeOfPciConnector())) {
            command.setTypeOfPciConnector(PciType.PCI_4X16.name());
        }

        return CardData.builder()
                .title(command.getTitle())
                .producentCode(command.getProducentCode())
                .producentSite(command.getProducentSite())
                .recommendedPower(command.getRecommendedPower())
                .rtxSupport(command.getRtxSupport())
                .supportedLibraries(command.getSupportedLibraries())
                .cudaCoresAmount(command.getCudaCoresAmount())
                .powerConsumption(command.getPowerConsumption())
                .powerConnector(command.getPowerConnector())
                .boostCoreClock(command.getBoostCoreClock())
                .cooling(command.getCooling())
                .coreClock(command.getCoreClock())
                .recommendedPower(command.getRecommendedPower())
                .memoryAmount(command.getMemoryAmount())
                .supportedDirectX(command.getSupportedDirectX())
                .memoryClock(command.getMemoryClock())
                .memoryBus(command.getMemoryBus())
                .technology(Technology.valueOf(command.getTechnology()))
                .typeOfMemory(MemoryType.valueOf(command.getTypeOfMemory()))
                .typeOfPciConnector(PciType.valueOf(command.getTypeOfPciConnector()))
                .build();
    }
}
