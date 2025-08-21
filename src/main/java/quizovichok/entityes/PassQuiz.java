package quizovichok.entityes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pass_quizzes", schema = "quizovi4ok")
public class PassQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID passId;

    private UUID quizId;
    private String quizName;
    private String quizCategory;
    private String quizImgUrl;
    private UUID userId;
    private String userName;
    private String userLogin;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<PassQuizQuestion> questionList;

    private LocalDateTime createdAt;
}
