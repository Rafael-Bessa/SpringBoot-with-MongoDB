package bessa.morangon.rafael.api;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ControleFamiliarApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleFamiliarApplication.class, args);
	}

	@Bean
	public ModelMapper criaModelMapper(){
		return new ModelMapper();
	}


}
