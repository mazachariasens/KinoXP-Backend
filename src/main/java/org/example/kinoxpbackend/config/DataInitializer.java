package org.example.kinoxpbackend.config;

import org.example.kinoxpbackend.entity.Theater;
import org.example.kinoxpbackend.repository.TheaterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeData(
            TheaterRepository theaterRepository) {

        return args -> {
            if (theaterRepository.count() == 0) {

                theaterRepository.save(new Theater("Lille sal", 20, 12)
                );

                theaterRepository.save(
                        new Theater("Stor sal", 25, 16)
                );
            }
        };
    }
}

