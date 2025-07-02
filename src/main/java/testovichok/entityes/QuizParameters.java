package testovichok.entityes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class QuizParameters {
    private String name;
    private String category;
    private String imgUrl;
    private User user;
    private LocalDateTime createdAt;
    private List<Question> questionList;
}
