package pl.lodz.p.it.gymbackend.servicestarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class}, scanBasePackages = "pl.lodz.p.it.*")
@EnableJpaRepositories("pl.lodz.p.it.*")
@EntityScan("pl.lodz.p.it.repositoryhibernate.entity")
public class GymBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymBackendApplication.class, args);
    }

}
