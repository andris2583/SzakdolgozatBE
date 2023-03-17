package com.szte.szakdolgozat.model.auth;

import com.szte.szakdolgozat.model.SubscriptionType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "user")
public class User {
    @Id
    String id;
    String username;
    String email;
    String password;
    SubscriptionType subscriptionType;
    private Set<UserRole> roles = new HashSet<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}