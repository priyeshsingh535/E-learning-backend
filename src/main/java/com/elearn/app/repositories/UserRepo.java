package com.elearn.app.repositories;

import com.elearn.app.entities.Course;
import com.elearn.app.entities.User;
import com.elearn.app.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User, String> {

    Optional<User> findByEmail(String Email);

}
