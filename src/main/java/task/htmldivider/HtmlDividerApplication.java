package task.htmldivider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HtmlDividerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HtmlDividerApplication.class, args);
	}

}
