package payconiq.example.stockmanagerrest.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import payconiq.example.stockmanagerrest.configs.StockJpaConfig;
import payconiq.example.stockmanagerrest.entities.Stock;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ContextConfiguration(
        classes = { StockJpaConfig.class },
        loader = AnnotationConfigContextLoader.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StockRepositoryTest {
    @Resource
    private StockRepository stockRepository;

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
    void findAllWithSpecifPageNumberAndPageSize() {
        List<Stock> listStocks = stockRepository.findAll(PageRequest.of(0, 2, Sort.by("ID").ascending())).toList();
        assertEquals(listStocks.size(), 2,"FindAll didn't retrieved correct number of records");
        assertEquals(listStocks.get(0).getID(), 1,"FindAll didn't retrieved correct records");
        assertEquals(listStocks.get(1).getID(), 2,"FindAll didn't retrieved correct records");

    }

    @Test
    public void findOneStockById() {


        Optional<Stock> stock = stockRepository.findById(3);
        assertEquals(true, stock.isPresent(),"FindOne stock didn't work");
        assertEquals("Dell B3", stock.get().getName(),"FindOne stock didn't work");
    }

    @Test
    public void saveNewStock() {
        Stock stock = new  Stock(6,"LG Noteook",299.9);
        stockRepository.save(stock);

        Optional<Stock> stock2 = stockRepository.findById(6);
        assertEquals(true, stock2.isPresent(),"Save new stock didn't work");
        assertEquals("LG Noteook", stock2.get().getName(),"Save new stock didn't work");
    }

    @Test
    public void updateStock() {

        Stock stock = new  Stock(2,"LG Noteook",399.9);
        Stock stock2 = stockRepository.save(stock);

        assertNotEquals(null, stock2,"updateStock stock didn't work");
        assertEquals("LG Noteook", stock2.getName(),"updateStock  didn't work (Name is not set)");
        assertEquals(399.9, stock2.getCurrentPrice(),"updateStock  didn't work (CurrentPrice is not set)");

    }

    @Test
    public void deleteStock() {

        Stock stock = stockRepository.getById(4);

        stockRepository.delete(stock);
        assertThrows(JpaObjectRetrievalFailureException.class, () -> {stockRepository.getById(4);},"Delete record didn't work");
    }
}