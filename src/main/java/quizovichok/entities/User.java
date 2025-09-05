package quizovichok.entities;

import jakarta.persistence.Entity;
import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users", schema = "quizovi4ok")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String name;
    String login;
    String password;

    @Enumerated(EnumType.STRING)
    Roles role;
}