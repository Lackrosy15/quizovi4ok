package testovichok.entityes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
