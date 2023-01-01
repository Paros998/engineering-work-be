package pg.search.store.application.cqrs.store.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import pg.lib.cqrs.command.Command;

import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
public class AddStoreCommand implements Command<UUID> {
    private String name;

    private String phone;

    private String website;

    private UUID storePhotoId;

    private Integer ratingCount;

    private Float ratingScore;

    private Collection<UUID> offersIds;
}