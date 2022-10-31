package ru.nidecker.nbanews.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import static org.hibernate.annotations.CascadeType.ALL;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
//@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String nickname;

    @NotBlank
    @Column(unique = true)
    @Email
    private String email;

    @NotBlank
    private String password;

    private String avatar;

    private boolean enabled;
    private boolean locked;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Column(name = "role")
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Cascade(ALL)
    private Set<Role> roles;

    public User(String nickname,
                String email,
                String password,
                Set<Role> roles) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @JsonIgnore
    public boolean isUser() {
        return getRoles().contains(Role.USER);
    }

    @JsonIgnore
    public boolean isAdmin() {
        return getRoles().contains(Role.ADMIN);
    }

    @JsonIgnore
    public boolean isEditor() {
        return getRoles().contains(Role.EDITOR);
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return enabled;
    }
}
