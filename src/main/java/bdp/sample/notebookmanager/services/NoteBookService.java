package bdp.sample.notebookmanager.services;


import bdp.sample.notebookmanager.repositories.NoteBookRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import bdp.sample.notebookmanager.entities.NoteBook;

import java.util.List;
import java.util.Optional;

@Service
public class NoteBookService {

    @Autowired
    NoteBookRepository noteBookRepository;

    //This method gets page index and page size and returns records from database accordingly
    public List<NoteBook> listStocks(int page, int size){
        return noteBookRepository.findAll(PageRequest.of(page, size, Sort.by("ID").ascending())).toList();
    }

    // This method gets an Id and returns corresponding stock if it exists, otherwise it returns null
    public NoteBook getStockById(int stockId){
        Optional<NoteBook> stock = noteBookRepository.findById(stockId);
        if (stock.isPresent())
            return stock.get();
        // returns null if the given Id doesn't exist
        return null;
    }

    // This method creates given stock object in the database and returns it with its Id
    public NoteBook createStock(NoteBook stock){

        return noteBookRepository.save(stock);
    }

    //This method gets a stock object along with its Id, updates the name and currentPrice and returns it back
    //if the stock Id doesn't exist, it returns null
    public NoteBook updateStock(int stockId, NoteBook changedStock){
        Optional<NoteBook> stock = noteBookRepository.findById(stockId);
        //Check if the stock exists
        if (stock.isPresent()) {
            NoteBook tempStock= stock.get();
            tempStock.setName(changedStock.getName());
            tempStock.setCurrentPrice(changedStock.getCurrentPrice());
            // Save and return updated stock object
            return noteBookRepository.save(tempStock);
        }

        // returns null if the given Id doesn't exist
        return null;
    }

    //This method gets a stock Id and delete the corresponding stock record in databse
    //if the stock Id doesn't exist, it returns null
    public boolean deleteStock(int stockId){
        Optional<NoteBook> stock = noteBookRepository.findById(stockId);
        //Check if the stock exists
        if (stock.isPresent()) {
            noteBookRepository.delete(stock.get());
            return true;
        }

        // returns null if the given Id doesn't exist
        return false;
    }

}
