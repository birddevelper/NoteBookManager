package payconiq.example.stockmanagerrest.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import payconiq.example.stockmanagerrest.entities.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock,Integer> {

    public Page<Stock> findAll(Pageable page);
}
