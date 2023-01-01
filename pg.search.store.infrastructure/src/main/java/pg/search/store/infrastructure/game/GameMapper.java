package pg.search.store.infrastructure.game;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import pg.lib.awsfiles.service.FileService;

import pg.search.store.domain.game.GameData;
import pg.search.store.domain.game.GameWithScore;
import pg.search.store.infrastructure.common.CommonData;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameMapper {
    private final FileService fileService;

    public GameWithScore toGameWithScore(final GameEntity entity) {
        return GameWithScore.builder()
                .gameId(entity.getGameId())
                .name(entity.getTitle())
                .fileUrl(fileService.getFileUrl(entity.getFile().getFileId()))
                .platforms(entity.getPlatforms())
                .scores(entity.getScoreOnPlatforms())
                .build();
    }

    public GameData toGameData(final GameEntity entity) {
        List<GameData.PlatformScore> scoreList = new ArrayList<>();
        entity.getScoreOnPlatforms().forEach((platform, score) -> scoreList.add(GameData.PlatformScore.builder()
                .platform(platform).score(score)
                .build()));

        return GameData.builder()
                .title(entity.getTitle())
                .releaseDate(entity.getReleaseDate().format(DateTimeFormatter.ofPattern(CommonData.DATE_FORMAT)))
                .description(entity.getDescription())
                .scoreOnPlatforms(scoreList)
                .platforms(entity.getPlatforms())
                .file(entity.getFile().getFileId())
                .graphicRequirements(entity.getGraphicRequirements())
                .processorRequirements(entity.getProcessorRequirements())
                .requiredGraphicCardModels(entity.getRequiredGraphicCardModels())
                .requiredProcessorModels(entity.getRequiredProcessorModels())
                .requiredRam(entity.getRequiredRam())
                .supportedSystems(entity.getSupportedSystems())
                .requiredSpace(entity.getRequiredSpace())
                .build();
    }
}
