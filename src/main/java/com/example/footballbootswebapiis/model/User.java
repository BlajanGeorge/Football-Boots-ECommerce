package com.example.footballbootswebapiis.model;

import com.example.footballbootswebapiis.enumlayer.Role;
import com.example.footballbootswebapiis.mappers.RoleConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Validated
@Entity
@Table(name = "user")
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String firstName;
    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String lastName;
    @Column(unique = true, nullable = false, columnDefinition = "varchar(255)")
    private String email;
    @Column(nullable = false, columnDefinition = "varchar(1)")
    private String gender;
    @Column(nullable = false, columnDefinition = "varchar(255)")
    private int age;
    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String password;
    @Column(nullable = false)
    @Convert(converter = RoleConverter.class)
    private Role role;

    public User(String firstName, String lastName, String email, String gender, int age, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.password = password;
        this.role = role;
    }
}
