package com.example.servicecompany.repository;

import com.example.servicecompany.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
