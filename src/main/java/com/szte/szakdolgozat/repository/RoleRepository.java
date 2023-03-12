package com.szte.szakdolgozat.repository;

import com.szte.szakdolgozat.model.auth.UserRole;
import com.szte.szakdolgozat.model.auth.UserRoleEnum;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<UserRole, String> {
    Optional<UserRole> findByName(UserRoleEnum name);
}