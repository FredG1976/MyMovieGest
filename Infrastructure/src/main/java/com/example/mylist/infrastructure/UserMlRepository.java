package com.example.mylist.infrastructure;

import com.example.mylist.domain.usermylist.UserMl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMlRepository extends JpaRepository<UserMl, Long> {
    boolean existsByLogin(String login);
    Optional<UserMl> findByLogin(String login);
}
