package com.rochez.wikiTI.security;

import com.rochez.wikiTI.model.Utilisateur;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author : Rochez Justin
 * @Creation : 10/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description :Class de définition pour l'utilisateur authentifié
 */
public class MyUserDetails implements UserDetails {
    private String mail;
    private String password;
    private String name;
    private List<GrantedAuthority> authorities; // c'est l'ensemble des role que possède notre utilisateur
    private Utilisateur user;

    public MyUserDetails() {
    }
    public MyUserDetails(String mail) {
        this.mail = mail;
    }
    public MyUserDetails(Utilisateur user) {
        this.mail = user.getMail();
        this.name = user.getNom()+ " " + user.getPrenom();
        this.password = user.getPassword();
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
        GrantedAuthority auhority = new SimpleGrantedAuthority("ROLE_"+this.user.getRole());
        auths.add(auhority);
        return auths;
    }
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    /**
     * Récupération de l'utilisateur pour l'utiliser dans notre application en code
     * @return
     */
    public Utilisateur getUser(){
        return this.user;
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
