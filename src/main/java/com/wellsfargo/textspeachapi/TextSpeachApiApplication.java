package com.wellsfargo.textspeachapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class TextSpeachApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TextSpeachApiApplication.class, args);
	}

	@Bean
	public CorsFilter corsFilter(){
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(false);
		configuration.setAllowedOrigins(Collections.singletonList("*"));
		configuration.addAllowedHeader("*");
		configuration.setAllowedMethods(Collections.singletonList(String.valueOf(Arrays.asList("POST","GET"))));
		source.registerCorsConfiguration("/**",configuration);
		return new CorsFilter(source);
	}

}
