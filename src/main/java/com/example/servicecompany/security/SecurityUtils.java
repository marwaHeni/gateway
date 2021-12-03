package com.example.servicecompany.security;


import com.example.servicecompany.security.jwt.SamAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user.
     */
    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }


    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user.
     */
    public static Optional<String> getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
            .filter(authentication -> authentication.getCredentials() instanceof String)
            .map(authentication -> (String) authentication.getCredentials());
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise.
     */
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
            getAuthorities(authentication).noneMatch(AuthoritiesConstants.ANONYMOUS::equals);
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the {@code isUserInRole()} method in the Servlet API.
     *
     * @param authority the authority to check.
     * @return true if the current user has the authority, false otherwise.
     */
    public static boolean isCurrentUserInRole(String authority) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null &&
            getAuthorities(authentication).anyMatch(authority::equals);
    }

    private static Stream<String> getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority);
    }

    public static Optional<UUID> gettingUserId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    if (authentication instanceof SamAuthenticationToken) {
                        SamAuthenticationToken samAuthenticationToken = (SamAuthenticationToken) authentication;
                        return samAuthenticationToken.getUserId();
                    }
                    return null;
                });
    }

    public static Optional<String> gettingSchema() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    if (authentication instanceof SamAuthenticationToken) {
                        SamAuthenticationToken samAuthenticationToken = (SamAuthenticationToken) authentication;
                        return samAuthenticationToken.getUserSchema();
                    }
                    return null;
                });
    }

    public static Optional<String> gettingLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    if (authentication instanceof SamAuthenticationToken) {
                        SamAuthenticationToken samAuthenticationToken = (SamAuthenticationToken) authentication;
                        return samAuthenticationToken.getUserLogin();
                    }
                    return null;
                });
    }

    public static Optional<String> gettingUserName() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    if (authentication instanceof SamAuthenticationToken) {
                        SamAuthenticationToken samAuthenticationToken = (SamAuthenticationToken) authentication;
                        return samAuthenticationToken.getUserName();
                    }
                    return null;
                });
    }

    public static Optional<String> gettingUserLang() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    if (authentication instanceof SamAuthenticationToken) {
                        SamAuthenticationToken samAuthenticationToken = (SamAuthenticationToken) authentication;
                        return samAuthenticationToken.getLang();
                    }
                    return null;
                });
    }

    public static Optional<String> gettingUserPays() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    if (authentication instanceof SamAuthenticationToken) {
                        SamAuthenticationToken samAuthenticationToken = (SamAuthenticationToken) authentication;
                        return samAuthenticationToken.getPays();
                    }
                    return null;
                });
    }

    public static UUID getUserId() {
        Optional<UUID> userId = gettingUserId();

        return userId.isPresent() ? userId.get() : null;
    }

    public static String getSchema() {
        Optional<String> schema = gettingSchema();

        return schema.isPresent() ? schema.get() : null;
    }

    public static String getLogin() {
        Optional<String> login = gettingLogin();

        return login.isPresent() ? login.get() : null;
    }


    public static String getUserName() {
        Optional<String> userName = gettingUserName();

        return userName.isPresent() ? userName.get() : null;
    }

    public static String getUserLang() {
        Optional<String> lang = gettingUserLang();

        return lang.isPresent() ? lang.get() : null;
    }

    public static String getUserPays() {
        Optional<String> pays = gettingUserPays();

        return pays.isPresent() ? pays.get() : null;
    }
}
