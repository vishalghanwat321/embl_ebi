package com.org.web.embl_ebi.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @apiNote Swagger can be used as digital document or api simulator to test
 *          API's.
 * @implNote This is swagger configuration class. We can use swagger to test
 *           API's. Link to access the same
 *           http://localhost:8082/swagger-ui.html
 * @link <https://swagger.io/>
 * @since 2020
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket apiDocket() {

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.org.web.embl_ebi")).paths(PathSelectors.any()).build();
	}

	private ApiInfo getApiInfo() {

		return new ApiInfoBuilder().title("Swagger API doc to maintain personal details...")
				.description("More description about the API").version("1.0.0").build();
	}
}
