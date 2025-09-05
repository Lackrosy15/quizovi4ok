package quizovichok.entities;

import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "pass_quizzes", schema = "quizovi4ok")
public class PassQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID passId;

    UUID quizId;
    String quizName;
    String quizCategory;
    String quizImgUrl;
    UUID userId;
    String userName;
    String userLogin;

    @JdbcTypeCode(SqlTypes.JSON)
    List<PassQuizQuestion> questionList;

    LocalDateTime createdAt;
}
