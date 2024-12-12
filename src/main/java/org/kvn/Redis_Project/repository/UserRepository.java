package org.kvn.Redis_Project.repository;

import org.kvn.Redis_Project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
