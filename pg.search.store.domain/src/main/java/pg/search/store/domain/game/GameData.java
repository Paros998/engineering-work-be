package pg.search.store.domain.game;

import lombok.*;

import pg.search.store.domain.system.OperatingSystem;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * A DTO for the {@link GameEntity} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GameData implements Serializable {
    private String title;
    private String releaseDate;
    private String description;
    private List<PlatformScore> scoreOnPlatforms;
    private List<Platform> platforms;
    private UUID file;
    private String graphicRequirements;
    private String processorRequirements;

    private List<String> requiredGraphicCardModels;
    private List<String> requiredProcessorModels;

    private Float requiredRam;
    private List<OperatingSystem> supportedSystems;
    private Float requiredSpace;

    @AllArgsConstructor
    @Builder
    @Getter
    public static class PlatformScore implements Serializable {
        private Platform platform;
        private Integer score;
    }
}