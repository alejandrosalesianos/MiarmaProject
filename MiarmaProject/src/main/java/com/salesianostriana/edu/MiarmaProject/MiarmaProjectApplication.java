package com.salesianostriana.edu.MiarmaProject;

import com.salesianostriana.edu.MiarmaProject.config.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(StorageProperties.class)
@SpringBootApplication
public class MiarmaProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiarmaProjectApplication.class, args);
	}

}
