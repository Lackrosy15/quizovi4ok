package testovichok.entityes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Quiz {
    private UUID quizId;
    private String name;
    private String category;
    private String imgUrl;
    private UUID userId;
    private LocalDateTime createdAt;
    private List<Question> questionList;
}
