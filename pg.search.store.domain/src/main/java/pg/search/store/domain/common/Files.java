package pg.search.store.domain.common;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class Files {
    private static UUID defaultUserPhoto;
    private static UUID defaultProductPhoto;

    public static UUID getDefaultUserPhoto() {
        return defaultUserPhoto;
    }

    public static void setDefaultUserPhoto(final UUID defaultUserPhoto) {
        Files.defaultUserPhoto = defaultUserPhoto;
    }

    public static UUID getDefaultProductPhoto() {
        return defaultProductPhoto;
    }

    public static void setDefaultProductPhoto(final UUID defaultProductPhoto) {
        Files.defaultProductPhoto = defaultProductPhoto;
    }
}
