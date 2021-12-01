package pl.lodz.p.it.gymbackend.servicestarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "pl.lodz.p.it.*")
@EnableJpaRepositories("pl.lodz.p.it.*")
@EnableJpaAuditing
@EnableScheduling
@EntityScan("pl.lodz.p.it.repositoryhibernate.entity")
@EnableRetry
public class GymBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymBackendApplication.class, args);
    }

}
