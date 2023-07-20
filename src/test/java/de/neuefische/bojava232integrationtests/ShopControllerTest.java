package de.neuefische.bojava232integrationtests;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ShopControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void getAllProducts_shouldReturnEmptyList_whenRepositoryIsEmpty() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/shop/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @DirtiesContext
    @Test
    void getAllProducts_shouldReturnListWithOneObject_whenOneObjectWasSavedInRepository() throws Exception {
        Product product = new Product("1", "Mayo");
        productRepository.save(product);

        mvc.perform(MockMvcRequestBuilders.get("/shop/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                        [
                         {
                             "name": "Mayo",
                             "id": "1"
                         }
                        ]
                        """
                ));
    }

    @DirtiesContext
    @Test
    void addProduct_shouldReturnCreatedProduct() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/shop/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                 {
                                      "name": "Mayo",
                                      "id": "1"
                                 }
                                """
                        ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                         {
                             "name": "Mayo",
                             "id": "1"
                         }
                        """
                ));
    }
}