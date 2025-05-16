package testovichok.entityes;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;
@Getter
@AllArgsConstructor
public class QuizParameters {
    private String name;
    private String category;
    private String imgUrl;
    private User user;
}
