package com.vogoun.it_num_assessment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vogoun.it_num_assessment.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    boolean existsByUsername(String username);
}
