package pg.search.store.infrastructure.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import pg.search.store.domain.product.ProductType;
import pg.search.store.domain.user.ChangePasswordData;
import pg.search.store.domain.user.RegisterClientData;
import pg.search.store.domain.user.UpdateUsernameEmailData;
import pg.search.store.domain.user.UserCredentialsData;
import pg.search.store.infrastructure.common.pageable.SpringPageRequest;
import pg.search.store.infrastructure.common.pageable.SpringPageResponse;
import pg.search.store.infrastructure.product.ProductEntity;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {

    UserEntity getUser(UUID userId);

    UserEntity getUser(String username);

    String getUserAvatar(UUID userId);

    UserEntity getUserByEmail(String email);

    List<UserEntity> getUsers();

    SpringPageResponse<UserEntity> getUsers(SpringPageRequest pageRequest);

    List<ProductEntity> getUserFollowedProducts(UUID userId, ProductType productType);

    Boolean isUserFollowingProduct(UUID userId, UUID productId);

    Boolean hasUserMarkedProduct(UUID userId, UUID productId);

    UserEntity createUser(UserCredentialsData credentials);

    UserEntity createClient(RegisterClientData data);

    void addProductToUserFollowed(UUID userId, UUID productId);

    void uploadUserAvatar(UUID userId, MultipartFile file);

    void updateUser(UserEntity user);

    void updateUserCurrency(UUID userId, String newCurrency);

    void changeUserPassword(UUID userId, ChangePasswordData changePasswordData);

    void updateUser(UUID userId, UpdateUsernameEmailData userData);

    void updateUserAvatar(UUID userId, MultipartFile file);

    void changeStateOfUser(UUID userId);

    void deleteUserById(UUID userId);

    void deleteUserAvatar(UUID userId);

    void removeProductFromUserFollowed(UUID userId, UUID productId);
}