package pg.search.store.infrastructure.user;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pg.search.store.domain.user.UserData;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserMapper {
    private final UserService userService;

    public UserData toUserData(final UserEntity userEntity) {
        final UUID userId = userEntity.getUserId();

        return UserData.builder()
                .userId(userId)
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .username(userEntity.getUsername())
                .isBlocked(!userEntity.isEnabled())
                .avatarFile(userService.getUserAvatar(userId))
                .build();
    }
}
