package com.dip.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition (
		info = @Info(
				title = "Insurance Claim And Wallet Service",
				description = "This Microservice lets user add balance in wallet, purchase insurance policy and claim the amount og the purchased insurance policy",
				version = "v1"
				),
		servers = {
				@Server(
						description = "Dev",
						url = "http://localhost:8082"
						)
		}
		)
public class OpenApiConfig {

}
