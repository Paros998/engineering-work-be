package pg.search.store.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserData {
    private UUID userId;
    private String username;
    private String email;
    private Roles role;
    private Boolean isBlocked;
    private String avatarFile;
    private String currency;
}
