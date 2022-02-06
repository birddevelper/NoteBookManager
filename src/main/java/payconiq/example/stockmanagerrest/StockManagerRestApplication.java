package payconiq.example.stockmanagerrest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import payconiq.example.stockmanagerrest.entities.Stock;
import payconiq.example.stockmanagerrest.repositories.StockRepository;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class StockManagerRestApplication {

    public static void main(String[] args) {

        SpringApplication.run(StockManagerRestApplication.class, args);

    }

}
