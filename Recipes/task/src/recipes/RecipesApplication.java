package recipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class RecipesApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecipesApplication.class, args);
    }
}