package fr.uphf.formations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SeancesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeancesApplication.class, args);
	}

}
