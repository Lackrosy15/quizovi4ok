package testovichok.entityes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@NoArgsConstructor
public class LoginAttempt {
    private AtomicInteger countOfAttempts = new AtomicInteger(0);
    private LocalDateTime lastAttemptsTime;

    public synchronized void incrementAttempts() {
        countOfAttempts.incrementAndGet();
        lastAttemptsTime = LocalDateTime.now();
    }

    public boolean isBlocked() {
        return countOfAttempts.get() > 3;
    }

    public boolean isBlockedExpired() {
        if (lastAttemptsTime == null) {
            lastAttemptsTime = LocalDateTime.now();
        }
        return LocalDateTime.now().isAfter(lastAttemptsTime.plus(5, ChronoUnit.MINUTES));
    }

}
