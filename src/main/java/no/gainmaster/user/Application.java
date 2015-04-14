package no.gainmaster.user;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import static org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

@SpringBootApplication
@EnableHypermediaSupport(type = HypermediaType.HAL)
@EnableRabbit
public class Application {

    public static void main(String[] args) {
    	SpringApplication app = new SpringApplication(Application.class);
        app.setShowBanner(false);
    	app.run(args);
    }

}
