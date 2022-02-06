package payconiq.example.stockmanagerrest.services;


import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import payconiq.example.stockmanagerrest.entities.Stock;
import payconiq.example.stockmanagerrest.repositories.StockRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    StockRepository stockRepository;

    public List<Stock> listStocks(int page, int size){
        return stockRepository.findAll(PageRequest.of(page, size, Sort.by("ID").ascending())).toList();
    }

    public Stock getStockById(int stockId){
        Optional<Stock> stock = stockRepository.findById(stockId);
        if (stock.isPresent())
            return stock.get();

        return null;
    }

    public Stock createStock(Stock stock){
        return stockRepository.save(stock);
    }


    public Stock updateStock(int stockId, Stock changedStock){
        Optional<Stock> stock = stockRepository.findById(stockId);
        if (stock.isPresent()) {
            Stock tempStock= stock.get();
            tempStock.setName(changedStock.getName());
            tempStock.setCurrentPrice(changedStock.getCurrentPrice());
            return stockRepository.save(tempStock);
        }

        return null;
    }


    public boolean deleteStock(int stockId){
        Optional<Stock> stock = stockRepository.findById(stockId);
        if (stock.isPresent()) {
            stockRepository.delete(stock.get());
            return true;
        }
        return false;
    }

}
