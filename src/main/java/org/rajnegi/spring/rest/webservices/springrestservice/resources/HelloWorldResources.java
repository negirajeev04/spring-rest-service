package org.rajnegi.spring.rest.webservices.springrestservice.resources;

import org.rajnegi.spring.rest.webservices.springrestservice.bean.HelloWorldBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldResources {
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping(value="/hello-world")
	public String sayHello() {
		return messageSource.getMessage("good.morning", null, LocaleContextHolder.getLocale());
	}
	
	@GetMapping(value="/hello-world-bean")
	public HelloWorldBean sayHelloBean() {
		return new HelloWorldBean("Hello-world");
	}
	
	@GetMapping(value="hello-user/{name}")
	public HelloWorldBean sayHelloBeanUser(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello-world, %s", name));
	}
}
