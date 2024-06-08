package mingeso.proyecto.autofix_eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class AutofixEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutofixEurekaApplication.class, args);
	}

}
