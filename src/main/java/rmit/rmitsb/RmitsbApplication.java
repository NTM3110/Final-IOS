package rmit.rmitsb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RmitsbApplication {

	public static void main(String[] args) {
		SpringApplication.run(RmitsbApplication.class, args);
	}
}
