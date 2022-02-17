package bdp.sample.notebookmanager.repositories;

import bdp.sample.notebookmanager.entities.NoteBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
/*
@ContextConfiguration(
        classes = { JpaConfig.class },
        loader = AnnotationConfigContextLoader.class)
*/
/*classes = { JpaConfig.class },*/
//@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class)
@DataJpaTest
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
    void findAllWithSpecifPageNumberAndPageSize() {
        List<NoteBook> listnotebooks = noteBookRepository.findAll(PageRequest.of(0, 2, Sort.by("ID").ascending())).toList();
        assertEquals(listnotebooks.size(), 2,"FindAll didn't retrieved correct number of records");
        assertEquals(listnotebooks.get(0).getID(), 1,"FindAll didn't retrieved correct records");
        assertEquals(listnotebooks.get(1).getID(), 2,"FindAll didn't retrieved correct records");

    }

    @Test
    public void findOnenotebookById() {


        Optional<NoteBook> notebook = noteBookRepository.findById(3);
        assertEquals(true, notebook.isPresent(),"FindOne notebook didn't work");
        assertEquals("Dell B3", notebook.get().getName(),"FindOne notebook didn't work");
    }

    @Test
    public void saveNewnotebook() {
        NoteBook notebook = new NoteBook(6,"LG Noteook",299.9);
        noteBookRepository.save(notebook);

        Optional<NoteBook> notebook2 = noteBookRepository.findById(6);
        assertEquals(true, notebook2.isPresent(),"Save new notebook didn't work");
        assertEquals("LG Noteook", notebook2.get().getName(),"Save new notebook didn't work");
    }

    @Test
    public void updatenotebook() {

        NoteBook notebook = new NoteBook(2,"LG Noteook",399.9);
        NoteBook notebook2 = noteBookRepository.save(notebook);

        assertNotEquals(null, notebook2,"updatenotebook notebook didn't work");
        assertEquals("LG Noteook", notebook2.getName(),"updatenotebook  didn't work (Name is not set)");
        assertEquals(399.9, notebook2.getCurrentPrice(),"updatenotebook  didn't work (CurrentPrice is not set)");

    }

    @Test
    public void deletenotebook() {

        NoteBook notebook = noteBookRepository.getById(4);

        noteBookRepository.delete(notebook);
        assertThrows(JpaObjectRetrievalFailureException.class, () -> {
            noteBookRepository.getById(4);},"Delete record didn't work");
    }
}