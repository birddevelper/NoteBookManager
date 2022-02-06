package payconiq.example.stockmanagerrest.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import payconiq.example.stockmanagerrest.entities.Stock;
import payconiq.example.stockmanagerrest.repositories.StockRepository;

@Configuration
class InitiateDatabaseRecords {

    private static final Logger log = LoggerFactory.getLogger(InitiateDatabaseRecords.class);


    @Bean
    CommandLineRunner initDatabase(StockRepository repository) {
        if(repository.findAll().size()<1) {
            return args -> {
                try {
                    repository.save(new Stock("Asus Vivo Book S", 211.9));
                    repository.save(new Stock("HP Inspiron", 299.9));
                    repository.save(new Stock("Dell Magic", 300));
                    repository.save(new Stock("Apple MacBook", 709.9));
                    repository.save(new Stock("LG D230", 299.9));
                    repository.save(new Stock("Acer P700", 199.9));

                    log.info("Initial records inserted into database." );
                } catch (Exception e) {
                    log.info("Error in initiating records into database." );
                }
            };
        }
        else
            return args -> {
                log.info("Records already initiated ");
            };
    }
}