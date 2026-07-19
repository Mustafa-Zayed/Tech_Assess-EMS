package com.ds_middle_east.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "MustafaZ",
                        email = "darsh.zayed22@gmail.com",
                        url = "https://www.facebook.com/mustafa.zayed.1612147"
                ),
                description = "OpenApi documentation for Employee Management System",
                title = "OpenApi specification - EMS",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local Server",
                        url = "http://localhost:8080"
                ),
        }
)
public class OpenApiConfig {
}
