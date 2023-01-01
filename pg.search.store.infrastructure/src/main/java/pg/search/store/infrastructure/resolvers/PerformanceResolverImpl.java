package pg.search.store.infrastructure.resolvers;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.search.store.domain.game.Game;
import pg.search.store.domain.game.GamesFilter;
import pg.search.store.domain.product.Performance;
import pg.search.store.infrastructure.game.GameRepository;
import pg.search.store.infrastructure.product.card.CardEntity;
import pg.search.store.infrastructure.product.card.CardRepository;
import pg.search.store.infrastructure.product.cpu.CpuEntity;
import pg.search.store.infrastructure.product.cpu.CpuRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PerformanceResolverImpl implements PerformanceResolver {
    private final GameRepository gameRepository;
    private final CardRepository cardRepository;
    private final CpuRepository cpuRepository;

    @Override
    public Performance resolveForCards(final GamesFilter filter) {
        Performance performance = new Performance();

        List<String> games = filter.getGames().stream().map(Game::getName).toList();

        final List<String> distinctGpuModels = gameRepository.findByTitleIn(games).stream()
                .flatMap(entity -> entity.getRequiredGraphicCardModels().stream())
                .distinct().toList();

        List<CardEntity> matchingCards = cardRepository.findMatchesByModel(distinctGpuModels);

        Optional<CardEntity> cardByProcessingPower = matchingCards.stream().max(Comparator.comparingDouble(CardEntity::getProcessingPower));
        Optional<CardEntity> cardByBoostProcessingPower =
                matchingCards.stream().max(Comparator.comparingDouble(CardEntity::getBoostProcessingPower));

        // TODO check if really empty
        performance.setAveragePerformance(cardByProcessingPower.isPresent() ? cardByProcessingPower.get().getProcessingPower() :
                matchingCards.stream().mapToDouble(CardEntity::getProcessingPower).average().orElse(0));

        performance.setPeakPerformance(cardByBoostProcessingPower.isPresent() ?
                cardByBoostProcessingPower.get().getBoostProcessingPower() :
                matchingCards.stream().mapToDouble(CardEntity::getBoostProcessingPower).average().orElse(0));

        return performance;
    }

    @Override
    public Performance resolveForCpus(final GamesFilter filter) {
        Performance performance = new Performance();

        List<String> games = filter.getGames().stream().map(Game::getName).toList();

        final List<String> distinctCpuModels = gameRepository.findByTitleIn(games).stream()
                .flatMap(entity -> entity.getRequiredProcessorModels().stream())
                .distinct().toList();

        List<CpuEntity> matchingCpus = cpuRepository.findMatchesByModel(distinctCpuModels);
        
        //TODO filter from matching Cpus and Cards ones with the same model, leave only the weakest one of each model

        Optional<CpuEntity> cpuByPerformance = matchingCpus.stream().max(Comparator.comparingDouble(CpuEntity::getBasePerformance));
        Optional<CpuEntity> cpuByBoostPerformance = matchingCpus.stream().max(Comparator.comparingDouble(CpuEntity::getBoostPerformance));

        performance.setAveragePerformance(cpuByPerformance.isPresent() ? cpuByPerformance.get().getBasePerformance() :
                matchingCpus.stream().mapToDouble(CpuEntity::getBasePerformance).average().orElse(0));

        performance.setPeakPerformance(cpuByBoostPerformance.isPresent() ?
                cpuByBoostPerformance.get().getBoostPerformance() :
                matchingCpus.stream().mapToDouble(CpuEntity::getBoostPerformance).average().orElse(0));

        return performance;
    }

    @Override
    public Performance resolveForPCs(final GamesFilter filter) {
        return resolveForPcLaptops(filter);
    }

    @Override
    public Performance resolveForLaptops(final GamesFilter filter) {
        return resolveForPcLaptops(filter);
    }

    private Performance resolveForPcLaptops(final GamesFilter filter) {
        final var cpuPerformance = resolveForCpus(filter);
        final var gpuPerformance = resolveForCards(filter);

        Double peakPerformance = (cpuPerformance.getPeakPerformance() == 0 || gpuPerformance.getPeakPerformance() == 0) ? 0 :
                (cpuPerformance.getPeakPerformance() + gpuPerformance.getPeakPerformance()) / 2.0;

        Double averagePerformance = (cpuPerformance.getAveragePerformance() == 0 || gpuPerformance.getAveragePerformance() == 0) ? 0 :
                (cpuPerformance.getAveragePerformance() + gpuPerformance.getAveragePerformance()) / 2.0;

        return Performance.builder().peakPerformance(peakPerformance).averagePerformance(averagePerformance).build();
    }
}