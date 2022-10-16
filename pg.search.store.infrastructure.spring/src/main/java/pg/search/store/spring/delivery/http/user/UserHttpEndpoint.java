package pg.search.store.spring.delivery.http.user;

import lombok.AllArgsConstructor;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pg.lib.cqrs.service.ServiceExecutor;

import pg.search.store.spring.delivery.http.common.HttpCommonHelper;

@RestController
@RequestMapping(HttpCommonHelper.USER_PATH)
@AllArgsConstructor
@Tag(name = "User")
public class UserHttpEndpoint {
    private final ServiceExecutor serviceExecutor;

//    @GetMapping
//    public PageResponse<BasicUserDTO> getUsers(final @RequestParam Integer page, final @RequestParam Integer pageLimit,
//                                               final @RequestParam(required = false, defaultValue = "asc") String sortDir,
//                                               final @RequestParam(required = false, defaultValue = "title") String sortBy) {
//        return userService.getUsers(new PageRequestDTO(page, pageLimit, sortDir, sortBy));
//    }
//
//    @GetMapping("{userId}")
//    public BasicUserDTO getUser(final @PathVariable UUID userId) {
//        User user = userService.getUser(userId);
//        return BasicUserDTO.convertFromEntity(user, userService.getUserAvatar(userId));
//    }
//
//    @GetMapping("{userId}/settings")
//    public UserSettings getUserSettings(final @PathVariable UUID userId) {
//        return settingsService.getSettingsByUser(userService.getUser(userId));
//    }
//
//    @GetMapping("{userId}/notifications")
//    public List<NotificationModelDTO> getUserNotifications(final @PathVariable UUID userId) {
//        return notificationService.getUserNotifications(userService.getUser(userId));
//    }
//
//    @GetMapping("{userId}/follows/{cardId}")
//    public Boolean isUserFollowingCard(final @PathVariable UUID userId, final @PathVariable UUID cardId) {
//        return userService.isUserFollowingCard(userId, cardId);
//    }
//
//    @PostMapping
//    public BasicUserDTO createAppUser(final @RequestBody UserCredentials userCredentials) {
//        User user = userService.createUser(userCredentials);
//        return BasicUserDTO.convertFromEntity(user, fileService.getFileUrl(user.getAvatar().getFileId()));
//    }
//
//    @PostMapping("/register")
//    public BasicUserDTO registerClient(final @RequestBody RegisterClientDTO clientDTO) {
//        User user = userService.createClient(clientDTO);
//        return BasicUserDTO.convertFromEntity(user, fileService.getFileUrl(user.getAvatar().getFileId()));
//    }
//
//    @PutMapping("{userId}")
//    public void updateAppUser(final @PathVariable UUID userId, final @RequestBody UserUpdateDTO userUpdateDTO) {
//        userService.updateUser(userId, userUpdateDTO);
//    }
//
//    @PutMapping("{userId}/change-password")
//    public void changeUserPassword(final @PathVariable UUID userId, final @RequestBody ChangePasswordDTO passwordDTO) {
//        userService.changeUserPassword(userId, passwordDTO);
//    }
//
//    @PutMapping("{userId}/settings")
//    public void updateUserSettings(final @PathVariable UUID userId, final @RequestBody UserSettings newSettings) {
//        settingsService.updateUserSettings(
//                settingsService.getSettingsByUser(userService.getUser(userId)),
//                newSettings
//        );
//    }
//
//    @PatchMapping("{userId}")
//    public void changeStateOfUser(final @PathVariable UUID userId) {
//        userService.changeStateOfUser(userId);
//    }
//
//    @DeleteMapping("{userId}")
//    public void deleteUserById(final @PathVariable UUID userId) {
//        userService.deleteUserById(userId);
//    }
}