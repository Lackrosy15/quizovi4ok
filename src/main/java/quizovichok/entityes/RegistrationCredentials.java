package quizovichok.entityes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegistrationCredentials {
    private String name;
    private String login;
    private String password;
}
