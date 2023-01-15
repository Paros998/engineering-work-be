package pg.search.store.infrastructure.resolvers;

import lombok.AllArgsConstructor;

import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import pg.lib.filters.common.Criteria;
import pg.lib.filters.common.JunctionType;
import pg.lib.filters.common.Operation;
import pg.lib.filters.specification.Combiner;
import pg.lib.filters.specification.SpecificationBuilder;

import pg.search.store.domain.game.Game;
import pg.search.store.domain.game.GamesFilter;
import pg.search.store.domain.game.Platform;
import pg.search.store.domain.product.Console;
import pg.search.store.domain.system.OperatingSystem;
import pg.search.store.infrastructure.game.GameEntity;
import pg.search.store.infrastructure.game.GameRepository;
import pg.search.store.infrastructure.product.card.CardEntity;
import pg.search.store.infrastructure.product.card.CardRepository;
import pg.search.store.infrastructure.product.cpu.CpuEntity;
import pg.search.store.infrastructure.product.cpu.CpuRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class FilterResolverImpl implements FilterResolver {
    private final CardRepository cardRepository;
    private final GameRepository gameRepository;
    private final CpuRepository cpuRepository;

    private Map<JunctionType, List<Criteria>> filter;

    private static List<String> getGames(final GamesFilter gamesFilter) {
        return gamesFilter.getGames().stream().map(Game::getName).toList();
    }

    @Override
    public List<SpecificationBuilder> resolveForCards(final GamesFilter gamesFilter) {
        filter = new HashMap<>();

        List<Criteria> criteria = new ArrayList<>();
        List<GameEntity> games = getGamesByTitles(getGames(gamesFilter));
        if (!games.stream().allMatch(game -> game.getPlatforms().contains(Platform.PC)))
            return List.of();

        List<CardEntity> matchingCards = getMatchingGpus(games);

        if (matchingCards.isEmpty())
            throw new ResponseStatusException(HttpStatus.OK, "No Matches");

        final Optional<Double> memoryBandwidth = matchingCards.stream().max(Comparator.comparingDouble(CardEntity::getMemoryBandwidth))
                .map(CardEntity::getMemoryBandwidth);

        Pair<Optional<Double>, Optional<Double>> cardProcessingPowerData = getCardProcessingPowerData(matchingCards);

        final Optional<Double> processingPower = cardProcessingPowerData.getFirst();

        final Optional<Double> boostProcessingPower = cardProcessingPowerData.getSecond();

        memoryBandwidth.ifPresent(value -> criteria.add(Criteria.builder()
                .operation(Operation.GREATER_THAN_EQUAL)
                .key("memoryBandwidth")
                .value(value)
                .build()));

        processingPower.ifPresent(value -> criteria.add(Criteria.builder()
                .operation(Operation.GREATER_THAN_EQUAL)
                .key("processingPower")
                .value(value)
                .build()));

        boostProcessingPower.ifPresent(value -> criteria.add(Criteria.builder()
                .operation(Operation.GREATER_THAN_EQUAL)
                .key("boostProcessingPower")
                .value(value)
                .build()));


        filter.put(JunctionType.OR, criteria);

        return List.of(SpecificationBuilder.of(Combiner.AND, filter));
    }

    private List<CardEntity> getMatchingGpus(List<GameEntity> games) {
        final List<String> distinctGpuModels = games.stream()
                .flatMap(entity -> entity.getRequiredGraphicCardModels().stream())
                .distinct().toList();

        return cardRepository.findMatchesByModel(distinctGpuModels);
    }

    private Pair<Optional<Double>, Optional<Double>> getCardProcessingPowerData(final List<CardEntity> cardEntities) {
        final Optional<Double> processingPower = cardEntities.stream().max(Comparator.comparingDouble(CardEntity::getProcessingPower))
                .map(CardEntity::getProcessingPower);

        final Optional<Double> boostProcessingPower =
                cardEntities.stream().max(Comparator.comparingDouble(CardEntity::getBoostProcessingPower))
                        .map(CardEntity::getBoostProcessingPower);

        return Pair.of(processingPower, boostProcessingPower);
    }

    @Override
    public List<SpecificationBuilder> resolveForConsoles(final GamesFilter gamesFilter) {
        filter = new HashMap<>();
        List<Console> consoles = gamesFilter.getConsoles();
        List<Criteria> finalCriteria = new ArrayList<>();

        consoles.forEach(console -> finalCriteria.add(Criteria.builder()
                .operation(Operation.EQUAL)
                .key("console")
                .value(console)
                .build()));

        filter.put(JunctionType.OR, finalCriteria);

        return List.of(SpecificationBuilder.of(Combiner.AND, filter));
    }

    @Override
    public List<SpecificationBuilder> resolveForCpus(GamesFilter gamesFilter) {
        filter = new HashMap<>();

        List<Criteria> criteria = new ArrayList<>();
        List<GameEntity> games = getGamesByTitles(getGames(gamesFilter));

        if (!games.stream().allMatch(game -> game.getPlatforms().contains(Platform.PC)))
            return List.of();

        List<CpuEntity> matchingCpus = getMatchingCpus(games);

        if (matchingCpus.isEmpty())
            throw new ResponseStatusException(HttpStatus.OK, "No Matches");

        filter.put(JunctionType.OR, List.of(
                Criteria.builder()
                        .operation(Operation.IS_FALSE)
                        .key("onlyLaptopCpu")
                        .build())
        );

        SpecificationBuilder onlyStandaloneProcessor = SpecificationBuilder.of(Combiner.AND, filter);

        Pair<Optional<Double>, Optional<Double>> cpuPerformanceData = getCpuPerformanceData(matchingCpus);

        final Optional<Double> performance = cpuPerformanceData.getFirst();

        final Optional<Double> boostPerformance = cpuPerformanceData.getSecond();

        performance.ifPresent(value -> criteria.add(Criteria.builder()
                .operation(Operation.GREATER_THAN_EQUAL)
                .key("basePerformance")
                .value(value)
                .build()));

        boostPerformance.ifPresent(value -> criteria.add(Criteria.builder()
                .operation(Operation.GREATER_THAN_EQUAL)
                .key("boostPerformance")
                .value(value)
                .build()));


        filter.put(JunctionType.OR, criteria);

        return List.of(onlyStandaloneProcessor, SpecificationBuilder.of(Combiner.AND, filter));
    }

    private List<CpuEntity> getMatchingCpus(final List<GameEntity> games) {
        final List<String> distinctCpuNames = games.stream()
                .flatMap(entity -> entity.getRequiredProcessorModels().stream())
                .distinct().toList();

        return cpuRepository.findMatchesByModel(distinctCpuNames);
    }

    private Pair<Optional<Double>, Optional<Double>> getCpuPerformanceData(final List<CpuEntity> matchingCpus) {
        final Optional<Double> performance = matchingCpus.stream().max(Comparator.comparingDouble(CpuEntity::getBasePerformance))
                .map(CpuEntity::getBasePerformance);

        final Optional<Double> boostPerformance = matchingCpus.stream().max(Comparator.comparingDouble(CpuEntity::getBoostPerformance))
                .map(CpuEntity::getBoostPerformance);

        return Pair.of(performance, boostPerformance);
    }

    @Override
    public List<SpecificationBuilder> resolveForPcs(final GamesFilter gamesFilter) {
        return resolveForPcsAndLaptops(gamesFilter);
    }

    @Override
    public List<SpecificationBuilder> resolveForLaptops(final GamesFilter gamesFilter) {
        return resolveForPcsAndLaptops(gamesFilter);
    }

    private Optional<Float> getRequiredRam(final List<GameEntity> games) {
        return Optional.of((float) games.stream().mapToDouble(GameEntity::getRequiredRam).max().orElse(0));
    }

    private List<GameEntity> getGamesByTitles(final List<String> games) {
        return gameRepository.findByTitleIn(games);
    }

    private Optional<Float> getRequiredSpace(final List<GameEntity> games) {
        return Optional.of(25.f + (float) games.stream().mapToDouble(GameEntity::getRequiredSpace).sum());
    }

    private List<OperatingSystem> getRequiredSystems(final List<GameEntity> games) {
        List<OperatingSystem> systems = new ArrayList<>();

        Arrays.stream(OperatingSystem.values()).forEach(system -> {
                    if (games.stream().allMatch(game -> game.getSupportedSystems().contains(system)))
                        systems.add(system);
                }
        );

        return systems;
    }

    private List<SpecificationBuilder> resolveForPcsAndLaptops(final GamesFilter gamesFilter) {
        List<SpecificationBuilder> builders = new ArrayList<>();
        List<Criteria> criteria;

        List<GameEntity> games = getGamesByTitles(getGames(gamesFilter));

        if (!games.stream().allMatch(game -> game.getPlatforms().contains(Platform.PC)))
            return List.of();

        List<CpuEntity> matchingCpus = getMatchingCpus(games);
        List<CardEntity> matchingCards = getMatchingGpus(games);

        Pair<Optional<Double>, Optional<Double>> cardProcessingPowerData = getCardProcessingPowerData(matchingCards);
        Pair<Optional<Double>, Optional<Double>> cpuPerformanceData = getCpuPerformanceData(matchingCpus);
        Optional<Float> minimumRequiredRam = getRequiredRam(games);
        Optional<Float> minimumRequiredSpace = getRequiredSpace(games);
        List<OperatingSystem> requiredSystems = getRequiredSystems(games);

        final Optional<Double> processingPower = cardProcessingPowerData.getFirst();
        final Optional<Double> boostProcessingPower = cardProcessingPowerData.getSecond();

        if (processingPower.isPresent() && boostProcessingPower.isPresent()) {
            filter = new HashMap<>();
            criteria = new ArrayList<>();

            criteria.add(Criteria.builder()
                    .operation(Operation.GREATER_THAN_EQUAL_JOIN)
                    .key("gpuCard.processingPower")
                    .value(processingPower.get())
                    .build());

            criteria.add(Criteria.builder()
                    .operation(Operation.GREATER_THAN_EQUAL_JOIN)
                    .key("gpuCard.boostProcessingPower")
                    .value(boostProcessingPower.get())
                    .build());

            filter.put(JunctionType.OR, criteria);

            builders.add(SpecificationBuilder.of(Combiner.AND, filter));
        }

        final Optional<Double> performance = cpuPerformanceData.getFirst();
        final Optional<Double> boostPerformance = cpuPerformanceData.getSecond();

        if (performance.isPresent() && boostPerformance.isPresent()) {
            filter = new HashMap<>();
            criteria = new ArrayList<>();

            criteria.add(Criteria.builder()
                    .operation(Operation.GREATER_THAN_EQUAL_JOIN)
                    .key("cpu.basePerformance")
                    .value(performance.get())
                    .build());

            criteria.add(Criteria.builder()
                    .operation(Operation.GREATER_THAN_EQUAL_JOIN)
                    .key("cpu.boostPerformance")
                    .value(boostPerformance.get())
                    .build());

            filter.put(JunctionType.OR, criteria);

            builders.add(SpecificationBuilder.of(Combiner.AND, filter));
        }

        if (minimumRequiredRam.isPresent()) {
            filter = new HashMap<>();
            criteria = new ArrayList<>();

            criteria.add(Criteria.builder()
                    .operation(Operation.GREATER_THAN_EQUAL)
                    .key("ramAmount")
                    .value(minimumRequiredRam.get())
                    .build());

            filter.put(JunctionType.OR, criteria);

            builders.add(SpecificationBuilder.of(Combiner.AND, filter));
        }

        if (minimumRequiredSpace.isPresent()) {
            filter = new HashMap<>();
            criteria = new ArrayList<>();

            criteria.add(Criteria.builder()
                    .operation(Operation.GREATER_THAN_EQUAL)
                    .key("totalSpaceAvailable")
                    .value(minimumRequiredSpace.get())
                    .build());

            filter.put(JunctionType.OR, criteria);

            builders.add(SpecificationBuilder.of(Combiner.AND, filter));
        }

        if (!requiredSystems.isEmpty()) {
            filter = new HashMap<>();
            criteria = new ArrayList<>();

            List<Criteria> finalCriteria = criteria;

            requiredSystems.forEach(system -> finalCriteria.add(Criteria.builder()
                    .operation(Operation.EQUAL)
                    .key("system")
                    .value(system)
                    .build()));

            filter.put(JunctionType.OR, finalCriteria);

            builders.add(SpecificationBuilder.of(Combiner.AND, filter));
        }

        return builders;
    }

}