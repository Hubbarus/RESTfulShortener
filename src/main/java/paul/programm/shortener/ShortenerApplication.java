package paul.programm.shortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2

public class ShortenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortenerApplication.class, args);
	}


	//Swagger Configuration methods
	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("paul.programm"))
				.build()
				.apiInfo(apiDetails());
	}

	private springfox.documentation.service.ApiInfo apiDetails() {
		return new ApiInfo(
				"Shortener API",
				"CV code example",
				"1.0",
				"Free to use",
				new Contact("Paul Ponomarev", "", "rocksquare@gmail.com"),
				"WTFPL",
				"http://www.wtfpl.net/"
		);
	}
}
