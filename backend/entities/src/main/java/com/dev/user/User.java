package com.dev.user;

import lombok.*;

import javax.persistence.*;
import java.util.Date;


@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "user_info")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String email;

    private String password;

    @Column(name = "created_time")
    private Date joinTime;

    @Column(name = "profileImgSrc")
    private String profilePicture;
}

