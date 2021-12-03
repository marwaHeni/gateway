package com.example.servicecompany.config.apidoc;

import com.example.servicecompany.domain.Authority;
import com.example.servicecompany.domain.User;
import com.example.servicecompany.repository.AuthorityRepository;
import com.example.servicecompany.repository.UserRepository;
import com.example.servicecompany.security.AuthoritiesConstants;
import com.example.servicecompany.service.RandomUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
@Transactional
public class ClassInit {


    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public ClassInit(AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void createRole() {

        Authority authority = new Authority();
        authority.setName("ROLE_ADMIN");
        authorityRepository.save(authority);


        Authority authority2 = new Authority();
        authority2.setName("ROLE_COMMUNITY");
        authorityRepository.save(authority2);

    }


    public void createUsers() {

        List<User> userList = userRepository.findAll();
        if (userList.size() == 0) {
            //omrani
            User omrani = new User();
            String encryptedPassword = passwordEncoder.encode("admin2020");
            omrani.setLogin("ui-100893882143169");
            // new user gets initially a generated password
            omrani.setPassword(encryptedPassword);
            omrani.setFirstName("Mohamed Amine");
            omrani.setLastName("Omrani");
            omrani.setEmail("omranimohamedamine@gmail.com");
            omrani.setPhone("21636339");
            omrani.setIsAdmin(false);
            omrani.setLangKey("fr");
            // new user is active
            omrani.setActivated(true);
            // new user gets registration key
            omrani.setActivationKey(RandomUtil.generateActivationKey());
            Set<Authority> authorities = new HashSet<>();
            authorityRepository.findById(AuthoritiesConstants.Admin).ifPresent(authorities::add);

            omrani.setAuthorities(authorities);

            DateTimeFormatter formatter = DateTimeFormatter
                    .ofPattern("yyyy-MM-dd HH:mm:ss")
                    .withLocale(Locale.getDefault())
                    .withZone(ZoneId.systemDefault());

            Instant now = Instant.now();
            String formatted = formatter.format(now);
            omrani.setDateCreation(formatted);

            userRepository.save(omrani);

        }
    }


    public void initialize(){

        this.createRole();
        this.createUsers();
    }
}
