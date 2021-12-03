package com.example.servicecompany.repository;

import com.example.servicecompany.domain.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    String USERS_BY_EMAIL_CACHE = "usersByEmail";

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByLoginIgnoreCase(String login);

    Optional<User> findOneByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    Optional<User> findOneWithAuthoritiesByLogin(String login);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByLoginIgnoreCase(String login);

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
    Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    Page<User> findAllByLoginNot(Pageable pageable, String login);


    Optional<User>findById(Long id);


    Optional<User>findByEmail(String email);

    //number of users annual
    @Query("select count(u) from User u where (extract('year' from current_date) = extract('year' from u.createdDate))")
    Long number_users_annual();

    //number of users monthly
    @Query("select count(u) from User u where (extract('MONTH' from u.createdDate) = extract('MONTH' from current_date)) and (extract('year' from current_date) = extract('year' from u.createdDate))")
    Long number_users_monthly();



    //number of users weekly
    @Query("select count(u) from User u where (extract('week' from u.createdDate) = extract('week' from current_date)) and (extract('year' from current_date) = extract('year' from u.createdDate))")
    Long number_users_weekly();

    //number of users daily
    @Query("select count(u) from User u where (to_char(u.createdDate , 'YYYY-MM-DD') = to_char(current_date,'YYYY-MM-DD'))")
    Long number_users_daily();


    //number of users total
    @Query("select count(u) from User u")
    Long number_users_total();



    //list of users total
    @Query("select u from User u")
    List<User> list_users_total();

    //list of users annual
    @Query("select u from User u where (extract('year' from current_date) = extract('year' from u.createdDate))")
    List<User> list_users_annual();

    //number of users monthly
    @Query("select u from User u where (extract('MONTH' from u.createdDate) = extract('MONTH' from current_date)) and (extract('year' from current_date) = extract('year' from u.createdDate))")
    List<User> list_users_monthly();


    //list of users weekly
    @Query("select u from User u where (extract('week' from u.createdDate) = extract('week' from current_date)) and (extract('year' from current_date) = extract('year' from u.createdDate))")
    List<User> list_users_weekly();

    //number of users daily
    @Query("select u from User u where (to_char(u.createdDate , 'YYYY-MM-DD') = to_char(current_date,'YYYY-MM-DD'))")
    List<User> list_users_daily();


}
