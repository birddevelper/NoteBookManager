package payconiq.example.stockmanagerrest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import payconiq.example.stockmanagerrest.StockManagerRestApplicationTests;
import payconiq.example.stockmanagerrest.configs.StockJpaConfig;
import payconiq.example.stockmanagerrest.entities.Stock;
import payconiq.example.stockmanagerrest.repositories.StockRepository;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@WebAppConfiguration("file:src/test/resources")
@ContextConfiguration( classes = { StockJpaConfig.class }) // loader = AnnotationConfigWebContextLoader.class,
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@AutoConfigureWebClient
class StockControllerTest {



   @Autowired
   MockMvc mockMvc;



    @Resource
    private StockRepository stockRepository;



    @BeforeEach
    public void setup()
    {

        // Insert sample records in temporary database
        stockRepository.deleteAllInBatch();
        Stock stock1 = new  Stock(1,"Asus Vivo Book S",211.9);
        Stock stock2 = new  Stock(2,"HP Inspiron",299.9);
        Stock stock3 = new  Stock(3,"Dell B3",399.9);
        Stock stock4 = new  Stock(4,"Asus Smart Book",400);
        Stock stock5 = new Stock(5,"Acer Logic",199.9);
        stockRepository.save(stock1);
        stockRepository.save(stock2);
        stockRepository.save(stock3);
        stockRepository.save(stock4);
        stockRepository.save(stock5);
    }

    @Test
    void getListOfStocksWithConfigurablePageSize() throws Exception {
        // First page with page size of 2
        mockMvc.perform(get("/api/stocks?page=0&pageSize=2")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json")).andExpect(jsonPath("$._embedded.stockList[0].id").value("1"))
                .andExpect(jsonPath("$._embedded.stockList[1].id").value("2"));

        // Second page with page size of 3
        mockMvc.perform(get("/api/stocks?page=1&pageSize=3")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$._embedded.stockList[0].id").value("4"))
                .andExpect(jsonPath("$._embedded.stockList[1].id").value("5"));

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

        Stock stock = new  Stock(2,"HP FitBook",305.99);
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

        Stock stock = new  Stock(77,"HP FitBook",305.99);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(stock);
        // Update stock with id = 77 which is not existed
        mockMvc.perform(patch("/api/stocks/77").content(json).characterEncoding("utf-8")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());

    }


    @Test
    void createStock() throws Exception{

        Stock stock = new  Stock("HP FitBook",305.99);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(stock);

        // Create new stock
        mockMvc.perform(post("/api/stocks").content(json).characterEncoding("utf-8")
                .contentType("application/json"))
                .andExpect(status().isCreated());

    }

    @Test
    void deleteStockWithExistingStockId() throws Exception{

        // Delete stock with id = 2
        mockMvc.perform(delete("/api/stocks/2"))
                .andExpect(status().isAccepted());
    }


}