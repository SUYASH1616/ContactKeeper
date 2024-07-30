package com.scm.Entity;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// name is optional by default class name 
// @Entity is fixed
@Entity(name="user")
// name is optinal by default name of class with phural
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
    @Id
    private String userId;
    @Column(name="user_name",nullable=false)
    private String name;
    @Column(unique=true,nullable = false)
    private String email;
    @Getter(AccessLevel.NONE)
    private String password;
    @Column(length = 1000)
    private String about;
    @Column(length = 1000)
    private String profilePic;
    private String phoneNumber;
    @Getter(AccessLevel.NONE)
    private boolean enabled=false;
    private boolean emailVerified=false;
    private boolean phoneVerified=false;
    private String emailToken;

    @Enumerated(value = EnumType.STRING)
    // tells from which type of login is
    
    private Providers provider=Providers.SELF;
    private String providerUserId;

    // mapping between contacts and User i.e user is One and it can have multiple contacts
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private  List<Contact> contacts=new ArrayList<>();

    // @ElementCollection(fetch = FetchType.EAGER)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList=new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<SimpleGrantedAuthority>roles= roleList.stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toList());

        return roles;
    }


    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
     }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }
    
    @Override
    public boolean isEnabled(){
        return this.enabled;
    }
    
    @Override
    public String  getPassword(){
        return this.password;
    }

}
