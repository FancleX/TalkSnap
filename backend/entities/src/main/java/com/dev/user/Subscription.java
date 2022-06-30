package com.dev.user;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_subscriptions")
public class Subscription implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sequenceId;

    private Long friendId;

    private String friendName;

    public Subscription(Long userId, String nickname) {
        this.friendId = userId;
        this.friendName = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return friendId.equals(that.friendId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(friendId);
    }
}
