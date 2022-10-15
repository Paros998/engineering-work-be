package pg.search.store.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialsData {
    private String username;
    private String password;
    private String email;
    private Roles role;
}
