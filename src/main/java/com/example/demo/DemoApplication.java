package com.example.demo;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}

	@Bean
	CommandLineRunner commandLineRunner(JsonPlaceholderService service) {
		return args -> service.posts().stream().forEach(System.out::println);
	}

	@Bean
	JsonPlaceholderService jsonPlaceholderService() {
		WebClient client = WebClient.builder().baseUrl("https://jsonplaceholder.typicode.com/").build();
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();

		return factory.createClient(JsonPlaceholderService.class);

	}

}

interface JsonPlaceholderService {

	@GetExchange("/posts")
	List<Post> posts();

}

record Post(Integer id, Integer UserId, String title, String body) {
}
