package pg.search.store.infrastructure.game;

import lombok.AllArgsConstructor;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import pg.lib.awsfiles.entity.FileEntity;
import pg.lib.awsfiles.service.FileService;
import pg.lib.filters.common.Criteria;
import pg.lib.filters.common.JunctionType;
import pg.lib.filters.common.Operation;
import pg.lib.filters.specification.Combiner;
import pg.lib.filters.specification.SpecificationBuilder;
import pg.lib.filters.specification.SpecificationCollector;

import pg.search.store.domain.common.Files;
import pg.search.store.domain.game.GameData;
import pg.search.store.domain.game.Platform;
import pg.search.store.infrastructure.common.CommonData;
import pg.search.store.infrastructure.common.exception.EntityNotFoundException;
import pg.search.store.infrastructure.common.pageable.SpringPageRequest;
import pg.search.store.infrastructure.common.pageable.SpringPageResponse;

import java.util.*;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final FileService fileService;

    public GameEntity getGameById(final UUID gameId) {
        return gameRepository.findById(gameId).orElseThrow(() -> new EntityNotFoundException(gameId, GameEntity.class));
    }

    public SpringPageResponse<GameEntity> findGamesByNameOrPlatform(final String searchTitle, final Platform searchPlatform,
                                                                    final SpringPageRequest request) {
        Map<JunctionType, List<Criteria>> filters;
        List<Criteria> criteria;
        List<SpecificationBuilder> builders = new ArrayList<>();

        if (searchTitle != null && !searchTitle.isBlank()) {
            criteria = new ArrayList<>();
            criteria.add(Criteria.of("title", searchTitle, Operation.MATCH));

            filters = new HashMap<>();
            filters.put(JunctionType.OR, criteria);
            builders.add(SpecificationBuilder.of(Combiner.AND, filters));
        }

        if (searchPlatform != null) {
            criteria = new ArrayList<>();

            criteria.add(Criteria.of("platforms", searchPlatform, Operation.IS_MEMBER));

            filters = new HashMap<>();
            filters.put(JunctionType.OR, criteria);
            builders.add(SpecificationBuilder.of(Combiner.AND, filters));
        }

        if (!builders.isEmpty()) {
            Specification<GameEntity> specification = SpecificationCollector.createSpecification(builders);

            return new SpringPageResponse<>(gameRepository.findAll(specification, request.getRequest(GameEntity.class)));
        }

        return new SpringPageResponse<>(gameRepository.findAll(request.getRequest(GameEntity.class)));
    }

    public UUID addGame(final GameData data) {
        return gameRepository.save(toGameEntity(data)).getGameId();
    }

    public GameEntity toGameEntity(final GameData data) {
        Optional<FileEntity> gamePhoto = fileService.findById(data.getFile());
        Map<Platform, Integer> scoreMap = new HashMap<>();

        data.getScoreOnPlatforms().forEach(platformScore ->
                scoreMap.put(platformScore.getPlatform(), platformScore.getScore()));

        return GameEntity.builder()
                .title(data.getTitle())
                .releaseDate(CommonData.parseFrom(data.getReleaseDate()))
                .description(data.getDescription())
                .scoreOnPlatforms(scoreMap)
                .platforms(data.getPlatforms())
                .file(gamePhoto.orElse(fileService.getFileById(Files.getDefaultProductPhoto())))
                .graphicRequirements(data.getGraphicRequirements())
                .processorRequirements(data.getProcessorRequirements())
                .requiredGraphicCardModels(data.getRequiredGraphicCardModels())
                .requiredProcessorModels(data.getRequiredProcessorModels())
                .requiredRam(data.getRequiredRam())
                .supportedSystems(data.getSupportedSystems())
                .requiredSpace(data.getRequiredSpace())
                .build();
    }

}