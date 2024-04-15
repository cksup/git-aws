package com.twd.SpringSecurityJWT.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String father;
    private String address;
    private String telno;
    private String numberth;
    private String birthday;
}
