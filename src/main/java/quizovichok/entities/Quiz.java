package quizovichok.entities;

import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "quizzes", schema = "quizovi4ok")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID quizId;

    String name;
    String category;
    String imgUrl;
    UUID userId;
    LocalDateTime createdAt;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    List<Question> questionList;
}
