package bdp.sample.notebookmanager.repositories;

import bdp.sample.notebookmanager.configs.JpaConfig;
import bdp.sample.notebookmanager.entities.NoteBook;
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

import javax.annotation.Resource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ContextConfiguration(
        classes = { JpaConfig.class },
        loader = AnnotationConfigContextLoader.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class NoteBookRepositoryTest {
    @Resource
    private NoteBookRepository noteBookRepository;

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
    void findAllWithSpecifPageNumberAndPageSize() {
        List<NoteBook> listStocks = noteBookRepository.findAll(PageRequest.of(0, 2, Sort.by("ID").ascending())).toList();
        assertEquals(listStocks.size(), 2,"FindAll didn't retrieved correct number of records");
        assertEquals(listStocks.get(0).getID(), 1,"FindAll didn't retrieved correct records");
        assertEquals(listStocks.get(1).getID(), 2,"FindAll didn't retrieved correct records");

    }

    @Test
    public void findOneStockById() {


        Optional<NoteBook> stock = noteBookRepository.findById(3);
        assertEquals(true, stock.isPresent(),"FindOne stock didn't work");
        assertEquals("Dell B3", stock.get().getName(),"FindOne stock didn't work");
    }

    @Test
    public void saveNewStock() {
        NoteBook stock = new NoteBook(6,"LG Noteook",299.9);
        noteBookRepository.save(stock);

        Optional<NoteBook> stock2 = noteBookRepository.findById(6);
        assertEquals(true, stock2.isPresent(),"Save new stock didn't work");
        assertEquals("LG Noteook", stock2.get().getName(),"Save new stock didn't work");
    }

    @Test
    public void updateStock() {

        NoteBook stock = new NoteBook(2,"LG Noteook",399.9);
        NoteBook stock2 = noteBookRepository.save(stock);

        assertNotEquals(null, stock2,"updateStock stock didn't work");
        assertEquals("LG Noteook", stock2.getName(),"updateStock  didn't work (Name is not set)");
        assertEquals(399.9, stock2.getCurrentPrice(),"updateStock  didn't work (CurrentPrice is not set)");

    }

    @Test
    public void deleteStock() {

        NoteBook stock = noteBookRepository.getById(4);

        noteBookRepository.delete(stock);
        assertThrows(JpaObjectRetrievalFailureException.class, () -> {
            noteBookRepository.getById(4);},"Delete record didn't work");
    }
}