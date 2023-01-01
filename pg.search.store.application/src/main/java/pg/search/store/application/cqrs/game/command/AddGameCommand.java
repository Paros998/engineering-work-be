package pg.search.store.application.cqrs.game.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import pg.lib.cqrs.command.Command;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class AddGameCommand implements Command<UUID> {
    private String title;
    private String releaseDate;
    private String description;
    private List<String> requiredGraphicCardModels;
    private List<String> requiredProcessorModels;
    private Map<String, Integer> scoreOnPlatforms;
    private List<String> platforms;
    private UUID file;
    private String graphicRequirements;
    private String processorRequirements;
    private Float requiredRam;
    private List<String> supportedSystems;
    private Float requiredSpace;
}
