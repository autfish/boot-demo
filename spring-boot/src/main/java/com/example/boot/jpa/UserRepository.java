package com.example.boot.jpa;

import com.example.boot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT t FROM User t WHERE t.sex=?1 and t.age=?2")
    List<User> findBySexAndAge(String sex, int age);
}
