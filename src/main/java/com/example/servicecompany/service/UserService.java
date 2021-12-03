package com.example.servicecompany.service;

import com.example.servicecompany.config.Constants;
import com.example.servicecompany.config.StorageProperties;
import com.example.servicecompany.domain.*;
import com.example.servicecompany.repository.*;
import com.example.servicecompany.service.dto.UserDTO;
import com.example.servicecompany.security.AuthoritiesConstants;
import com.example.servicecompany.security.SecurityUtils;
import com.example.servicecompany.service.exception.EmailAlreadyUsedException;
import com.example.servicecompany.service.exception.InvalidPasswordException;
import com.example.servicecompany.service.exception.TenantNameAlreadyUsedException;
import com.example.servicecompany.service.exception.UsernameAlreadyUsedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {


    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    private final StorageProperties storageProps;

    //  private final CacheManager cacheManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, StorageProperties storageProps) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.storageProps = storageProps;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
                .map(user -> {
                    // activate given user for the registration key.
                    user.setActivated(true);
                    //user.setActivationKey(null);
                    // this.clearUserCaches(user);
                    log.debug("Activated user: {}", user);
                    return user;
                });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        return userRepository.findOneByResetKey(key)
                .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setResetKey(null);
                    user.setResetDate(null);
                    //  this.clearUserCaches(user);
                    return user;
                });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmailIgnoreCase(mail)
                .filter(User::getActivated)
                .map(user -> {
                    user.setResetKey(RandomUtil.generateResetKey());
                    user.setResetDate(Instant.now());
                    // this.clearUserCaches(user);
                    return user;
                });
    }

    public User registerUser(UserDTO userDTO, String password, String login){

        userRepository.findOneByLogin(login.toLowerCase()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new UsernameAlreadyUsedException();
            }
        });
        userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new EmailAlreadyUsedException();
            }
        });
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(login.toLowerCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            newUser.setEmail(userDTO.getEmail().toLowerCase());
        }

        //create schema
//        String nameCompanywithoutSpace = companyName.trim();
//        String name_Company = nameCompanywithoutSpace.replaceAll(" ", "_");

     //   String schemaName = "sh"+uuidCompany.replace("-", "");
        newUser.setIsCommunity(true);



        newUser.setImageUrl(userDTO.getImageUrl());
        String phoneWithoutSpace = userDTO.getPhone().replace(" ", "");
        newUser.setPhone(phoneWithoutSpace);
        newUser.setLangKey(userDTO.getLangKey());
        // new user is not active
        newUser.setActivated(true);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.Community).ifPresent(authorities::add);
//        authorityRepository.findById(AuthoritiesConstants.DASHBOARD).ifPresent(authorities::add);
//        authorityRepository.findById(AuthoritiesConstants.SALE).ifPresent(authorities::add);
//        authorityRepository.findById(AuthoritiesConstants.PURCHASE).ifPresent(authorities::add);
//        authorityRepository.findById(AuthoritiesConstants.STOCK).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);


        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss")
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault());

        Instant now = Instant.now();
        String formatted =  formatter.format(now);
        newUser.setDateCreation(formatted);


        userRepository.save(newUser);
        //this.clearUserCaches(newUser);
        log.debug("Created Information for User: {}", newUser);

//
//        Connection conn = dataSource.getConnection();
//        String schemaname = name_Company.toLowerCase();
//        conn.createStatement().execute("CREATE SCHEMA " + schemaname + " AUTHORIZATION postgres");
//
//        DriverManagerDataSource dataSource1 = new DriverManagerDataSource();
//        //Postgres database we are using
//        dataSource1.setDriverClassName("org.postgresql.Driver");
//        dataSource1.setUrl("jdbc:postgresql://localhost:5432/testOne?currentSchema="+schemaname);
//        dataSource1.setUsername("postgres");
//        dataSource1.setPassword("postgres");
//
//        Resource resource = new ClassPathResource("dataa.sql");
//        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
//        databasePopulator.execute(dataSource1);
//        //stmt.close();
//        conn.close();

        return newUser;
    }


