package bdp.sample.notebookmanager.service;

import bdp.sample.notebookmanager.repositories.NoteBookRepository;
import bdp.sample.notebookmanager.services.NoteBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.transaction.annotation.Transactional;
import bdp.sample.notebookmanager.entities.NoteBook;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class)/*classes = { JpaConfig.class },*/
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
        NoteBook notebook1 = new NoteBook(1,"Asus Vivo Book S",211.9);
        NoteBook notebook2 = new NoteBook(2,"HP Inspiron",299.9);
        NoteBook notebook3 = new NoteBook(3,"Dell B3",399.9);
        NoteBook notebook4 = new NoteBook(4,"Asus Smart Book",400);
        NoteBook notebook5 = new NoteBook(5,"Acer Logic",199.9);
        noteBookRepository.save(notebook1);
        noteBookRepository.save(notebook2);
        noteBookRepository.save(notebook3);
        noteBookRepository.save(notebook4);
        noteBookRepository.save(notebook5);
    }



    @Test
    public void createnotebook() {
        NoteBook notebook = new NoteBook(6,"LG Noteook",299.9);
        NoteBook notebook2 = noteBookService.createnotebook(notebook);

        assertNotEquals(null, notebook,"Create new notebook didn't work");
        assertEquals("LG Noteook", notebook2.getName(),"createnotebook didn't work (Name is not set)");
        assertEquals(299.9, notebook2.getCurrentPrice(),"createnotebook  didn't work (CurrentPrice is not set)");
    }


    @Test
    public void getExistingnotebookById() {

        NoteBook notebook = noteBookService.getnotebookById(2);
        assertNotEquals(null, notebook,"getnotebookById didn't work correctly when notebook exists");
        assertEquals("HP Inspiron", notebook.getName(),"getnotebookById didn't work correctly when notebook exists (Name is not set)");
    }

    @Test
    public void getNotExistingnotebookById() {

        NoteBook notebook = noteBookService.getnotebookById(9);
        assertEquals(null, notebook,"getnotebookById didn't work correctly when notebook doesn't exist");
    }

    @Test
    public void listnotebooks()
    {

        List<NoteBook> notebookList = noteBookService.listnotebooks(0,3);
        assertEquals(3, notebookList.size(), "listnotebooks didn't retrieved correct number of records");
        assertEquals(1, notebookList.get(0).getID(), "listnotebooks didn't retrieved correct records");
        assertEquals(3, notebookList.get(2).getID(), "listnotebooks didn't retrieved correct records");

    }

    @Test
    public void updatenotebook() {
        NoteBook notebook = new NoteBook(2,"LG Noteook",399.9);
        NoteBook notebook2 = noteBookService.updatenotebook(2,notebook);

        assertNotEquals(null, notebook2,"updatenotebook notebook didn't work");
        assertEquals("LG Noteook", notebook2.getName(),"updatenotebook  didn't work (Name is not set)");
        assertEquals(399.9, notebook2.getCurrentPrice(),"updatenotebook  didn't work (CurrentPrice is not set)");
    }

    @Test
    public void deletenotebook() {

        Boolean notebookDeleted = noteBookService.deletenotebook(4);

        assertEquals(true, notebookDeleted,"deletenotebook  didn't work ");
        assertThrows(JpaObjectRetrievalFailureException.class, () -> {
            noteBookRepository.getById(4);},"deletenotebook didn't work");
    }


}
