package com.example.bankcards.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "Username", nullable = false)
    private String username;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "FirstName", nullable = false)
    private String firstname;

    @Column(name = "LastName", nullable = false)
    private String lastname;

    @Enumerated(EnumType.STRING)
    @Column(name = "Role", nullable = false)
    private Role role;


    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @Column(name = "card")
    private List<Card> card;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
