package pg.search.store.infrastructure.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;
import pg.search.store.domain.user.ChangePassword;
import pg.search.store.domain.user.RegisterClient;
import pg.search.store.domain.user.UserCredentials;
import pg.search.store.domain.user.UserUpdate;
import pg.search.store.infrastructure.common.SpringPageRequest;
import pg.search.store.infrastructure.common.SpringPageResponse;
import pg.search.store.infrastructure.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface IUserService extends UserDetailsService {

    UserEntity getUser(UUID userId);

    UserEntity getUser(String username);

    String getUserAvatar(UUID userId);

    UserEntity getUserByEmail(String email);

    List<UserEntity> getUsers();

    SpringPageResponse<UserEntity> getUsers(SpringPageRequest pageRequest);

    UserEntity createUser(UserCredentials userCredentials);

    UserEntity createClient(RegisterClient registerClient);

    void uploadUserAvatar(UUID userId, MultipartFile file);

    void updateUser(UserEntity user);

    void changeUserPassword(UUID userId, ChangePassword changePassword);

    void updateUser(UUID userId, UserUpdate userUpdate);

    void updateUserAvatar(UUID userId, MultipartFile file);

    void changeStateOfUser(UUID userId);

    void deleteUserById(UUID userId);

    void deleteUserAvatar(UUID userId);

}
