package com.xdf.xd_f371;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan({"com.xdf.xd_f371.controller","com.xdf.xd_f371.model",
		"com.xdf.xd_f371.service",
		"com.xdf.xd_f371.service.impl",
		"com.xdf.xd_f371.dto","com.xdf.xd_f371.fatory","com.xdf.xd_f371.config.DataSourceConfig"})
@EnableJpaRepositories(basePackages = {"com.xdf.xd_f371.repo"})
@EntityScan(basePackages = {"com.xdf.xd_f371.entity"})
@SpringBootApplication(scanBasePackages = {"com.xdf.xd_f371"})
public class XdF371Application {

	public static void main(String[] args) {
		SpringApplication.run(XdF371Application.class, args);
	}
}
