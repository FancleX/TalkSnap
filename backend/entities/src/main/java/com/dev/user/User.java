package com.dev.user;

import com.sun.istack.NotNull;
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

    @NotNull
    private String nickname;

    @NotNull
    private String email;

    @NotNull
    private String password;

    // md5 salt for password
    private String salt;

    @NotNull
    @Column(name = "created_time")
    private Date joinTime;

    @Column(name = "profileImgSrc")
    private String profilePicture;
}

