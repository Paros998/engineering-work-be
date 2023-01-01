package pg.search.store.domain.history;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Action {
    UNFOLLOW("unfollow"),

    FOLLOW("follow"),

    CHECK_PRODUCT("checkProduct"),

    OPINION("opinion"),

    CHECK_OFFER("checkOffer");

    private final String actionName;

    public static Action fromValue(String actionName) {
        return Arrays.stream(Action.values()).filter(action -> action.getActionName().equals(actionName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(actionName));
    }
}
