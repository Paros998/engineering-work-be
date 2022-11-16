package pg.search.store.infrastructure.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Platform, Integer> scoreOnPlatforms;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Platform> platforms;

    private String requiredGraphicCard;

    private String requiredCpu;

    private Float requiredRam;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<OperatingSystem> supportedSystems;
}