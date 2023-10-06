package com.backend.login.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="user")

public class User implements UserDetails {

    @Id
    @Column(name="userId")
    private String userId;
   // private String name;
    @Column(name="email",unique=true)
    private String email;

    @Column(name="Name")
    private String Name;

    @Column(name="aboutMe",length = 200)
    private String aboutMe;

    @Column(name="password")
    private String password;



//    @OneToMany(mappedBy = "user", orphanRemoval = true,cascade = CascadeType.ALL)
//    private List<Post> posts = new ArrayList<>();



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
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
