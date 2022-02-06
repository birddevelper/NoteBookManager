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

    //This method gets page index and page size and returns records from database accordingly
    public List<Stock> listStocks(int page, int size){
        return stockRepository.findAll(PageRequest.of(page, size, Sort.by("ID").ascending())).toList();
    }

    // This method gets an Id and returns corresponding stock if it exists, otherwise it returns null
    public Stock getStockById(int stockId){
        Optional<Stock> stock = stockRepository.findById(stockId);
        if (stock.isPresent())
            return stock.get();
        // returns null if the given Id doesn't exist
        return null;
    }

    // This method creates given stock object in the database and returns it with its Id
    public Stock createStock(Stock stock){

        return stockRepository.save(stock);
    }

    //This method gets a stock object along with its Id, updates the name and currentPrice and returns it back
    //if the stock Id doesn't exist, it returns null
    public Stock updateStock(int stockId, Stock changedStock){
        Optional<Stock> stock = stockRepository.findById(stockId);
        //Check if the stock exists
        if (stock.isPresent()) {
            Stock tempStock= stock.get();
            tempStock.setName(changedStock.getName());
            tempStock.setCurrentPrice(changedStock.getCurrentPrice());
            // Save and return updated stock object
            return stockRepository.save(tempStock);
        }

        // returns null if the given Id doesn't exist
        return null;
    }

    //This method gets a stock Id and delete the corresponding stock record in databse
    //if the stock Id doesn't exist, it returns null
    public boolean deleteStock(int stockId){
        Optional<Stock> stock = stockRepository.findById(stockId);
        //Check if the stock exists
        if (stock.isPresent()) {
            stockRepository.delete(stock.get());
            return true;
        }

        // returns null if the given Id doesn't exist
        return false;
    }

}
