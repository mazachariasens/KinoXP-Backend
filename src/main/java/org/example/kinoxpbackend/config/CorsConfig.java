package org.example.kinoxpbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//Gør at frontend og backend kan tale sammen, selvom de kører på forskellige porte. Dette er nødvendigt, fordi browseren ellers vil blokere for cross-origin requests.
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:63342") // siger til Spring Boot at frontend der kører på localhost:63342 må tilgå backend endpoints der starter med /api/
                .allowedMethods("GET", "POST", "PUT", "DELETE");

    }
}
