package com.szte.szakdolgozat.model.auth;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
@Data
public class UserRole {
    @Id
    private String id;

    private UserRoleEnum name;

    public UserRole() {
    }

    public UserRole(UserRoleEnum name) {
        this.name = name;
    }
}
