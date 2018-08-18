package org.rajnegi.spring.rest.webservices.springrestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringRestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRestServiceApplication.class, args);
	}
	
	/*@Bean
	public LocaleResolver localResolver() {
		AcceptHeaderLocaleResolver locale = new AcceptHeaderLocaleResolver();
		locale.setDefaultLocale(Locale.US);
		return locale;
	}*/
	
}