//    public User registerSimpleUser(UserDTO userDTO, String password, String login){
//
//        System.out.println(userDTO);
//        userRepository.findOneByLogin(login.toLowerCase()).ifPresent(existingUser -> {
//            boolean removed = removeNonActivatedUser(existingUser);
//            if (!removed) {
//                throw new UsernameAlreadyUsedException();
//            }
//        });
//        userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).ifPresent(existingUser -> {
//            boolean removed = removeNonActivatedUser(existingUser);
//            if (!removed) {
//                throw new EmailAlreadyUsedException();
//            }
//        });
//        User newUser = new User();
//        String encryptedPassword = passwordEncoder.encode(password);
//        newUser.setLogin(login.toLowerCase());
//        // new user gets initially a generated password
//        newUser.setPassword(encryptedPassword);
//        newUser.setFirstName(userDTO.getFirstName());
//        newUser.setLastName(userDTO.getLastName());
//        if (userDTO.getEmail() != null) {
//            newUser.setEmail(userDTO.getEmail().toLowerCase());
//        }
//
//
//        newUser.setIsOwner(false);
//
//        //newUser.setTenant(tenant);
//
//
//        UUID managerId = SecurityUtils.getUserId();
//        User manager = userRepository.getOne(managerId);
//        newUser.setCompany(manager.getCompany());
//        //  newUser.setByPhone(byPhone);
//
//
//
//        newUser.setImageUrl(userDTO.getImageUrl());
//        String phoneWithoutSpace = userDTO.getPhone().replace(" ", "");
//        newUser.setPhone(phoneWithoutSpace);
//        newUser.setLangKey(userDTO.getLangKey());
//        // new user is active
//        newUser.setActivated(true);
//        // new user gets registration key
//        newUser.setActivationKey(RandomUtil.generateActivationKey());
//       User savedUser = userRepository.save(newUser);
//        //this.clearUserCaches(newUser);
//
//        Set<Authority> authorities = new HashSet<>();
//        authorityRepository.findById(AuthoritiesConstants.CompanyUser).ifPresent(authorities::add);
//        // newUser.setAuthorities(authorities);
//
//        Set<UserModule>userModules=userDTO.getUserModules();
//        System.out.println("size "+ userModules.size());
//        for(UserModule userModule: userModules) {
//            System.out.println("userModule "+userModule);
//            System.out.println("userModule.getModule() "+userModule.getModule());
//            UserModulePK userModulePK = new UserModulePK(savedUser.getId(), userModule.getModule().getName());
//            userModule.setId(userModulePK);
//            userModule.setUser(savedUser);
//            userModuleRepository.save(userModule);
//
////            //add user authorities
//            if(userModule.isActivated() && userModule.getModule().getName().equals("Eshop")){
//                authorityRepository.findById(AuthoritiesConstants.ESHOP).ifPresent(authorities::add);
//            }
//
//            if(userModule.isActivated() && userModule.getModule().getName().equals("Sale")){
//                authorityRepository.findById(AuthoritiesConstants.SALE).ifPresent(authorities::add);
//            }
//
//            if(userModule.isActivated() && userModule.getModule().getName().equals("Purchase")){
//                authorityRepository.findById(AuthoritiesConstants.PURCHASE).ifPresent(authorities::add);
//            }
//            if(userModule.isActivated() && userModule.getModule().getName().equals("Stock")){
//                authorityRepository.findById(AuthoritiesConstants.STOCK).ifPresent(authorities::add);
//            }
//            if(userModule.isActivated() && userModule.getModule().getName().equals("Treasury")){
//                authorityRepository.findById(AuthoritiesConstants.TREASURY).ifPresent(authorities::add);
//            }
//
//        }
//
//        savedUser.setAuthorities(authorities);
//
//        DateTimeFormatter formatter = DateTimeFormatter
//                .ofPattern("yyyy-MM-dd HH:mm:ss")
//                .withLocale(Locale.getDefault())
//                .withZone(ZoneId.systemDefault());
//
//        Instant now = Instant.now();
//        String formatted =  formatter.format(now);
//        newUser.setDateCreation(formatted);
//
//
//        userRepository.save(savedUser);
//
//        log.debug("Created Information for User: {}", newUser);
//
//
//        return savedUser;
//    }


    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.getActivated()) {
            return false;
        }
        //delete user
        userRepository.delete(existingUser);
        userRepository.flush();

        // this.clearUserCaches(existingUser);
        return true;
    }

    private boolean removeNonActivatedUserModified(User existingUser) {
        if (existingUser.getActivated()) {
            return true;
        }
        //delete user
        userRepository.delete(existingUser);
        userRepository.flush();
        // this.clearUserCaches(existingUser);
        return false;
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin().toLowerCase());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail().toLowerCase());
        }
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = userDTO.getAuthorities().stream()
                    .map(authorityRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }
        userRepository.save(user);
        // this.clearUserCaches(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }



    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update.
     * @return updated user.
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository
                .findById(userDTO.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> {
                    // this.clearUserCaches(user);
                    user.setLogin(userDTO.getLogin().toLowerCase());
                    user.setFirstName(userDTO.getFirstName());
                    user.setLastName(userDTO.getLastName());
                    if (userDTO.getEmail() != null) {
                        user.setEmail(userDTO.getEmail().toLowerCase());
                    }
                    user.setImageUrl(userDTO.getImageUrl());
                    user.setActivated(userDTO.isActivated());
                    user.setLangKey(userDTO.getLangKey());
                    Set<Authority> managedAuthorities = user.getAuthorities();
                    managedAuthorities.clear();
                    userDTO.getAuthorities().stream()
                            .map(authorityRepository::findById)
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .forEach(managedAuthorities::add);
                    // this.clearUserCaches(user);
                    log.debug("Changed Information for User: {}", user);
                    return user;
                })
                .map(UserDTO::new);
    }

    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            userRepository.delete(user);
            // this.clearUserCaches(user);
            log.debug("Deleted User: {}", user);
        });
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user.
     * @param lastName  last name of user.
     * @param email     email id of user.
     * @param langKey   language key.
     * @param imageUrl  image URL of user.
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl, String image, String avatar, String dateBirthday, String description) {
        System.out.println("imaaaaaage "+image);
        SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByLogin)
                .ifPresent(user -> {
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    if (email != null) {
                        user.setEmail(email.toLowerCase());
                    }
                    user.setLangKey(langKey);
                    user.setImageUrl(imageUrl);
                    user.setImage(image);
                    user.setDescription(description);
                    user.setDateBirthday(dateBirthday);
                    //  this.clearUserCaches(user);
                    log.debug("Changed Information for User: {}", user);


                    if (avatar != null) {

                        String path = storageProps.getPath();
                        String realPath = path.substring(7,path.length());
                        String currentDate = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
                        int locationofExtension = image.lastIndexOf('.');
                        String extension = image.substring(locationofExtension, image.length());
                        String nameWithoutExtension = image.substring(0, locationofExtension);
                        String newNameOfImage = nameWithoutExtension + currentDate +extension;
                        String pathImageUsers = storageProps.getUrl() + "/resources/compaktorGatewayImages" + "/users/" + newNameOfImage;
                        user.setImage(pathImageUsers);

                        String usersFolder = realPath+"/compaktorGatewayImages/users/"+newNameOfImage;


                        Path rootUsers = Paths.get(usersFolder);
                        byte[] scanBytes = Base64.getDecoder().decode(avatar);
                        try {
                            Files.write(rootUsers, scanBytes);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



    @Transactional
    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByLogin)
                .ifPresent(user -> {
                    String currentEncryptedPassword = user.getPassword();
                    if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                        throw new InvalidPasswordException();
                    }
                    String encryptedPassword = passwordEncoder.encode(newPassword);
                    user.setPassword(encryptedPassword);
                    // this.clearUserCaches(user);
                    log.debug("Changed password for User: {}", user);
                });
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findOneWithAuthoritiesByLogin);
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository
                .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
                .forEach(user -> {
                    log.debug("Deleting not activated user {}", user.getLogin());
                    userRepository.delete(user);
                    //  this.clearUserCaches(user);
                });
    }

    /**
     * Gets a list of all the authorities.
     * @return a list of all the authorities.
     */
    @Transactional(readOnly = true)
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }


