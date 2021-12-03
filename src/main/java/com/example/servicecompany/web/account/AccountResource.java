package com.example.servicecompany.web.account;


import com.example.servicecompany.domain.*;
import com.example.servicecompany.repository.UserRepository;
import com.example.servicecompany.security.SecurityUtils;
import com.example.servicecompany.service.dto.PasswordChangeDTO;
import com.example.servicecompany.service.exception.EmailAlreadyUsedException;
import com.example.servicecompany.service.exception.InvalidPasswordException;
import com.example.servicecompany.service.UserService;
import com.example.servicecompany.service.dto.UserDTO;
import com.example.servicecompany.web.errors.LoginAlreadyUsedException;
import com.example.servicecompany.web.vm.KeyAndPasswordVM;
import com.example.servicecompany.web.vm.ManagedUserVM;
import com.example.servicecompany.web.vm.PhoneMail;
import com.fasterxml.uuid.Generators;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserRepository userRepository;

    private final UserService userService;


    public AccountResource(UserRepository userRepository, UserService userService) { //,

        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM){
        log.info("registeeeer");

        //      String password =   RandomStringUtils.randomAlphanumeric(8).toLowerCase();

        if (!checkPasswordLength(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }

        //    UUID version 1
        UUID uuid1 = Generators.timeBasedGenerator().generate();
        String login = "ui-"+uuid1.node();
//        int randomNum = ThreadLocalRandom.current().nextInt(1000, 10000);
//        String code = String.valueOf(randomNum);
//        String message = "code: "+ code+" "+"login: "+login+" "+"password: "+password;


        userService.registerUser(managedUserVM, managedUserVM.getPassword(), login);

     //   mailService.sendActivationEmail(user);
        //User.pwd2=password;
        //    User.setPwd2(password);
        //test if want validation by phone or by mail
        // if(managedUserVM.getByPhone() == true) {
//
//        String phone = "+216" + managedUserVM.getPhone();
//        SmsRequest smsRequest = new SmsRequest(phone, code);
//        service.sendSms(smsRequest);
//
//        TwilioClass resutTwilio = twilioClassRepository.findByEmail(managedUserVM.getEmail());
//        if (resutTwilio == null) {
//            TwilioClass testTwilio = new TwilioClass();
//            testTwilio.codeTwilio(code);
//            testTwilio.email(managedUserVM.getEmail());
//            twilioClassRepository.save(testTwilio);
//        } else {
//            resutTwilio.setCodeTwilio(code);
//            twilioClassRepository.save(resutTwilio);
//        }

        //   mailService.sendActivationEmail(user);


    }



    /**
     * {@code POST  /register} : register the user.
     *
     * @param phoneMail the managed user View Model.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is already used.
     */
    @PostMapping("/verifyPhoneIdentity")
    public void verifyPhoneIdentity(@Valid @RequestBody PhoneMail phoneMail){

        System.out.println(phoneMail.getEmail());
        System.out.println(phoneMail.getPhone());
        log.info("registeeeer");

          int randomNum = ThreadLocalRandom.current().nextInt(1000, 10000);
          String code = String.valueOf(randomNum);

    String phone = "+216" + phoneMail.getPhone();
 //   SmsRequest smsRequest = new SmsRequest(phone, code);

        String newNumberPhone = phoneMail.getPhone().replace(" ", "");
        String identifiantUpperCase = phoneMail.getIdentifiant().toUpperCase();
        System.out.println(newNumberPhone);

        Optional<User> user = userRepository.findByEmail(phoneMail.getEmail());


        String winsms = "https://www.winsmspro.com/sms/sms/api?action=send-sms&api_key=bWFwc2l0LmluZm9AZ21haWwuY29tOk1hcFNJVDE2ODczMjIqKio=&to=216" + newNumberPhone + "&from=COMPAKTOR&sms=Cher(e) client " + identifiantUpperCase + ", Bienvenue sur COMPAKTOR! votre code d'inscription est " + code;

        if(phoneMail.getLang().equals("fr")) {

            winsms = "https://www.winsmspro.com/sms/sms/api?action=send-sms&api_key=bWFwc2l0LmluZm9AZ21haWwuY29tOk1hcFNJVDE2ODczMjIqKio=&to=216" + newNumberPhone + "&from=COMPAKTOR&sms=Cher client(e) " + phoneMail.getIdentifiant() + ", Bienvenue chez COMPAKTOR ! votre code d’inscription est: "+code+".";
        }
        else
        if(phoneMail.getLang().equals("en")){

            winsms = "https://www.winsmspro.com/sms/sms/api?action=send-sms&api_key=bWFwc2l0LmluZm9AZ21haWwuY29tOk1hcFNJVDE2ODczMjIqKio=&to=216" + newNumberPhone + "&from=COMPAKTOR&sms=Dear " + phoneMail.getIdentifiant() + " customer, Welcome to COMPAKTOR! your registration code is: "+code+".";

        }
        else
        {
            winsms = "https://www.winsmspro.com/sms/sms/api?action=send-sms&api_key=bWFwc2l0LmluZm9AZ21haWwuY29tOk1hcFNJVDE2ODczMjIqKio=&to=216" + newNumberPhone + "&from=COMPAKTOR&sms=" +
                    "عزيزي المستخدم "
                    + phoneMail.getIdentifiant()
                    + " مرحبا بكم في COMPAKTOR!  " +
                     "رمز التسجيل الخاص بكم هو: " +code;


           /* winsms2 = "https://www.winsmspro.com/sms/sms/api?action=send-sms&api_key=bWFwc2l0LmluZm9AZ21haWwuY29tOk1hcFNJVDE2ODczMjIqKio=&to=216" + newNumberPhone + "&from=COMPAKTOR&sms="+
                    "عزيزي المستخدم "*/


        }


   //     service.sendSms(smsRequest);

//    TwilioClass resutTwilio = twilioClassRepository.findByEmail(phoneMail.getEmail());
//    if (resutTwilio == null) {
//        TwilioClass testTwilio = new TwilioClass();
//        testTwilio.codeTwilio(code);
//        testTwilio.email(phoneMail.getEmail());
//        twilioClassRepository.save(testTwilio);
//    } else {
//        resutTwilio.setCodeTwilio(code);
//        twilioClassRepository.save(resutTwilio);
//    }

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<String>(headers);

         ///restTemplate.exchange(winsms, HttpMethod.GET, entity, String.class).getBody();


    }


//    @PostMapping("/registerOwner")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void registerOwner(@Valid @RequestBody ManagedUserVM managedUserVM){
//        log.info("registeeeer owner");
//
//        String password =   RandomStringUtils.randomAlphanumeric(8).toLowerCase();
//        User.setPwd(password);
////        if (!checkPasswordLength(password)) {
////            throw new InvalidPasswordException();
////        }
//
//        //    UUID version 1
//        UUID uuid1 = Generators.timeBasedGenerator().generate();
//        String login = "ui-"+uuid1.node();
//
//        User user = userService.registerUser(managedUserVM, password, managedUserVM.getCompanyName(), login);
//
//        mailService.sendActivationEmailToSimpleUser(user);
//
//
//    }


    /**
     * {@code GET  /activate} : activate the registered user.
     *
     * @param key the activation key.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be activated.
     */
    @GetMapping("/activate")
    public void activateAccount(@RequestParam(value = "key") String key) {
        System.out.println(key);
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this activation key");
        }
    }

    /**
     * {@code GET  /authenticate} : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request.
     * @return the login if the user is authenticated.
     */
    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * {@code GET  /account} : get the current user.
     *
     * @return the current user.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be returned.
     */
    @GetMapping("/account")
    public UserDTO getAccount() {
        return userService.getUserWithAuthorities()
            .map(UserDTO::new)
            .orElseThrow(() -> new AccountResourceException("User could not be found"));
    }

    /**
     * {@code POST  /account} : update the current user information.
     *
     * @param userDTO the current user information.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user login wasn't found.
     */
    @PostMapping("/account")
    public void saveAccount(@Valid @RequestBody UserDTO userDTO) {

        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new AccountResourceException("User could not be found");
        }
        userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
                userDTO.getLangKey(), userDTO.getImageUrl(), userDTO.getImage(), userDTO.getAvatar(), userDTO.getDateBirthday(),userDTO.getDescription());
    }

    /**
     * {@code POST  /account/change-password} : changes the current user's password.
     *
     * @param passwordChangeDto current and new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the new password is incorrect.
     */
    @PostMapping(path = "/account/change-password")
    public void changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
        if (!checkPasswordLength(passwordChangeDto.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
    }

    /**
     * {@code POST   /account/reset-password/init} : Send an email to reset the password of the user.
     *
     * @param mail the mail of the user.
     */
//    @PostMapping(path = "/account/reset-password/init")
//    public void requestPasswordReset(@RequestBody String mail) {
//        Optional<User> user = userService.requestPasswordReset(mail);
//        if (user.isPresent()) {
//            mailService.sendPasswordResetMail(user.get());
//        } else {
//            // Pretend the request has been successful to prevent checking which emails really exist
//            // but log that an invalid attempt has been made
//            log.warn("Password reset requested for non existing mail");
//        }
//    }

    /**
     * {@code POST   /account/reset-password/finish} : Finish to reset the password of the user.
     *
     * @param keyAndPassword the generated key and the new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the password could not be reset.
     */
    @PostMapping(path = "/account/reset-password/finish")
    public void finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<User> user =
            userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this reset key");
        }
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }



 }
