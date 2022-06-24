package com.dev.user;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "user_info")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String email;

    private String password;

    // md5 salt for password
    private String salt;

    @Column(name = "created_time")
    private Date joinTime;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "profileImg")
    private byte[] profileImg;

    private String bio;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "bgImg")
    private byte[] backgroundImg;

}

