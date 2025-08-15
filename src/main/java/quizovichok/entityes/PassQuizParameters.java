package quizovichok.entityes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PassQuizParameters {
    private UUID passId;
    private UUID quizId;
    private String quizName;
    private String quizCategory;
    private String quizImgUrl;
    private UUID userId;
    private String userName;
    private String userLogin;
    private List<PassQuizQuestion> questionList;
    private LocalDateTime createdAt;
}
