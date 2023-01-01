package pg.search.store.infrastructure.user;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import pg.lib.awsfiles.entity.FileEntity;

import pg.search.store.domain.user.Roles;
import pg.search.store.domain.user.UserCredentialsData;
import pg.search.store.infrastructure.common.currency.CurrencyProvider;
import pg.search.store.infrastructure.history.HistoryEntity;
import pg.search.store.infrastructure.notification.NotificationEntity;
import pg.search.store.infrastructure.review.ReviewEntity;
import pg.search.store.infrastructure.user.settings.SettingsEntity;

import javax.persistence.*;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(
            nullable = false,
            updatable = false
    )
    private UUID userId;

    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private Roles role;

    private Boolean locked;

    private Boolean enabled;

    private String currency;

    @ManyToOne
    @JoinColumn(name = "file_id")
    @JsonManagedReference
    private FileEntity avatar;

    @OneToOne
    @JoinColumn(name = "settings_Id")
    private SettingsEntity userSettings;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<NotificationEntity> notifications;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ReviewEntity> reviews;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<HistoryEntity> userHistory;

    @ElementCollection(fetch = FetchType.LAZY, targetClass = UUID.class)
    private List<UUID> followedProducts;

    @ElementCollection(fetch = FetchType.LAZY, targetClass = UUID.class)
    private List<UUID> markedForBuyProducts;

    public UserEntity(final UserCredentialsData credentials,
                      final Boolean locked,
                      final Boolean enabled) {
        this.username = credentials.getUsername();
        this.password = credentials.getPassword();
        this.email = credentials.getEmail();
        this.role = credentials.getRole();
        this.locked = locked;
        this.enabled = enabled;
        this.currency = CurrencyProvider.DEFAULT_CURRENCY;
    }

    public UserEntity() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(("ROLE_" + role.name()));
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return userId != null && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