//    private void clearUserCaches(User user) {
//        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
//        if (user.getEmail() != null) {
//            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
//        }
//    }


    public void desactivateUser(UUID userId) {
        User usertoDesactivate = userRepository.getOne(userId);
        usertoDesactivate.setActivated(false);
    }

    public void activateUser(UUID userId) {
        User usertoActivate = userRepository.getOne(userId);
        usertoActivate.setActivated(true);
    }



//    @Transactional(readOnly = true)
//    public Company getCompanyOfOwner(Long ownerId) {
//        Optional<User> owner = userRepository.findById(ownerId);
//        Company company = owner.get().getCompany();
//        return company;
//    }

    /**
     * Get one user by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<User> findOne(Long id) {
        log.debug("Request to get User : {}", id);
        return userRepository.findById(id);
    }


    public Boolean checkMail(String email) {
      Optional<User> user =  userRepository.findOneByEmailIgnoreCase(email);
        //System.out.println(user.get().getFirstName());

       /* userRepository.findOneByEmailIgnoreCase(email).ifPresent(existingUser -> {
            Boolean result = false;
             result =  removeNonActivatedUser(existingUser);

        });*/
        //return true;

        Boolean result = false;
        if(user.isPresent() ){
            result = removeNonActivatedUserModified(user.get());
        }

            return result;

    }



