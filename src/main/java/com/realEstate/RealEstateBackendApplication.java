package com.realEstate;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RealEstateBackendApplication {
	@Value("${spring.mail.username:NOT_FOUND}")
	private static String mailUsername;

	@Value("${spring.mail.password:NOT_FOUND}")
	private static String mailPassword;

	public static void main(String[] args) {
		System.out.println("MAIL USERNAME: " + mailUsername);
		System.out.println("MAIL PASSWORD: " + mailPassword); // cuidado, solo para testeo
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing()
				.load();

		dotenv.entries().forEach(entry ->
				System.setProperty(entry.getKey(), entry.getValue())
		);
		SpringApplication.run(RealEstateBackendApplication.class, args);
	}

}
