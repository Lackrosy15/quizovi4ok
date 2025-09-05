package quizovichok.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PassQuizParameters {
    UUID quizId;
    String quizName;
    String quizCategory;
    String quizImgUrl;
    UUID userId;
    String userName;
    String userLogin;
    List<PassQuizQuestion> questionList;
    LocalDateTime createdAt;
}
