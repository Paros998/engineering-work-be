package pg.search.store.infrastructure.user;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import pg.lib.awsfiles.entity.FileEntity;
import pg.lib.awsfiles.service.FileService;

import pg.search.store.domain.common.Files;
import pg.search.store.domain.product.ProductType;
import pg.search.store.domain.user.*;
import pg.search.store.infrastructure.common.pageable.SpringPageRequest;
import pg.search.store.infrastructure.common.pageable.SpringPageResponse;
import pg.search.store.infrastructure.product.ProductEntity;
import pg.search.store.infrastructure.product.ProductRepository;
import pg.search.store.infrastructure.user.settings.SettingsService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private static final String USER_NOT_FOUND = "User with username %s not found";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final FileService fileService;
    private final SettingsService settingsService;

    @Override
    public UserDetails loadUserByUsername(final @NonNull String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, username))
        );
    }

    public UserEntity getUser(final @NonNull UUID userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with given userId %s " + "doesn't exist!", userId))
        );
    }

    public UserEntity getUser(final @NonNull String username) {
        return userRepository.getByUsername(username);
    }

    public String getUserAvatar(final @NonNull UUID userId) {
        FileEntity avatar = getUser(userId).getAvatar();

        if (avatar == null)
            return fileService.getFileUrl(Files.getDefaultUserPhoto());

        return fileService.getFileUrl(avatar.getFileId());
    }

    public UserEntity getUserByEmail(final @NonNull String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with given email %s doesn't exist in " +
                        "database!", email))
        );
    }

    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    public SpringPageResponse<UserEntity> getUsers(final @NonNull SpringPageRequest pageRequestDTO) {
        return new SpringPageResponse<>(userRepository.findAll(pageRequestDTO.getRequest(UserEntity.class)));
    }

    public List<ProductEntity> getUserFollowedProducts(final @NonNull UUID userId, final ProductType productType) {
        UserEntity user = userRepository.getReferenceById(userId);

        if (productType == null)
            return productRepository.findByProductIdIn(user.getFollowedProducts());

        return productRepository.findByProductIdInAndProductType(user.getFollowedProducts(), productType);
    }

    public UserEntity createUser(final @NonNull UserCredentialsData credentials) {
        checkForEmailDuplicates(credentials.getEmail());

        checkForUsernameDuplicates(credentials.getUsername());

        credentials.setPassword(bCryptPasswordEncoder.encode(credentials.getPassword()));

        UserEntity user = new UserEntity(credentials, false, true);

        userRepository.save(user);

        user.setUserSettings(settingsService.createUserSettings(user));

        userRepository.save(user);

        return user;
    }

    public UserEntity createClient(final @NonNull RegisterClientData data) {
        checkForEmailDuplicates(data.getEmail());

        checkForUsernameDuplicates(data.getUsername());

        data.setPassword(bCryptPasswordEncoder.encode(data.getPassword()));

        UserEntity user = new UserEntity(
                new UserCredentialsData(data.getUsername(), data.getPassword(), data.getEmail(), Roles.CLIENT),
                false,
                true
        );

        userRepository.save(user);

        Optional<FileEntity> avatar = fileService.findById(data.getFileId());

        if (avatar.isPresent())
            user.setAvatar(avatar.get());
        else user.setAvatar(fileService.getFileById(Files.getDefaultUserPhoto()));

        userRepository.save(user);

        return user;
    }

    public void addProductToUserFollowed(final @NonNull UUID userId, final @NonNull UUID productId) {
        final List<UUID> followedProducts = getUser(userId).getFollowedProducts();
        followedProducts.add(productId);

        userRepository.updateFollowedProductsByUserId(followedProducts, userId);
    }

    public void uploadUserAvatar(final @NonNull UUID userId, @NonNull final MultipartFile file) {
        UserEntity user = getUser(userId);

        UUID avatarId = fileService.uploadFile(file);

        user.setAvatar(fileService.getFileById(avatarId));

        userRepository.save(user);
    }

    public void updateUser(final @NonNull UserEntity user) {
        userRepository.save(user);
    }

    public void changeUserPassword(final @NonNull UUID userId, final @NonNull ChangePasswordData data) {
        UserEntity user = getUser(userId);

        if (!bCryptPasswordEncoder.matches(data.getOldPassword(), user.getPassword()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Old password doesn't match existing one");

        if (bCryptPasswordEncoder.matches(data.getNewPassword(), user.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password and current password are the same");

        user.setPassword(bCryptPasswordEncoder.encode(data.getNewPassword()));

        userRepository.save(user);
    }

    public void updateUser(final UUID userId, final UpdateUsernameEmailData userData) {
        UserEntity user = getUser(userId);

        // TODO fix update
        if (!user.getEmail().equals(userData.getEmail().trim())) {
            checkForEmailDuplicates(userData.getEmail());
        }

        if (!user.getUsername().equals(userData.getUsername().trim())) {
            checkForUsernameDuplicates(userData.getUsername());
        }

        if (!userData.getUsername().isEmpty())
            user.setUsername(userData.getUsername().trim());

        if (!userData.getEmail().isEmpty())
            user.setEmail(userData.getEmail().trim());

        userRepository.save(user);
    }

    private void checkForUsernameDuplicates(final String username) {
        if (userRepository.existsByUsername(username.trim()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("This username %s is already taken!", username));
    }

    private void checkForEmailDuplicates(final String email) {
        if (userRepository.existsByEmail(email.trim()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("This email %s is already taken!", email));
    }

    public void updateUserAvatar(final UUID userId, final MultipartFile file) {
        UserEntity user = getUser(userId);

        UUID newAvatarId = fileService.uploadFile(file);

        FileEntity oldAvatar = user.getAvatar();

        user.setAvatar(null);

        fileService.deleteFile(oldAvatar.getFileId());

        user.setAvatar(fileService.getFileById(newAvatarId));

        userRepository.save(user);
    }

    public void changeStateOfUser(final UUID userId) {
        UserEntity user = getUser(userId);

        user.setLocked(!user.getLocked());

        user.setEnabled(!user.getEnabled());

        userRepository.save(user);
    }

    public void deleteUserById(final UUID userId) {
        getUser(userId);

        userRepository.deleteById(userId);
    }

    public void deleteUserAvatar(final UUID userId) {
        UserEntity user = getUser(userId);

        FileEntity avatar = user.getAvatar();

        user.setAvatar(null);

        userRepository.save(user);

        fileService.deleteFile(avatar.getFileId());
    }

    public void removeProductFromUserFollowed(final UUID userId, final UUID productId) {
        final List<UUID> followedProducts = getUser(userId).getFollowedProducts();

        if (!followedProducts.remove(productId))
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "User with id: %s couldn't unfollow product: %s, because he didn't ".formatted(userId, productId) + "followed it");

        userRepository.updateFollowedProductsByUserId(followedProducts, userId);
    }
}