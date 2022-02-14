package bdp.sample.notebookmanager.controller;

import bdp.sample.notebookmanager.configs.JpaConfig;
import bdp.sample.notebookmanager.entities.NoteBook;
import bdp.sample.notebookmanager.repositories.NoteBookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@WebAppConfiguration("file:src/test/resources")
@ContextConfiguration( classes = { JpaConfig.class }) // loader = AnnotationConfigWebContextLoader.class,
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@AutoConfigureWebClient
class NoteBookControllerTest {



   @Autowired
   MockMvc mockMvc;



    @Resource
    private NoteBookRepository noteBookRepository;



    @BeforeEach
    public void setup()
    {

        // Insert sample records in temporary database
        //stockRepository.deleteAllInBatch();
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
    void getListOfStocksWithConfigurablePageSize() throws Exception {
        // First page with page size of 2
        mockMvc.perform(get("/api/stocks?page=0&pageSize=2")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json")).andExpect(jsonPath("$._embedded.noteBookList[0].id").value("1"))
                .andExpect(jsonPath("$._embedded.noteBookList[1].id").value("2"));

        // Second page with page size of 3
        mockMvc.perform(get("/api/stocks?page=1&pageSize=3")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$._embedded.noteBookList[0].id").value("4"))
                .andExpect(jsonPath("$._embedded.noteBookList[1].id").value("5"));

    }

    @Test
    void getSingleExistingStockById() throws Exception {

        // Get stock with id=2
        mockMvc.perform(get("/api/stocks/2")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("2"))
                .andExpect(jsonPath("$.name").value("HP Inspiron"));

    }


    @Test
    void getSingleNotExistingStockById() throws Exception {

        // Get stock with id=22
        mockMvc.perform(get("/api/stocks/22"))
                .andExpect(status().isNotFound());

    }

    @Test
    void getSingleStockByInvalidStockId() throws Exception {

        // Get stock with id=i
        mockMvc.perform(get("/api/stocks/i"))
                .andExpect(status().isBadRequest());

        // Get stock with id=*
        mockMvc.perform(get("/api/stocks/*"))
                .andExpect(status().isBadRequest());

    }

    @Test
    void updateStockWithExistingStockId() throws Exception{

        NoteBook stock = new NoteBook(2,"HP FitBook",305.99);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(stock);
        // Update stock with id = 2
        mockMvc.perform(patch("/api/stocks/2").content(json).characterEncoding("utf-8")
                        .contentType("application/json"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.name").value("HP FitBook"))
                .andExpect(jsonPath("$.currentPrice").value(305.99));
    }


    @Test
    void updateStockWithNotExistingStockId() throws Exception{

        NoteBook stock = new NoteBook(77,"HP FitBook",305.99);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(stock);
        // Update stock with id = 77 which is not existed
        mockMvc.perform(patch("/api/stocks/77").content(json).characterEncoding("utf-8")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());

    }


    @Test
    void createStock() throws Exception{

        NoteBook stock = new NoteBook("HP FitBook",305.99);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(stock);

        // Create new stock
        mockMvc.perform(post("/api/stocks").content(json).characterEncoding("utf-8")
                .contentType("application/json"))
                .andExpect(status().isCreated());

    }

    @Test
    void createStockWithDuplicateName() throws Exception{

        NoteBook stock = new NoteBook("Dell B3",305.99);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(stock);

        // Create new stock with duplicate name
        mockMvc.perform(post("/api/stocks").content(json).characterEncoding("utf-8")
                        .contentType("application/json"))
                        .andExpect(status().isConflict());

    }

    @Test
    void deleteStockWithExistingStockId() throws Exception{

        // Delete stock with id = 2
        mockMvc.perform(delete("/api/stocks/2"))
                .andExpect(status().isAccepted());
    }


}