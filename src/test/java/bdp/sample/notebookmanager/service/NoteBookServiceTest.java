package bdp.sample.notebookmanager.service;

import bdp.sample.notebookmanager.configs.JpaConfig;
import bdp.sample.notebookmanager.repositories.NoteBookRepository;
import bdp.sample.notebookmanager.services.NoteBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import bdp.sample.notebookmanager.entities.NoteBook;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ContextConfiguration(
        classes = { JpaConfig.class },
        loader = AnnotationConfigContextLoader.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class NoteBookServiceTest {

    @Resource
    private NoteBookRepository noteBookRepository;
    @Resource
    private NoteBookService noteBookService;


    @BeforeEach
    public void init()
    {
        // Insert sample records in temporary database
        noteBookRepository.deleteAllInBatch();
        NoteBook stock1 = new NoteBook(1,"Asus Vivo Book S",211.9);
        NoteBook stock2 = new NoteBook(2,"HP Inspiron",299.9);
        NoteBook stock3 = new NoteBook(3,"Dell B3",399.9);
        NoteBook stock4 = new NoteBook(4,"Asus Smart Book",400);
        NoteBook stock5 = new NoteBook(5,"Acer Logic",199.9);
        noteBookRepository.save(stock1);
        noteBookRepository.save(stock2);
        noteBookRepository.save(stock3);
        noteBookRepository.save(stock4);
        noteBookRepository.save(stock5);
    }



    @Test
    public void createStock() {
        NoteBook stock = new NoteBook(6,"LG Noteook",299.9);
        NoteBook stock2 = noteBookService.createStock(stock);

        assertNotEquals(null, stock,"Create new stock didn't work");
        assertEquals("LG Noteook", stock2.getName(),"createStock didn't work (Name is not set)");
        assertEquals(299.9, stock2.getCurrentPrice(),"createStock  didn't work (CurrentPrice is not set)");
    }


    @Test
    public void getExistingStockById() {

        NoteBook stock = noteBookService.getStockById(2);
        assertNotEquals(null, stock,"getStockById didn't work correctly when stock exists");
        assertEquals("HP Inspiron", stock.getName(),"getStockById didn't work correctly when stock exists (Name is not set)");
    }

    @Test
    public void getNotExistingStockById() {

        NoteBook stock = noteBookService.getStockById(9);
        assertEquals(null, stock,"getStockById didn't work correctly when stock doesn't exist");
    }

    @Test
    public void listStocks()
    {

        List<NoteBook> stockList = noteBookService.listStocks(0,3);
        assertEquals(stockList.size(), 3,"listStocks didn't retrieved correct number of records");
        assertEquals(stockList.get(0).getID(), 1,"listStocks didn't retrieved correct records");
        assertEquals(stockList.get(2).getID(), 3,"listStocks didn't retrieved correct records");

    }

    @Test
    public void updateStock() {
        NoteBook stock = new NoteBook(2,"LG Noteook",399.9);
        NoteBook stock2 = noteBookService.updateStock(2,stock);

        assertNotEquals(null, stock2,"updateStock stock didn't work");
        assertEquals("LG Noteook", stock2.getName(),"updateStock  didn't work (Name is not set)");
        assertEquals(399.9, stock2.getCurrentPrice(),"updateStock  didn't work (CurrentPrice is not set)");
    }

    @Test
    public void deleteStock() {

        Boolean stockDeleted = noteBookService.deleteStock(4);

        assertEquals(true, stockDeleted,"deleteStock  didn't work ");
        assertThrows(JpaObjectRetrievalFailureException.class, () -> {
            noteBookRepository.getById(4);},"deleteStock didn't work");
    }


}
