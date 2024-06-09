package mingeso.proyecto.autofix_ordenes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AutofixOrdenesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutofixOrdenesApplication.class, args);
	}

}
