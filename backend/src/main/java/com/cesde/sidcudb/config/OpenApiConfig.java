package com.cesde.sidcudb.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Metadatos de la documentación OpenAPI (Swagger UI y Scalar), adaptados al contexto del negocio:
 * orientación de estudiantes hacia carreras y universidades.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI sidcuOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SIDCU API - Orientación de carreras y universidades")
                        .version("1.0.0")
                        .description("""
                                API REST del sistema SIDCU. Permite a los estudiantes registrar sus intereses en \
                                carreras y universidades, y consultar qué instituciones ofrecen los programas que \
                                les interesan, priorizando las de su propia ciudad.

                                Recursos principales: estudiantes y sus perfiles, carreras y sus habilidades, \
                                universidades, oferta académica (qué universidad ofrece qué carrera) e intereses.""")
                        .contact(new Contact()
                                .name("Equipo SIDCU - CESDE")
                                .email("sidcu@cesde.net"))
                        .license(new License()
                                .name("Uso académico")));
    }
}
