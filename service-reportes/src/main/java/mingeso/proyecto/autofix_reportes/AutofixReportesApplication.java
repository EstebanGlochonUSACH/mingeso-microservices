package mingeso.proyecto.autofix_reportes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AutofixReportesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutofixReportesApplication.class, args);
	}

}
