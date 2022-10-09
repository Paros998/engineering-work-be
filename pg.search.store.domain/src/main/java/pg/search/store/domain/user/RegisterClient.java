package pg.search.store.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterClient {
    private UUID fileId;
    private String email;
    private String username;
    private String password;
}
