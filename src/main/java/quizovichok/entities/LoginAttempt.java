package quizovichok.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "login_attempts", schema = "quizovi4ok")
public class LoginAttempt {
    @Id
    @Column(name = "login", nullable = false)
    String login;
    int countOfAttempts;
    LocalDateTime lastAttemptsTime;
}
