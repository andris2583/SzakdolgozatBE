package com.szte.szakdolgozat.repository;

import com.szte.szakdolgozat.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User,String> {

}
