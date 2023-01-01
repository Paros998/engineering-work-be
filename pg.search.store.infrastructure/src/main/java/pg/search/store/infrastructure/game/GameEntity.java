package pg.search.store.infrastructure.game;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import pg.lib.awsfiles.entity.FileEntity;

import pg.search.store.domain.game.Platform;
import pg.search.store.domain.system.OperatingSystem;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "games")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameEntity implements Serializable {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(
            nullable = false,
            updatable = false
    )
    private UUID gameId;

    private String title;

    private LocalDate releaseDate;

    private String description;

    @ElementCollection(fetch = FetchType.LAZY)
    private Map<Platform, Integer> scoreOnPlatforms;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private List<Platform> platforms;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    @JsonManagedReference
    private FileEntity file;

    private String graphicRequirements;
    private String processorRequirements;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> requiredGraphicCardModels;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> requiredProcessorModels;

    private Float requiredRam;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<OperatingSystem> supportedSystems;

    private Float requiredSpace;
}