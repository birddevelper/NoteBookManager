package payconiq.example.stockmanagerrest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import payconiq.example.stockmanagerrest.configs.StockJpaConfig;
import payconiq.example.stockmanagerrest.entities.Stock;
import payconiq.example.stockmanagerrest.repositories.StockRepository;
import payconiq.example.stockmanagerrest.services.StockService;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ContextConfiguration(
        classes = { StockJpaConfig.class },
        loader = AnnotationConfigContextLoader.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class StockServiceTest {

    @Resource
    private StockRepository stockRepository;
    @Resource
    private StockService stockService;


    @BeforeEach
    public void init()
    {
        // Insert sample records in temporary database
        stockRepository.deleteAllInBatch();
        Stock stock1 = new  Stock(1,"Asus Vivo Book S",211.9);
        Stock stock2 = new  Stock(2,"HP Inspiron",299.9);
        Stock stock3 = new  Stock(3,"Dell B3",399.9);
        Stock stock4 = new  Stock(4,"Asus Smart Book",400);
        Stock stock5 = new  Stock(5,"Acer Logic",199.9);
        stockRepository.save(stock1);
        stockRepository.save(stock2);
        stockRepository.save(stock3);
        stockRepository.save(stock4);
        stockRepository.save(stock5);
    }



    @Test
    public void createStock() {
        Stock stock = new  Stock(6,"LG Noteook",299.9);
        Stock stock2 = stockService.createStock(stock);

        assertNotEquals(null, stock,"Create new stock didn't work");
        assertEquals("LG Noteook", stock2.getName(),"createStock didn't work (Name is not set)");
        assertEquals(299.9, stock2.getCurrentPrice(),"createStock  didn't work (CurrentPrice is not set)");
    }


    @Test
    public void getExistingStockById() {

        Stock stock = stockService.getStockById(2);
        assertNotEquals(null, stock,"getStockById didn't work correctly when stock exists");
        assertEquals("HP Inspiron", stock.getName(),"getStockById didn't work correctly when stock exists (Name is not set)");
    }

    @Test
    public void getNotExistingStockById() {

        Stock stock = stockService.getStockById(9);
        assertEquals(null, stock,"getStockById didn't work correctly when stock doesn't exist");
    }

    @Test
    public void listStocks()
    {

        List<Stock> stockList = stockService.listStocks(0,3);
        assertEquals(stockList.size(), 3,"listStocks didn't retrieved correct number of records");
        assertEquals(stockList.get(0).getID(), 1,"listStocks didn't retrieved correct records");
        assertEquals(stockList.get(2).getID(), 3,"listStocks didn't retrieved correct records");

    }

    @Test
    public void updateStock() {
        Stock stock = new  Stock(2,"LG Noteook",399.9);
        Stock stock2 = stockService.updateStock(2,stock);

        assertNotEquals(null, stock2,"updateStock stock didn't work");
        assertEquals("LG Noteook", stock2.getName(),"updateStock  didn't work (Name is not set)");
        assertEquals(399.9, stock2.getCurrentPrice(),"updateStock  didn't work (CurrentPrice is not set)");
    }

    @Test
    public void deleteStock() {

        Boolean stockDeleted = stockService.deleteStock(4);

        assertEquals(true, stockDeleted,"deleteStock  didn't work ");
        assertThrows(JpaObjectRetrievalFailureException.class, () -> {stockRepository.getById(4);},"deleteStock didn't work");
    }


}
