package com.bughunters.code.passwordmanagerwebapplication.entity;

import com.bughunters.code.passwordmanagerwebapplication.models.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false , unique = true)
    private String email;

    private String username;

    private String password;

    @JsonIgnore
    private Role role;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private VerificationCodes verificationCodes;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<RefreshTokenTable> refreshTokenTable;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<AccessTokenTable> accessTokenTable;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private ForgotPassword forgotPassword;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserProfiles userProfiles;

    // Other relationships and methods...

    @PrePersist
    public void defaults() {
        this.role = Role.USER;
        if (this.userProfiles == null) {
            this.userProfiles = new UserProfiles();
            this.userProfiles.setProfileImage(null);
            this.userProfiles.setUser(this);
            this.userProfiles.setFirstName("Default");
            this.userProfiles.setLastName("Name");

        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(Role.USER.toString()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
