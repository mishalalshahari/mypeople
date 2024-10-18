package com.mypeople.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "user")
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String userId;
    @Column(name = "user_name", nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String phoneNumber;
    private String password;
    @Lob
    private String about;
    @Lob
    private String profilePic;

    //information
    private boolean enabled = false;
    private boolean emailVerified = false;
    private boolean phoneNumberVerified = false;

    //Self, Google, Facebook, Twitter, LinkedIn, Github
    private Providers provider = Providers.SELF;
    private String providerUserId;

    //add more fields if needed
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();
}
