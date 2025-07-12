package jordan.university.gradproject2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GradProject2Application {

    public static void main(String[] args) {
        SpringApplication.run(GradProject2Application.class, args);
    }

}
