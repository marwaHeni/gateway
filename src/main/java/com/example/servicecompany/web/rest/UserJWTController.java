package com.example.servicecompany.web.rest;

import com.example.servicecompany.domain.User;
import com.example.servicecompany.repository.UserRepository;
import com.example.servicecompany.security.jwt.JWTFilter;
import com.example.servicecompany.security.jwt.TokenProvider;
import com.example.servicecompany.service.UserService;
import com.example.servicecompany.web.vm.LoginVM;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller to authenticate users.
 */

@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);
    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final UserRepository userRepository;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserJWTController(TokenProvider tokenProvider, UserService userService, UserRepository userRepository, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @PostMapping("/authenticate")
    // @CrossOrigin(origins = "http://localhost:8080")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
        log.info("loginVM");

        Optional<User> userLogin = userRepository.findByEmail(loginVM.getUsername());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLogin.get().getLogin(),loginVM.getPassword());
        log.debug("authenticationToken", authenticationToken);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(userLogin.get().getLogin());
        UUID userId = user.get().getId();
        //  String schema = user.get().getCompany().getTenant().getSchema();

//        Optional<String> schema = Optional.of("public");
//        Optional<String> schema1 = Optional.ofNullable(user.get().getCompany().getTenant().getSchema());
//        if (schema1 != null) {
//            schema = schema1;
//        }

        String schema = null;
        String pays = null;
//        Company company = user.get().getCompany();
//        if(company != null){
//            schema = user.get().getCompany().getTenant().getSchema();
//
//            pays = user.get().getCompany().getPays().getName();
//
//        }
        String login = user.get().getLogin();
        String userName = user.get().getLastName()+" "+user.get().getFirstName();
        String lang = user.get().getLangKey();

//        String schema = TenantContextHolder.DEFAULT_SCHEMA;
//        if (user.get().getTenant() != null) {
//            schema = user.get().getTenant().getSchema();
//        }
//        TenantContextHolder.setCurrentSchema(schema);

        String jwt = tokenProvider.createToken(authentication, rememberMe, userId, schema, login, userName, lang, pays);
        log.debug("jwttttt", jwt);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        if(new JWTToken(jwt) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/authenticateEshopClient")
    public ResponseEntity<JWTToken> authorizeEshopClient(@Valid @RequestBody LoginVM loginVM) {
        log.info("loginVM");

        Optional<User> userLogin = userRepository.findByEmail(loginVM.getUsername());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLogin.get().getLogin(), loginVM.getPassword());
        log.debug("authenticationToken", authenticationToken);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(userLogin.get().getLogin());
        UUID userId = user.get().getId();


        String schema = null;
        String pays = null;
//        Company company = user.get().getCompany();
//        if (company != null) {
//            schema = user.get().getCompany().getTenant().getSchema();
//
//            pays = user.get().getCompany().getPays().getName();
//
//        }
        String login = user.get().getLogin();
        String userName = user.get().getLastName() + " " + user.get().getFirstName();
        String lang = user.get().getLangKey();

        String jwt = null;
        if (loginVM.getSchemaName().equals(schema)){
            jwt = tokenProvider.createToken(authentication, rememberMe, userId, schema, login, userName, lang, pays);
    }

        log.debug("jwttttt", jwt);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        if(new JWTToken(jwt) == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);

    }



    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }

//    public static Optional<String> gettingSchema(Optional<User> user) {
//        System.out.println(user.get().getCompany());
//        return Optional.ofNullable(user.get().getCompany().getTenant().getSchema());
//
//    }
//
//    public static String getSchema(Optional<User> user) {
//        Optional<String> schema = gettingSchema(user);
//
//        return schema.isPresent() ? schema.get() : null;
//    }
}
