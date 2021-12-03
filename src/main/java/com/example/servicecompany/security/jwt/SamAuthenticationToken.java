package com.example.servicecompany.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

public class SamAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public UUID getUserId() {
        return userId;
    }
    public String getUserSchema() {
        return schema;
    }

    public String getUserLogin() {
        return login;
    }

    public String getUserName() {
        return userName;
    }

    public String getLang() {
        return lang;
    }

    public String getPays() {
        return pays;
    }

    private final UUID userId;
    private final String schema;
    private final String login;
    private final String userName;
    private final String lang;
    private final String pays;

    public SamAuthenticationToken(Object principal, Object credentials, UUID userId, String schema, String login, String userName, String lang, String pays) {
        super(principal, credentials);
        this.userId = userId;
        this.schema = schema;
        this.login = login;
        this.userName = userName;
        this.lang = lang;
        this.pays = pays;
    }

    public SamAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, UUID userId, String schema, String login, String userName, String lang, String pays) {
        super(principal, credentials, authorities);
        this.userId = userId;
        this.schema = schema;
        this.login = login;
        this.userName = userName;
        this.lang = lang;
        this.pays = pays;
    }
}