//    public Boolean updatePhone(UUID id, String oldPhone, String newPhone){
//
//
//
//        Optional<User> user = userRepository.findById(id);
//
//        String userPhone = user.get().getPhone();
//        System.out.println("userPhone "+ userPhone);
//        String oldPhonewithoutSpace = oldPhone.replace(" ", "");
//        System.out.println("oldPhonewithoutSpace "+ oldPhonewithoutSpace);
//
//        Boolean result = true;
//        if( ! userPhone.equals(oldPhonewithoutSpace)){
//            result =  false;
//        }
//
//        else {
//            String phoneNumber = newPhone.replace(" ", "");
//           // String newPhone216 = "+216" + phoneNumber;
//            int randomNum = ThreadLocalRandom.current().nextInt(1000, 10000);
//            String code = String.valueOf(randomNum);
////            SmsRequest smsRequest = new SmsRequest(newPhone216, code);
////            service.sendSms(smsRequest);
//
//  //          String winsms = "https://www.winsmspro.com/sms/sms/api?action=send-sms&api_key=bWFwc2l0LmluZm9AZ21haWwuY29tOk1hcFNJVDE2ODczMjIqKio=&to=216"+phoneNumber+"&from=COMPAKTOR&sms=Code de vérification"+code;
//
//            String lang = user.get().getLangKey();
//            String winsms;
//
//            if(lang.equals("fr")) {
//
//                winsms = "https://www.winsmspro.com/sms/sms/api?action=send-sms&api_key=bWFwc2l0LmluZm9AZ21haWwuY29tOk1hcFNJVDE2ODczMjIqKio=&to=216" + user.get().getPhone() + "&from=COMPAKTOR&sms=Cher client(e) " + user.get().getFirstName() + " " + user.get().getLastName() + ", la modification de votre numéro de téléphone sur COMPAKTOR est approuvée. Votre code de vérification est: "+ code +".";
//            }
//            else
//            if(lang.equals("en")){
//
//                winsms = "https://www.winsmspro.com/sms/sms/api?action=send-sms&api_key=bWFwc2l0LmluZm9AZ21haWwuY29tOk1hcFNJVDE2ODczMjIqKio=&to=216" + user.get().getPhone() + "&from=COMPAKTOR&sms=Dear " + user.get().getFirstName() + " " + user.get().getLastName() + ", the modification of your telephone number on COMPAKTOR is approved with success. Your verification code is: "+code+".";
//
//            }
//            else
//            {
//                winsms = "https://www.winsmspro.com/sms/sms/api?action=send-sms&api_key=bWFwc2l0LmluZm9AZ21haWwuY29tOk1hcFNJVDE2ODczMjIqKio=&to=216" + user.get().getPhone() + "&from=COMPAKTOR&sms=عزيزي المستخدم " + user.get().getFirstName() + " " + user.get().getLastName() + " ، تمت الموافقة على تعديل رقم هاتفك على COMPAKTOR بنجاح. رمز التحقق الخاص بك هو: " +
//                        code+".";
//            }
//
//
//            System.out.println(winsms);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//            HttpEntity<String> entity = new HttpEntity<String>(headers);
//
//            restTemplate.exchange(winsms, HttpMethod.GET, entity, String.class).getBody();
//
//            TwilioClass resutTwilio = twilioClassRepository.findByEmail(user.get().getEmail());
//            if (resutTwilio == null) {
//                TwilioClass testTwilio = new TwilioClass();
//                testTwilio.codeTwilio(code);
//                testTwilio.email(user.get().getEmail());
//                twilioClassRepository.save(testTwilio);
//            } else {
//                resutTwilio.setCodeTwilio(code);
//                twilioClassRepository.save(resutTwilio);
//            }
//        }
//
//        return result;
//    }


//    public Boolean updateEmail(UUID id, String oldEmail, String newEmail){
//        System.out.println(oldEmail);
//        System.out.println(newEmail);
//        Optional<User> user = userRepository.findById(id);
//
//        String userEmail = user.get().getEmail();
//
//        Boolean result = true;
//        if( ! userEmail.equals(oldEmail)){
//            result =  false;
//        }
//
//        else {
//            int randomNum = ThreadLocalRandom.current().nextInt(1000, 10000);
//            String code = String.valueOf(randomNum);
//
//            TwilioClass resutTwilio = twilioClassRepository.findByEmail(user.get().getEmail());
//            if (resutTwilio == null) {
//                TwilioClass testTwilio = new TwilioClass();
//                testTwilio.codeTwilio(code);
//                testTwilio.email(user.get().getEmail());
//                twilioClassRepository.save(testTwilio);
//            } else {
//                resutTwilio.setCodeTwilio(code);
//                twilioClassRepository.save(resutTwilio);
//            }
//
//            user.get().setCode(code);
//
//            userRepository.save(user.get());
//            mailService.sendUpdatingEmail(user.get(), newEmail);
//
//        }
//
//        return result;
//    }


}
