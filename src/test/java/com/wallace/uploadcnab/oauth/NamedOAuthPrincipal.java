package com.wallace.uploadcnab.oauth;

import org.springframework.security.core.GrantedAuthority;

import java.security.Principal;
import java.util.Collection;

public class NamedOAuthPrincipal implements Principal {
    public String name;
    public Collection<GrantedAuthority> authorities;
    public NamedOAuthPrincipal(String name, Collection<GrantedAuthority> authorities) {
        this.name = name;
        this.authorities = authorities;
    }
    @Override
    public String getName() {
        return name;
    }
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}