package quizovichok.entityes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangePasswordCredentials {
    String currentPassword;
    String newPassword;
}
