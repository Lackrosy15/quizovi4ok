package testovichok.entityes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private String question;
    private List<String> answerOptions;
    private List<Integer> correctAnswers;
}
