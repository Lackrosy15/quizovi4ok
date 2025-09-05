package quizovichok.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PassQuizQuestion {
    String question;
    List<String> answerOptions;
    List<Integer> userAnswers;
    List<Integer> correctAnswers;
}
