package quizovichok.entityes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "quizzes", schema = "quizovi4ok")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID quizId;

    private String name;
    private String category;
    private String imgUrl;
    private UUID userId;
    private LocalDateTime createdAt;

    @Column(columnDefinition = "jsonb")
    private List<Question> questionList;
}
