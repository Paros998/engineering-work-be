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

import javax.persistence.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

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

    @ManyToOne
    @JoinColumn(name = "file_id")
    @JsonManagedReference
    private FileEntity avatar;

//    @OneToOne
//    @JoinColumn(name = "user_settings_Id")
//    private UserSettings userSettings;
//
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    @JsonManagedReference
//    private List<Notification> notifications;
//
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    @JsonManagedReference
//    private List<Review> reviews;
//
//    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
//    @JsonManagedReference
//    private List<History> userHistory;
//
//    @ElementCollection(fetch = FetchType.LAZY, targetClass = UUID.class)
//    private List<UUID> followedCards;

    public UserEntity(final UserCredentialsData credentials,
                      final Boolean locked,
                      final Boolean enabled) {
        this.username = credentials.getUsername();
        this.password = credentials.getPassword();
        this.email = credentials.getEmail();
        this.role = credentials.getRole();
        this.locked = locked;
        this.enabled = enabled;
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