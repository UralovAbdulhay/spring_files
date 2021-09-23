package com.example.lesson12.entity;// Author - Orifjon Yunusjonov 
// t.me/coderr24

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "users")
@Table
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 30)
    private String name;

    @Column(unique = true)
    private String email;

    private Long age;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp()
    private Date createAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private Date updateAt;

    public User(String name, String email, Long age) {

        this.age = age;
        this.email = email;
        this.name = name;
    }
}
