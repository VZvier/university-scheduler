package ua.foxminded.university;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ua.foxminded.university.service.data.DatabaseFilling;

@ConditionalOnProperty(
        prefix = "application.runner",
        value = "enabled",
        havingValue = "true",
        matchIfMissing = true)
@Component
public class AppRunner implements ApplicationRunner {

    private final DatabaseFilling dbFilling;

    public AppRunner(DatabaseFilling dbFilling){
        this.dbFilling = dbFilling;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        dbFilling.checkDBAndFillIfRequired();
    }
}
